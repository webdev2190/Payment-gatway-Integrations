import com.optum.insights.smith.fhir.Location
import com.optum.insights.smith.fhir.datatypes._
import com.optum.oap.sparkdataloader._
import com.optum.ove.common.utils._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._
import org.apache.spark.sql._
import java.sql.Timestamp

object LocationApptTableInfo extends BaseTableInfo[Location] {

  override def name: String = "LOCATION_APPT"

  override def dependsOn: Set[String] = Set(
    "zh_appointment_location",
    "zh_location_attributes"
  )

  override protected def createDataFrame(sparkSession: SparkSession, 
                                      loadedDependencies: Map[String, DataFrame],
                                      udfMap: Map[String, UserDefinedFunctionForDataLoader],
                                      runtimeVariables: RuntimeVariables): DataFrame = {

    import sparkSession.implicits._

    val setupDtmTimestamp = Timestamp.valueOf(CommonRuntimeVariables(runtimeVariables).setupDtm)

    val zhApptLocDF = loadedDependencies("zh_appointment_location")
      .select(
        $"client_ds_id",
        $"location_id",
        $"location_name",
        $"address_line1",
        $"city",
        $"state",
        $"postal_code"
      ).distinct()

    val zhLocAttrDF = loadedDependencies("zh_location_attributes")
      .select(
        $"location_id",
        $"location_type",
        $"operational_status"
      )

    val createIdentifierUDF = udf((system: String, value: String) => 
      Identifier(
        use = null,
        `type` = null,
        system = system,
        value = value,
        period = null
      )
    )

    val createAddressUDF = udf((line1: String, city: String, state: String, postalCode: String) => 
      Address(
        use = Coding("http://hl7.org/fhir/address-use", "work", "Work", null, true),
        `type` = Coding("http://hl7.org/fhir/address-type", "physical", "Physical", null, true),
        line = List(line1),
        city = city,
        district = "",
        state = state,
        postalCode = postalCode,
        country = "US",
        period = null
      )
    )

    val finalDF = zhApptLocDF.as("A")
      .join(zhLocAttrDF.as("B"), $"A.location_id" === $"B.location_id", "left")
      .select(
        createIdentifierUDF(concat(lit("CDR:"), $"A.client_ds_id"), $"A.location_id").as("id"),
        typedLit(rowToMeta(lit(setupDtmTimestamp))).as("meta"),
        $"A.location_name".as("name"),
        when($"B.operational_status".isNotNull, 
          Coding(
            system = "http://hl7.org/fhir/location-status",
            code = $"B.operational_status",
            display = $"B.operational_status",
            version = null,
            validated = true
          )
        ).otherwise(lit(null)).as("operationalStatus"),
        createAddressUDF($"A.address_line1", $"A.city", $"A.state", $"A.postal_code").as("address"),
        when($"B.location_type".isNotNull,
          array(
            CodeableConcept(
              coding = List(
                Coding(
                  system = "http://hl7.org/fhir/v3/ServiceDeliveryLocationRoleType",
                  code = $"B.location_type",
                  display = $"B.location_type",
                  version = null,
                  validated = true
                )
              ),
              text = null
            )
          )
        ).otherwise(lit(null)).as("types"),
        lit(null).cast(ArrayType(Encoders.product[Identifier].schema)).as("ids"),
        lit(null).cast(StringType).as("status"),
        lit(null).cast(ArrayType(StringType)).as("alias"),
        lit(null).cast(ArrayType(Encoders.product[CodeableConcept].schema)).as("characteristics")
      )

    finalDF
  }
}

=====================================================================================================================
LOcationApptTableInfoTest

package com.optum.ove.common.etl.cdrbe

import com.optum.insights.smith.fhir.Location
import com.optum.insights.smith.fhir.datatypes._
import com.optum.oap.cdr.models._
import com.optum.ove.common.etl.framework.QueryTestFramework
import com.optum.ove.common.utils.CommonRuntimeVariables
import java.sql.Timestamp

class LocationApptTableInfoTest extends QueryTestFramework {

  behavior of "LOCATION_APPT"

  import spark.implicits._

  val runtimeVariables = CommonRuntimeVariables(setupDtm = Timestamp.valueOf("2024-01-01 00:00:00").toLocalDateTime)

  val zhApptLocDF = mkDataFrame(
    zh_appointment_location(
      client_ds_id = "CLIENT123",
      location_id = "LOC456",
      location_name = "Main Clinic",
      address_line1 = "123 Health St",
      city = "Wellness City",
      state = "WC",
      postal_code = "98765"
    )
  )

  val zhLocAttrDF = mkDataFrame(
    zh_location_attributes(
      location_id = "LOC456",
      location_type = "CLINIC",
      operational_status = "active"
    )
  )

  val loadedDependencies = Map(
    "zh_appointment_location" -> zhApptLocDF,
    "zh_location_attributes" -> zhLocAttrDF
  )

  val expectedOutput = Seq(
    Location(
      id = Identifier(
        system = "CDR:CLIENT123",
        value = "LOC456",
        use = null,
        `type` = null,
        period = null
      ),
      meta = Meta.createMeta(Timestamp.valueOf("2024-01-01 00:00:00")),
      name = "Main Clinic",
      operationalStatus = Coding(
        system = "http://hl7.org/fhir/location-status",
        code = "active",
        display = "active",
        version = null,
        validated = true
      ),
      address = Address(
        use = Coding("http://hl7.org/fhir/address-use", "work", "Work", null, true),
        `type` = Coding("http://hl7.org/fhir/address-type", "physical", "Physical", null, true),
        line = List("123 Health St"),
        city = "Wellness City",
        district = "",
        state = "WC",
        postalCode = "98765",
        country = "US",
        period = null
      ),
      types = List(
        CodeableConcept(
          coding = List(
            Coding(
              system = "http://hl7.org/fhir/v3/ServiceDeliveryLocationRoleType",
              code = "CLINIC",
              display = "CLINIC",
              version = null,
              validated = true
            )
          ),
          text = null
        )
      ),
      ids = null,
      status = null,
      alias = null,
      characteristics = null
    )
  )

  testQuery(
    testName = "should correctly transform appointment location data",
    query = LocationApptTableInfo,
    inputs = loadedDependencies,
    expectedOutput = expectedOutput,
    runtimeVariables = runtimeVariables
  )
}
