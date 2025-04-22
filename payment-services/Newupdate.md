import com.optum.insights.smith.fhir.Location
import com.optum.insights.smith.fhir.datatypes._
import com.optum.oap.sparkdataloader.{TableInfo,RuntimeVariables, UserDefinedFunctionForDataLoader}
import com.optum.ove.common.utils.CommonRuntimeVariables
import org.apache.spark.sql.functions._
import org.apache.spark.sql.{DataFrame, SparkSession, Encoders}

import java.sql.Timestamp

object L1LocationFacTableInfo extends TableInfo[Location] {

  override def name: String = "l1_location_facility"

  override def dependsOn: Set[String] = Set(
    "ZH_FACILITY",
    "ZH_FACILITY_ROLLUP"
  )

  override protected def createDataFrame(
                                          sparkSession: SparkSession,
                                          loadedDependencies: Map[String, DataFrame],
                                          udfMap: Map[String, UserDefinedFunctionForDataLoader],
                                          runtimeVariables: RuntimeVariables
                                        ): DataFrame = {
    createLocationFacDataFrame(sparkSession, loadedDependencies, udfMap, runtimeVariables)
  }

  private def createLocationFacDataFrame(
                                          sparkSession: SparkSession,
                                          loadedDependencies: Map[String, DataFrame],
                                          udfMap: Map[String, UserDefinedFunctionForDataLoader],
                                          runtimeVariables: RuntimeVariables
                                        ): DataFrame = {



    val ClientDsIdColumn = "Z.client_ds_id"
    val FacilityIdColumn = "Z.facilityid"
    val FacilityNameColumn = "Z.facilityname"
    val Address1Column = "Z.address1"
    val CityColumn = "Z.city"
    val StateColumn = "Z.state"
    val FacilityPostalCdColumn = "Z.facilitypostalcd"
    val MasterFacilityNameColumn = "Zh.master_facility_name"
    val SiteOfCareNameColumn = "Zh.siteofcare_name"
    val ZhFacilityIdColumn = "Zh.facility_id"

    val setupDtmTimestamp = Timestamp.valueOf(CommonRuntimeVariables(runtimeVariables).setupDtm)

    val zh_facilityDF = loadedDependencies("ZH_FACILITY").as("Z")
      .select(
        col(ClientDsIdColumn),
        col(FacilityIdColumn),
        col(FacilityNameColumn),
        col(Address1Column),
        col(CityColumn),
        col(StateColumn),
        col(FacilityPostalCdColumn)
      ).distinct()

    val zh_facility_rollupDF = loadedDependencies("ZH_FACILITY_ROLLUP").as("Zh")
      .select(
        col(ZhFacilityIdColumn),
        col(SiteOfCareNameColumn),
        col(MasterFacilityNameColumn)
      )

    val finalLocationFacDF = zh_facilityDF.as("Z")
      .join(zh_facility_rollupDF.as("Zh"), col(FacilityIdColumn) === col(ZhFacilityIdColumn), "left")
      .select(
        col(ClientDsIdColumn).cast("string").as("client_ds_id"),
        col(FacilityIdColumn).cast("string").as("facilityid"),
        col(FacilityNameColumn),
        col(Address1Column),
        col(CityColumn),
        col(StateColumn),
        col(FacilityPostalCdColumn),
        col(SiteOfCareNameColumn),
        col(MasterFacilityNameColumn)
      )
      .map(row => {
        Location(
          id = Identifier(
            use = null,
            `type` = null,
            system = s"CDR:${row.getAs[String]("client_ds_id")}",
            value = List(row.getAs[String]("facilityid"), row.getAs[String]("client_ds_id")).mkString("-"),
            period = null
          ),
          meta = Meta.createMeta(setupDtmTimestamp),
          ids = null,
          status = null,
          operationalStatus = null,
          name = row.getAs[String]("facilityname"),
          alias = List(row.getAs[String]("siteofcare_name"), row.getAs[String]("master_facility_name")).filter(_ != null),
          characteristics = null,
          description = null,
          types = null,
          telecoms = null,
          address = Address(
            use = null,
            `type` = null,
            line = List(row.getAs[String]("address1")),
            city = row.getAs[String]("city"),
            district = null,
            state = row.getAs[String]("state"),
            postalCode = row.getAs[String]("facilitypostalcd"),
            country = null,
            period = null

          ),
          physicalType = null,
          position = null,
          managingOrg = null,
          partOf = null,
          extensions = null
        )
      })(Encoders.product[Location])

    finalLocationFacDF.toDF()

  }
}

===================================================================================
LocatioFacTableInfotest Code

