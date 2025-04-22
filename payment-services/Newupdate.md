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
          status = "active", // Defaulting to active as per common practice
          operationalStatus = null,
          name = row.getAs[String]("locationname"),
          alias = List.empty[String],
          characteristics = null,
          description = null,
          types = List(CodeableConcept(
            coding = List(Coding(
              system = "http://www.hl7.org/fhir/R4/codesystem-service-place.html",
              code = "office", // Defaulting to office, can be customized based on data
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
            country = "US", // Defaulting to US, can be parameterized
            period = null
          ),
          physicalType = CodeableConcept(
            coding = List(Coding(
              system = "http://terminology.hl7.org/CodeSystem/location-physical-type",
              code = "bu", // Defaulting to building
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
