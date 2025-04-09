import com.optum.insights.smith.fhir.Location
import com.optum.insights.smith.fhir.datatypes.{Address, CodeableConcept, Extension, Identifier, Reference, ContactPoint, Coding, Period}
import com.optum.oap.sparkdataloader.{RuntimeVariables, UserDefinedFunctionForDataLoader}
import com.optum.ove.common.utils.ClinicalFhirDataTypeUtils.rowToMeta
import com.optum.ove.common.utils.{BaseTableInfo, CommonRuntimeVariables}
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types.{ArrayType, StringType}
import org.apache.spark.sql.{DataFrame, Encoders, SparkSession}

import java.sql.Timestamp

object LocationFacTableInfo extends BaseTableInfo[Location] {

  override def name: String = "LOCATION_FAC"

  override def dependsOn: Set[String] = Set(
    "zh_facility",
    "zh_facility_rollup"
  )

  override protected def createDataFrame(sparkSession: SparkSession, loadedDependencies: Map[String, DataFrame], udfMap: Map[String, UserDefinedFunctionForDataLoader], runtimeVariables: RuntimeVariables): DataFrame = {

    import sparkSession.implicits._

    val setupDtmTimestamp = Timestamp.valueOf(CommonRuntimeVariables(runtimeVariables).setupDtm)

    val zh_facilityDF = loadedDependencies("zh_facility").as("Z")
      .select(
        $"Z.client_ds_id",
        $"Z.facilityid",
        $"Z.facilityname",
        $"Z.address1",
        $"Z.city",
        $"Z.state",
        $"Z.facilitypostalcd"
      ).distinct()

    val zh_facility_rollupDF = loadedDependencies("zh_facility_rollup").as("Zh")
      .select(
        $"Zh.facility_id",
        $"Zh.siteofcare_name",
        $"Zh.master_facility_name"
      )

        val createIdentifierWithoutCodeableConceptUDF = udf((arg1: String, arg2: String, arg3: String, arg4: String, arg5: String, arg6: String, arg7: String, arg8: String) =>
          createIdentifierWithoutCodeableConcept(Seq(arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8))
        )

    def createIdentifierWithoutCodeableConcept(strings: Seq[String]): Identifier = {
      Identifier(
        use = null,
        `type` = null,
        system = strings(0),
        value = strings(1),
        period = null
        // assigner = null
      )
    }
        val createAliasUDF = udf((siteOfCare: String, masterFacility: String) => List(siteOfCare, masterFacility))
        val createAddressUDF = udf((address1: String, city: String, state: String, postalCode: String) => Address(
          Coding("useSystem", "useCode", "useDisplay", "useVersion", validated = true),
          Coding("typeSystem", "typeCode", "typeDisplay", "typeVersion", validated = true),
          List(address1),
          city,
          "district",
          state,
          postalCode,
          "country",
          Period(Timestamp.valueOf("2023-01-01 00:00:00"), Timestamp.valueOf("2023-12-31 23:59:59"))
        ))

    val finalLocationFacDF = zh_facilityDF.as("Z")
      .join(zh_facility_rollupDF.as("Zh"), $"Z.facilityid" === $"Zh.facility_id", "left")
      .select(
        createIdentifierWithoutCodeableConceptUDF(
          concat_ws("", col("Z.client_ds_id"), col("Z.facilityid"), col("Z.facilityname"), col("Z.address1"), col("Z.city"), col("Z.state"), col("Z.facilitypostalcd")),
          col("Z.client_ds_id"),
          lit(""),
          lit(""),
          concat(lit("CDR:"), col("Z.client_ds_id")),
          lit(""),
          lit(""),
          lit("")
        ).as("id"),
        typedLit(rowToMeta(lit(setupDtmTimestamp))).as("meta"),
        lit(null).cast(ArrayType(Encoders.product[Identifier].schema)).as("ids"),
        lit(null).cast(StringType).as("status"),
        lit(null).cast(Encoders.product[Coding].schema).as("operationalStatus"),
        col("Zh.master_facility_name").as("name"),
        createAliasUDF(col("Zh.siteofcare_name"), col("Zh.master_facility_name")).as("alias"),
        lit(null).cast(ArrayType(Encoders.product[CodeableConcept].schema)).as("characteristics"),
        lit(null).cast(StringType).as("description"),
        lit(null).cast(ArrayType(Encoders.product[CodeableConcept].schema)).as("types"),
        lit(null).cast(ArrayType(Encoders.product[ContactPoint].schema)).as("telecoms"),
        createAddressUDF(
          col("Z.address1"),
          col("Z.city"),
          col("Z.state"),
          col("Z.facilitypostalcd")
        ).as("address"),
        lit(null).cast(Encoders.product[CodeableConcept].schema).as("physicalType"),
        lit(null).cast(StringType).as("position"),
        lit(null).cast(Encoders.product[Reference].schema).as("managingOrg"),
        lit(null).cast(Encoders.product[Reference].schema).as("partOf"),
        lit(null).cast(ArrayType(Encoders.product[Extension].schema)).as("extensions")
      )

    finalLocationFacDF
  }
}


====================================================================================================


package com.optum.ove.common.etl.cdrbe

import com.optum.insights.smith.fhir.Location
import com.optum.insights.smith.fhir.datatypes._
import com.optum.oap.cdr.models._
import com.optum.ove.common.etl.framework.QueryTestFramework
import com.optum.ove.common.utils.CommonRuntimeVariables

import java.sql.Timestamp

