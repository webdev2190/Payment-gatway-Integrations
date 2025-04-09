import com.optum.insights.smith.fhir.Location
import com.optum.insights.smith.fhir.datatypes._
import com.optum.oap.sparkdataloader.{RuntimeVariables, UserDefinedFunctionForDataLoader}
import com.optum.ove.common.utils.{BaseTableInfo, CommonRuntimeVariables}
import com.optum.ove.common.utils.ClinicalFhirDataTypeUtils.rowToMeta
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types.{ArrayType, StringType}
import org.apache.spark.sql.{DataFrame, Encoders, SparkSession}

import java.sql.Timestamp

object LocationApptTableInfo extends BaseTableInfo[Location] {

  override def name: String = "LOCATION_APPT"

  override def dependsOn: Set[String] = Set("zh_appt_location")

  override protected def createDataFrame(sparkSession: SparkSession, loadedDependencies: Map[String, DataFrame], udfMap: Map[String, UserDefinedFunctionForDataLoader], runtimeVariables: RuntimeVariables): DataFrame = {

    import sparkSession.implicits._

    val setupDtmTimestamp = Timestamp.valueOf(CommonRuntimeVariables(runtimeVariables).setupDtm)

    val zhApptLocationDF = loadedDependencies("zh_appt_location").as("Z")
      .select(
        $"Z.client_ds_id",
        $"Z.locationid",
        $"Z.locationname",
        $"Z.address1",
        $"Z.city",
        $"Z.state",
        $"Z.zipcode"
      ).distinct()

    val createIdentifierUDF = udf((clientDsId: String, locationId: String) =>
      Identifier(
        use = null,
        `type` = null,
        system = s"CDR:$clientDsId:APPTLOC",
        value = locationId,
        period = null
      )
    )

    val createAddressUDF = udf((address1: String, city: String, state: String, postalCode: String) => Address(
      use = Coding("useSystem", "useCode", "useDisplay", "useVersion", validated = true),
      `type` = Coding("typeSystem", "typeCode", "typeDisplay", "typeVersion", validated = true),
      line = List(address1),
      city = city,
      district = "",
      state = state,
      postalCode = postalCode,
      country = "",
      period = null
    ))

    zhApptLocationDF.as("Z")
      .select(
        createIdentifierUDF(col("Z.client_ds_id"), col("Z.locationid")).as("id"),
        typedLit(rowToMeta(setupDtmTimestamp)).as("meta"),
        lit(null).cast(ArrayType(Encoders.product[Identifier].schema)).as("ids"),
        lit(null).cast(StringType).as("status"),
        lit(null).cast(Encoders.product[Coding].schema).as("operationalStatus"),
        col("Z.locationname").as("name"),
        lit(null).cast(ArrayType(StringType)).as("alias"),
        lit(null).cast(ArrayType(Encoders.product[CodeableConcept].schema)).as("characteristics"),
        lit(null).cast(StringType).as("description"),
        lit(null).cast(ArrayType(Encoders.product[CodeableConcept].schema)).as("types"),
        lit(null).cast(ArrayType(Encoders.product[ContactPoint].schema)).as("telecoms"),
        createAddressUDF(
          col("Z.address1"),
          col("Z.city"),
          col("Z.state"),
          col("Z.zipcode")
        ).as("address"),
        lit(null).cast(Encoders.product[CodeableConcept].schema).as("physicalType"),
        lit(null).cast(StringType).as("position"),
        lit(null).cast(Encoders.product[Reference].schema).as("managingOrg"),
        lit(null).cast(Encoders.product[Reference].schema).as("partOf"),
        lit(null).cast(ArrayType(Encoders.product[Extension].schema)).as("extensions")
      )
  }
}

===========================================================================
LocationApptTableInfoTest

import com.optum.insights.smith.fhir.Location
import com.optum.insights.smith.fhir.datatypes._
import com.optum.oap.cdr.models._
import com.optum.ove.common.etl.framework.QueryTestFramework
import com.optum.ove.common.utils.CommonRuntimeVariables
import org.apache.spark.sql.functions._

import java.sql.Timestamp

class LocationApptTableInfoTest extends QueryTestFramework {

  behavior of "LOCATION_APPT"

  import spark.implicits._

  val runtimeVariables = CommonRuntimeVariables(setupDtm = Timestamp.valueOf("2024-11-26 13:35:45.318").toLocalDateTime)

  val zhApptLocationDF = mkDataFrame(
    zh_appt_location(
      client_ds_id = 10628,
      locationid = "LOC123",
      locationname = "Appointment Location",
      address1 = "456 Appt St",
      city = "Appt City",
      state = "AS",
      zipcode = "67890"
    )
  )

  val loadedDependencies = Map(
    "zh_appt_location" -> zhApptLocationDF
  )

  val expectedOutput = Seq(
    Location(
      id = Identifier(
        use = null,
        `type` = null,
        system = "CDR:10628:APPTLOC",
        value = "LOC123",
        period = null
      ),
      meta = Meta.createMeta(Timestamp.valueOf("2024-11-26 13:35:45.318")),
      ids = null,
      status = null,
      operationalStatus = null,
      name = "Appointment Location",
      alias = null,
      characteristics = null,
      description = null,
      types = null,
      telecoms = null,
      address = Address(
        use = Coding("useSystem", "useCode", "useDisplay", "useVersion", validated = true),
        `type` = Coding("typeSystem", "typeCode", "typeDisplay", "typeVersion", validated = true),
        line = List("456 Appt St"),
        city = "Appt City",
        district = "",
        state = "AS",
        postalCode = "67890",
        country = "",
        period = null
      ),
      physicalType = null,
      position = null,
      managingOrg = null,
      partOf = null,
      extensions = null
    )
  )

  testQuery(
    testName = "have expected output given input",
    query = LocationApptTableInfo,
    inputs = loadedDependencies,
    expectedOutput = expectedOutput,
    runtimeVariables = runtimeVariables
  )
}