import com.optum.insights.smith.fhir.Location
import com.optum.insights.smith.fhir.datatypes._
import com.optum.oap.cdr.models.{zh_facility,zh_facility_rollup}
import com.optum.ove.common.etl.framework.QueryTestFramework
import com.optum.ove.common.utils.CommonRuntimeVariables

import java.sql.Timestamp

class L1LocationFacTableInfoTest extends QueryTestFramework {

  behavior of "L1LocationFacTableInfo"

  import spark.implicits._

  val runtimeVariables = CommonRuntimeVariables(setupDtm = Timestamp.valueOf("2024-11-26 13:35:45.318").toLocalDateTime)

  val zhFacilityDF = mkDataFrame(
    zh_facility(
      client_ds_id = 10628,
      facilityid = "FAC123",
      facilityname = "Test Facility",
      address1 = "123 Test St",
      city = "Test City",
      state = "TS",
      facilitypostalcd = "12345"
    )
  )

  val zhFacilityRollupDF = mkDataFrame(
    zh_facility_rollup(
      client_ds_id = 10628,
      facility_id = "FAC123",
      siteofcare_name = "Test Site of Care",
      master_facility_name = "Master Facility"
    )
  )

  val loadedDependencies = Map(
    "ZH_FACILITY" -> zhFacilityDF,
    "ZH_FACILITY_ROLLUP" -> zhFacilityRollupDF
  )