class LocationFacTableInfoTest extends QueryTestFramework {

  behavior of "LOCATION_FAC"

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
    "zh_facility" -> zhFacilityDF,
    "zh_facility_rollup" -> zhFacilityRollupDF
  )

  val expectedOutput = Seq(
    Location(
      id = Identifier(
        use = null,
        `type` = null,
        system = "10628FAC123Test Facility123 Test StTest CityTS12345",
        value = "10628",
        period = null
        // assigner = null
      ),
      meta = Meta.createMeta(Timestamp.valueOf("2024-11-26 13:35:45.318")),
      ids = null,
      status = null,
      operationalStatus = null,
      name = ("Master Facility"),
      alias = List("Test Site of Care", "Master Facility"),
      characteristics = null,
      description = null,
      types = null,
      telecoms = null,
      address = Address(
        use = Coding("useSystem", "useCode", "useDisplay", "useVersion", validated = true),
        `type` = Coding("typeSystem", "typeCode", "typeDisplay", "typeVersion", validated = true),
        line = List("123 Test St"),
        city = "Test City",
        district = "district",
        state = "TS",
        postalCode = "12345",
        country = "country",
        period = Period(
          start = Timestamp.valueOf("2023-01-01 00:00:00"),
          end = Timestamp.valueOf("2023-12-31 23:59:59")
        )
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
    query = LocationFacTableInfo,
    inputs = loadedDependencies,
    expectedOutput = expectedOutput,
    runtimeVariables = runtimeVariables
  )
}

-----------------------------------------------------------
This is the LocationFac Data Mapping
id	C	E	T	element	datatype	datatype2	datatype3	datatype4		desc	comment/notes	cdr be table	column	ETL Notes	
				updated	DateTime					Time of last update	For the transaction layer, this is the date of the transaction.  For the current state, it's the date of the last transaction.				
				id	Identifier					Unique identifier of this particular location					
						use : String,								""	
						type: CodeableConcept								""	
							system: String								
							version: String								
							value: Code								
							display: String								
							validated: Boolean								
						system : String						zh_facility	client_ds_id	"CDR:"+client_ds_id	
						value : String						zh_facility	facilityid		
						period : Period								""	
							start: Timestamp								
							end: Timestamp								
				identifiers	List[Identifier]									""	
						use : String,									
						type: CodeableConcept									
							system: String								
							version: String								
							value: Code								
							display: String								
							validated: Boolean								
						system : String									
						value : String									
						period : Period									
							start: Timestamp								
							end: Timestamp								
				status	Code					The current status of the location	Active / Inactive			""	
						String									
				operationalStatus	Coding					The operation status of the location	Typically it's only for Beds and I don't think we'll have to encode that level for MVP.			""	
						system: String									
						version: String									
						value: Code									
						display: String									
						validated: Boolean									
				name	String					Human-readable name	Display-only, should not be used for computation.	zh_facility	facilityname		
				alias	List[String]										
												zh_facility_rollup	siteofcare_name	left join zh_facility to zh_facility_rollup on client_ds_id and facilityid = facility_id	
										Other ways of referring to the location	Display-only, should not be used for computation.	zh_facility_rollup	master_facility_name		
				characteristics	List[CodeableConcept]						http://hl7.org/fhir/location-characteristic, extensible.			""	
						system: String									
						version: String									
						value: Code									
						display: String									
						validated: Boolean									
				description	String					Human-readable, longer description of the location	Display-only, should not be used for computation.			""	
				types	List[CodeableConcept]						"http://www.hl7.org/fhir/R4/codesystem-service-place.html

At least one concept here should represents the ""place of service"" for this location e.g., ""office"""			""	
						system: String									
						version: String									
						value: Code									
						display: String									
						validated: Boolean									
				telcoms	List[ContactPoint]					Methods for contacting the location	Phone, email, fax, web site etc			""	
						system: String									
						value: String									
						use: String									
						rank: Int									
						period: Period									
				address	Address					Specific address of the location	The physical location				
						use: Coding								"work"	
						type: Coding								"physical"	
						line: Array of String						zh_facility	address1		
						city: String						zh_facility	city		
						district: String 								""	
						state: String						zh_facility	state		
						postalCode: String						zh_facility	facilitypostalcd		
						country: String								""	
						period: Period								""	
				physicalType	CodeableConcept					The physical type of location such as Room, Unit/Floor, Building, Campus				""	
						system: String									
						version: String									
						value: Code									
						display: String									
						validated: Boolean									
				position	String					Geo-spacial information (lat/long/alt) of the location	Not yet used,  datatype should be changed to more accurately represent the decimal values of lat/long/alt			""	
				managingOrganization	reference(Organization)					The organization that 				""	
						reference : string									
						type : string									
						identifier : identifier 									
							use : String,								
							type : CodeableConcept								
								system : String							
								version: String.							
								value: Code							
								display: String							
								validated: Boolean							
							system : String,								
							value : String,								
							period : Period								
								start: Timestamp							
								end: Timestamp							
				partOf	reference(Location)					Parent locations	Only supports hierarchical location definitions			""	
						reference : string									
						type : string									
						identifier : identifier 									
							use : String,								
							type : CodeableConcept								
								system : String							
								version: String.							
								value: Code							
								display: String							
								validated: Boolean							
							system : String,								
							value : String,								
							period : Period								
								start: Timestamp							
								end: Timestamp							
				extensions	List[Extension]					Arbitrary name/value pairs				""	
						URI: String,									
						value: String									
															
