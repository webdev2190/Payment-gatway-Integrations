# Payment-gatway-Integrations

package com.optum.ove.common.etl.cdrbe

import com.optum.insights.smith.fhir.Allergy
import com.optum.insights.smith.fhir.datatypes._
import com.optum.oap.sparkdataloader.{RuntimeVariables, UserDefinedFunctionForDataLoader}
import com.optum.ove.common.utils.ClinicalFhirDataTypeUtils.{createCodeableConceptFromCodingsUDF, createCodeableConceptUDF, createCodingUDF, createIdentifierWithCodeableConceptUDF, createReferenceUDF, rowToMeta}
import com.optum.ove.common.utils.{BaseTableInfo, CommonRuntimeVariables}
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types.{ArrayType, TimestampType}
import org.apache.spark.sql.{DataFrame, Encoders, SparkSession}

import java.sql.Timestamp

object AllergyTableInfo extends BaseTableInfo[Allergy] {

  override def name: String = "ALLERGY"

  override def dependsOn: Set[String] = Set(
    "ALLERGY_SRC",
    "MAP_ALLERGEN",
    "MAP_ALLERGEN_TYPE",
    "MAP_ALLERGY_STATUS",
    "MV_HTS_DOMAIN_CONCEPT",
    "REF_HTS_DCC_CURRENT",
    "XWALK_MAP"
  )