  val expectedOutput = Seq(
    Location(
      id = Identifier(
        use = null,
        `type` = null,
        system = "CDR:10628",
        value = "FAC123-10628",
        period = null
        // assigner = null
      ),
      meta = Meta.createMeta(Timestamp.valueOf("2024-11-26 13:35:45.318")),
      ids = null,
      status = null,
      operationalStatus = null,
      name = ("Test Facility"),
      alias = List("Test Site of Care", "Master Facility"),
      characteristics = null,
      description = null,
      types = null,
      telecoms = null,
      address = Address(
        use = null,
        `type` = null,
        line = List("123 Test St"),
        city = "Test City",
        district = null,
        state = "TS",
        postalCode = "12345",
        country = null,
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
    query = L1LocationFacTableInfo,
    inputs = loadedDependencies,
    expectedOutput = expectedOutput,
    runtimeVariables = runtimeVariables
  )

}

===========================================================================
LocationApptTableInfo

import com.optum.insights.smith.fhir.Location
import com.optum.insights.smith.fhir.datatypes._
import com.optum.oap.sparkdataloader.{TableInfo, RuntimeVariables, UserDefinedFunctionForDataLoader}
import com.optum.ove.common.utils.CommonRuntimeVariables
import org.apache.spark.sql.functions._
import org.apache.spark.sql.{DataFrame, SparkSession, Encoders}

import java.sql.Timestamp

object L1LocationApptTableInfo extends TableInfo[Location] {

  override def name: String = "l1_location_appointment"

  override def dependsOn: Set[String] = Set("ZH_APPT_LOCATION")

  override protected def createDataFrame(
    sparkSession: SparkSession,
    loadedDependencies: Map[String, DataFrame],
    udfMap: Map[String, UserDefinedFunctionForDataLoader],
    runtimeVariables: RuntimeVariables
  ): DataFrame = {
    createLocationApptDataFrame(sparkSession, loadedDependencies, udfMap, runtimeVariables)
  }

  private def createLocationApptDataFrame(
    sparkSession: SparkSession,
    loadedDependencies: Map[String, DataFrame],
    udfMap: Map[String, UserDefinedFunctionForDataLoader],
    runtimeVariables: RuntimeVariables
  ): DataFrame = {

    val ClientDsIdColumn = "Z.client_ds_id"
    val LocationIdColumn = "Z.locationid"
    val LocationNameColumn = "Z.locationname"
    val Address1Column = "Z.address1"
    val CityColumn = "Z.city"
    val StateColumn = "Z.state"
    val PostalCodeColumn = "Z.zipcode"

    val setupDtmTimestamp = Timestamp.valueOf(CommonRuntimeVariables(runtimeVariables).setupDtm)

    val zhApptLocationDF = loadedDependencies("ZH_APPT_LOCATION").as("Z")
      .select(
        col(ClientDsIdColumn),
        col(LocationIdColumn),
        col(LocationNameColumn),
        col(Address1Column),
        col(CityColumn),
        col(StateColumn),
        col(PostalCodeColumn)
      ).distinct()

    val finalLocationApptDF = zhApptLocationDF
      .select(
        col(ClientDsIdColumn).cast("string").as("client_ds_id"),
        col(LocationIdColumn).cast("string").as("locationid"),
        col(LocationNameColumn).as("locationname"),
        col(Address1Column).as("address1"),
        col(CityColumn).as("city"),
        col(StateColumn).as("state"),
        col(PostalCodeColumn).as("zipcode")
      )
      .map(row => {
        Location(
          id = Identifier(
            use = null,
            `type` = null,
            system = s"CDR:${row.getAs[String]("client_ds_id")}APPTLOC",
            value = row.getAs[String]("locationid"),
            period = null
          ),
          meta = Meta.createMeta(setupDtmTimestamp),
          ids = null,
          status = "active",
          operationalStatus = null,
          name = row.getAs[String]("locationname"),
          alias = List.empty[String],
          characteristics = null,
          description = null,
          types = List(CodeableConcept(
            coding = List(Coding(
              system = "http://www.hl7.org/fhir/R4/codesystem-service-place.html",
              code = "office",
              display = "Office"
            )),
            text = null
          )),
          telecoms = null,
          address = Address(
            use = "work",
            `type` = "physical",
            line = List(row.getAs[String]("address1")),
            city = row.getAs[String]("city"),
            district = null,
            state = row.getAs[String]("state"),
            postalCode = row.getAs[String]("zipcode"),
            country = "US",
            period = null
          ),
          physicalType = CodeableConcept(
            coding = List(Coding(
              system = "http://terminology.hl7.org/CodeSystem/location-physical-type",
              code = "bu",
              display = "Building"
            )),
            text = null
          ),
          position = null,
          managingOrg = null,
          partOf = null,
          extensions = null
        )
      })(Encoders.product[Location])

    finalLocationApptDF.toDF()
  }
}

=============================================
LocationApptTableInfotest

import com.optum.insights.smith.fhir.Location
import com.optum.insights.smith.fhir.datatypes._
import com.optum.oap.cdr.models.zh_appt_location
import com.optum.ove.common.etl.framework.QueryTestFramework
import com.optum.ove.common.utils.CommonRuntimeVariables

import java.sql.Timestamp

class L1LocationApptTableInfoTest extends QueryTestFramework {

  behavior of "L1LocationApptTableInfo"

  import spark.implicits._

  val runtimeVariables = CommonRuntimeVariables(setupDtm = Timestamp.valueOf("2024-11-26 13:35:45.318").toLocalDateTime)

  val zhApptLocationDF = mkDataFrame(
    zh_appt_location(
      client_ds_id = 10628,
      locationid = "LOC456",
      locationname = "Test Appointment Location",
      address1 = "456 Appointment Ave",
      city = "Appointment City",
      state = "AP",
      zipcode = "67890"
    )
  )

  val loadedDependencies = Map(
    "ZH_APPT_LOCATION" -> zhApptLocationDF
  )

  val expectedOutput = Seq(
    Location(
      id = Identifier(
        use = null,
        `type` = null,
        system = "CDR:10628APPTLOC",
        value = "LOC456",
        period = null
      ),
      meta = Meta.createMeta(Timestamp.valueOf("2024-11-26 13:35:45.318")),
      ids = null,
      status = "active",
      operationalStatus = null,
      name = "Test Appointment Location",
      alias = List.empty[String],
      characteristics = null,
      description = null,
      types = List(CodeableConcept(
        coding = List(Coding(
          system = "http://www.hl7.org/fhir/R4/codesystem-service-place.html",
          code = "office",
          display = "Office"
        )),
        text = null
      )),
      telecoms = null,
      address = Address(
        use = "work",
        `type` = "physical",
        line = List("456 Appointment Ave"),
        city = "Appointment City",
        district = null,
        state = "AP",
        postalCode = "67890",
        country = "US",
        period = null
      ),
      physicalType = CodeableConcept(
        coding = List(Coding(
          system = "http://terminology.hl7.org/CodeSystem/location-physical-type",
          code = "bu",
          display = "Building"
        )),
        text = null
      ),
      position = null,
      managingOrg = null,
      partOf = null,
      extensions = null
    )
  )

  testQuery(
    testName = "should transform appointment location data correctly",
    query = L1LocationApptTableInfo,
    inputs = loadedDependencies,
    expectedOutput = expectedOutput,
    runtimeVariables = runtimeVariables
  )
}

============================================================
Error Note

C:\Users\ahaldar1\repo\ove-common-etl\common-etl\src\main\scala\com\optum\ove\common\etl\cdrbe\LocationApptTableInfo.scala:89:18
too many arguments (2) for method apply: (coding: Seq[com.optum.insights.smith.fhir.datatypes.Coding])com.optum.insights.smith.fhir.datatypes.CodeableConcept in object CodeableConcept
Note that 'text' is not a parameter name of the invoked method.
            text = null
