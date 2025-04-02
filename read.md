AllergyTableInfoTest

import com.optum.insights.smith.fhir.Allergy
import com.optum.insights.smith.fhir.datatypes._
import com.optum.oap.cdr.models._
import com.optum.ove.common.etl.framework.QueryTestFramework
import com.optum.ove.common.models.xwalk_map
import com.optum.ove.common.utils.CommonRuntimeVariables

import java.sql.Timestamp

class AllergyTableInfoTest extends QueryTestFramework {

  behavior of "ALLERGY"

  import spark.implicits._

  val runtimeVariables = CommonRuntimeVariables(setupDtm = Timestamp.valueOf("2024-11-26 13:35:45.318").toLocalDateTime)

  val allergyDF = mkDataFrame(
    com.optum.oap.cdr.models.allergy(
      client_ds_id = 10628,
      localallergencd = "GABAPENTINOIDS / BENZODIAZEPINES",
      localallergentype = "10628.a.8",
      grp_mpi = "1390409554",
      onsetdate = Timestamp.valueOf("2019-10-03 18:06:00.000"),
      localstatus = "Test Local Status",
      localallergendesc = "GABAPENTINOIDS / BENZODIAZEPINES",
      dcc = "20601",
      encounterid = "c96aec0a6721377f64fcf6b1b49f95"
    )
  )

  val mapAllergenDF = mkDataFrame(
    map_allergen(
      mnemonic = "Test Local Status",
      cui = "CH002030"
    )
  )

  val mapAllergenTypeDF = mkDataFrame(
    map_allergen_type(
      localcode = "10628.a.8",
      cui = "CH002030"
    )
  )

  val mapAllergyStatusDF = mkDataFrame(
    map_allergy_status(
      mnemonic = "Test Local Status",
      cui = "CH002030"
    )
  )

  val mvHtsDomainConceptDF = mkDataFrame(
    mv_hts_domain_concept(
      concept_cui = "CH002030",
      concept_name = "Test Local Status"
    )
  )

  val refHtsDccCurrentDF = mkDataFrame(
    ref_hts_dcc_current(
      dcc = "20601",
      genericname = "Zavegepant",
      pcc = "166",
      pcc_label = "Mitotic inhibitors, taxanes"
    )
  )

  val xwalk_mapDF = mkDataFrame(
    xwalk_map(
      domain = "allergy",
      sourceAttribute = "clinicalStatus",
      sourceSystem = "cui",
      sourceCode = "CH002030",
      sourceDescription = "Active (Allergy Status)",
      targetSystem = "fhirClinicalStatus",
      targetDescription = "Active",
      targetCode = "active"
    ),
    xwalk_map(
      domain = "allergy",
      sourceAttribute = "category",
      sourceSystem = "cui",
      sourceCode = "CH002030",
      sourceDescription = "Resolved (Allergy Status)",
      targetSystem = "fhirClinicalStatus",
      targetDescription = "active",
      targetCode = "active"
    )
  )

  val loadedDependencies = Map(
    "ALLERGY_SRC" -> allergyDF,
    "MAP_ALLERGEN" -> mapAllergenDF,
    "MAP_ALLERGEN_TYPE" -> mapAllergenTypeDF,
    "MAP_ALLERGY_STATUS" -> mapAllergyStatusDF,
    "MV_HTS_DOMAIN_CONCEPT" -> mvHtsDomainConceptDF,
    "REF_HTS_DCC_CURRENT" -> refHtsDccCurrentDF,
    "XWALK_MAP" -> xwalk_mapDF
  )


  val expectedOutput = Seq(
    Allergy(
      id = Identifier.createIdentifier("10628GABAPENTINOIDS / BENZODIAZEPINES10628.a.813904095542019-10-03 18:06:00.000Test Local StatusGABAPENTINOIDS / BENZODIAZEPINES20601c96aec0a6721377f64fcf6b1b49f95", "10628", "usual", CodeableConcept.createCodeableConcept(Seq(Coding("CDR:10628", "concatenated", "a concated key as there is no PK on the data")))),
      meta = Meta.createMeta(Timestamp.valueOf("2024-11-26 13:35:45.318")),
      ids = null,
      clinicalStatus = CodeableConcept.createCodeableConcept(Seq(Coding("CDR:10628", "active"))),
      verificationStatus = null,
      `type` = CodeableConcept.createCodeableConcept(Seq(Coding(null, "allergy"))),
      category = CodeableConcept.createCodeableConcept(Seq(Coding("CDR:10628", "active"))),
      criticality = null,
      code = CodeableConcept.createCodeableConcept(Seq(Coding("CDR:10628", "GABAPENTINOIDS / BENZODIAZEPINES", "GABAPENTINOIDS / BENZODIAZEPINES"), null, Coding("DCC", "20601", "Zavegepant"), null)),
      patient = Reference.createReference(null, "patient", "1390409554", null, null, "CDR"),
      encounter = Reference.createReference(null, "encounter", "c96aec0a6721377f64fcf6b1b49f95", null, null, "CDR:10628"),
      onset = TimeType.createTimeType(null, null, null),
      recordedDate = Timestamp.valueOf("2019-10-03 18:06:00.000"),
      recorder = null,
      lastOccurrence = null,
      notes = null,
      extension = null
    )
  )

  testQuery(
    testName = "have expected output given input",
    query = AllergyTableInfo,
    inputs = loadedDependencies,
    expectedOutput = expectedOutput,
    runtimeVariables = runtimeVariables
  )
}