  override protected def createDataFrame(sparkSession: SparkSession, loadedDependencies: Map[String, DataFrame], udfMap: Map[String, UserDefinedFunctionForDataLoader], runtimeVariables: RuntimeVariables): DataFrame = {

    import sparkSession.implicits._

    val setupDtmTimestamp = Timestamp.valueOf(CommonRuntimeVariables(runtimeVariables).setupDtm)

    val allergyDF = loadedDependencies("ALLERGY_SRC").as("a")
      .select(
        $"a.client_ds_id",
        $"a.localallergencd",
        $"a.localallergentype",
        $"a.grp_mpi",
        $"a.onsetdate",
        $"a.localstatus",
        $"a.localallergendesc",
        $"a.dcc",
        $"a.encounterid"
      ).distinct()

    val mapAllergenDF = loadedDependencies("MAP_ALLERGEN").as("ma")
      .select(
        $"ma.mnemonic",
        $"ma.cui"
      )

    val mapAllergenTypeDF = loadedDependencies("MAP_ALLERGEN_TYPE").as("mat")
      .select(
        $"mat.localcode",
        $"mat.cui"
      )

    val mapAllergyStatusDF = loadedDependencies("MAP_ALLERGY_STATUS").as("mas")
      .select(
        $"mas.mnemonic",
        $"mas.cui"
      )

    val mvHtsDomainConceptDF = loadedDependencies("MV_HTS_DOMAIN_CONCEPT").as("mv")
      .select(
        $"mv.concept_cui",
        $"mv.concept_name"
      )

    val refHtsDccCurrentDF = loadedDependencies("REF_HTS_DCC_CURRENT").as("rh")
      .select(
        $"rh.dcc",
        $"rh.genericname",
        $"rh.pcc",
        $"rh.pcc_label"
      ).dropDuplicates("pcc", "pcc_label")

    val xwalk_mapDF = loadedDependencies("XWALK_MAP").as("xm").filter($"xm.domain" === "allergy")

    val finalAllergyDF = allergyDF.as("a")
      .join(mapAllergenDF.as("ma"), $"a.localallergencd" === $"ma.mnemonic", "left")
      .join(mapAllergenTypeDF.as("mat"), $"a.localallergentype" === $"mat.localcode", "left")
      .join(xwalk_mapDF.filter($"sourceAttribute" === "category").as("xwm1"), $"mat.cui" === $"xwm1.sourceCode", "left")
      .join(mapAllergyStatusDF.as("mas"), $"a.localstatus" === $"mas.mnemonic", "left")
      .join(xwalk_mapDF.filter($"sourceAttribute" === "clinicalStatus").as("xwm2"), $"mas.cui" === $"xwm2.sourceCode", "left")
      .join(mvHtsDomainConceptDF.as("mhdc"), $"ma.cui" === $"mhdc.concept_cui", "left")
      .join(refHtsDccCurrentDF.as("rhdc").as("mdcc"), $"a.dcc" === $"mdcc.dcc", "left")
      .join(refHtsDccCurrentDF.as("rhdc").as("mpcc"), $"a.dcc" === $"mpcc.pcc", "left")
      .withColumn("localcode", createCodingUDF(lit("local"), lit("CDR:"), col("a.client_ds_id"), col("a.localallergencd"), col("a.localallergendesc")))
      .withColumn("mapToCui", createCodingUDF(lit("CUI"), lit("CUI"), lit(""), col("ma.cui"), col("mhdc.concept_name")))
      .withColumn("mapToDcc", createCodingUDF(lit("DCC"), lit("DCC"), lit(""), col("a.dcc"), col("mdcc.genericname")))
      .withColumn("mapToPcc", when(col("a.dcc").isNotNull && length(col("a.dcc")) === 3, createCodingUDF(lit("PCC"), lit("PCC"), lit(""), col("a.dcc"), col("mpcc.pcc_label"))).otherwise(lit(null).cast(Encoders.product[Coding].schema)))
      .withColumn("code", createCodeableConceptFromCodingsUDF(array(col("localcode"), col("mapToCui"), col("mapToDcc"), col("mapToPcc"))))
      .withColumn("formattedOnsetDate", date_format(col("a.onsetdate"), "yyyy-MM-dd HH:mm:ss.SSS")) // to get the date in the format yyyy-MM-dd HH:mm:ss.SSS which is required for the id column
      .select(
        createIdentifierWithCodeableConceptUDF(
          concat_ws("", col("a.client_ds_id"), col("a.localallergencd"), col("a.localallergentype"), col("a.grp_mpi"), col("formattedOnsetDate"), col("a.localstatus"), col("a.localallergendesc"), col("a.dcc"), col("a.encounterid")),
          col("a.client_ds_id"), lit("usual"), lit(""), concat(lit("CDR:"), col("a.client_ds_id")), lit("concatenated"), lit("a concated key as there is no PK on the data")).as("id"),
        typedLit(rowToMeta(lit(setupDtmTimestamp))).as("meta"),
        lit(null).cast(ArrayType(Encoders.product[Identifier].schema)).as("ids"),
        createCodeableConceptUDF(array(struct(concat(lit("CDR:"), col("a.client_ds_id")), col("xwm2.targetCode"), lit(null)))).as("clinicalStatus"),
        lit(null).cast(Encoders.product[CodeableConcept].schema).as("verificationStatus"),
        typedLit(CodeableConcept.createCodeableConcept(Seq(Coding(null, "allergy")))).as("type"),
        createCodeableConceptUDF(array(struct(concat(lit("CDR:"), col("a.client_ds_id")), col("xwm1.targetCode"), lit(null)))).as("category"),
        lit(null).cast(Encoders.product[CodeableConcept].schema).as("criticality"),
        $"code",
        createReferenceUDF(lit("patient"), col("a.grp_mpi"), lit("CDR"), lit(null)).as("patient"),
        createReferenceUDF(lit("encounter"), col("a.encounterid"), concat(lit("CDR:"), col("a.client_ds_id")), lit(null)).as("encounter"),
        lit(null).cast(Encoders.product[TimeType].schema).as("onset"),
        col("a.onsetdate").as("recordedDate"),
        lit(null).cast(Encoders.product[Reference].schema).as("recorder"),
        typedLit(null).as("lastOccurrence").cast(TimestampType),
        lit(null).cast(ArrayType(Encoders.product[Annotation].schema)).as("notes"),
        lit(null).cast(ArrayType(Encoders.product[Extension].schema)).as("extension")
      )
    finalAllergyDF
  }
}
