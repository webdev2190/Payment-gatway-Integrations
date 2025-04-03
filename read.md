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

---------------------------------------------------------------
LocationApptTableInfoTest

import com.optum.insights.smith.fhir.Location
import com.optum.insights.smith.fhir.datatypes._
import com.optum.ove.common.etl.cdrbe.LocationApptTableInfo
import com.optum.ove.common.etl.framework.QueryTestFramework
import com.optum.ove.common.models._
import com.optum.ove.common.utils.CommonRuntimeVariables
import java.sql.Timestamp

class LocationApptTableInfoTest extends QueryTestFramework {

  behavior of "LOCATION_APPT"

  import spark.implicits._

  val runtimeVariables = CommonRuntimeVariables(
    setupDtm = Timestamp.valueOf("2024-11-26 13:35:45.318").toLocalDateTime
  )

  val zhApptLocationDF = mkDataFrame(
    zh_appt_location(
      client_ds_id = 101,
      locationid = "LOC001",
      locationname = "Apollo Clinic - Delhi",
      address1 = "123 Health Street",
      city = "New Delhi",
      state = "Delhi",
      zipcode = "110001",
      phone_number = "+91-9999999999",
      org_id = "ORG001",
      parent_location_id = "PLOC001"
    )
  )

  val loadedDependencies = Map(
    "zh_appt_location" -> zhApptLocationDF
  )

  val expectedOutput = Seq(
    Location(
      id = Identifier(
        use = "usual",
        `type` = CodeableConcept.createCodeableConcept(
          Seq(Coding("CDR:101APPTLOC", null, null))
        ),
        system = "CDR:101APPTLOC",
        value = "LOC001",
        period = null
      ),
      meta = Meta.createMeta(Timestamp.valueOf("2024-11-26 13:35:45.318")),
      name = "Apollo Clinic - Delhi",
      status = "active",
      description = "Apollo Clinic - Delhi",
      address = Address(
        line = Seq("123 Health Street"),
        city = "New Delhi",
        state = "Delhi",
        postalCode = "110001",
        country = "IN",
        use = "work",
        `type` = "physical"
      ),
      telecom = Seq(
        ContactPoint(system = "phone", value = "+91-9999999999", use = "work")
      ),
      types = Seq(
        CodeableConcept.createCodeableConcept(
          Seq(Coding("http://hl7.org/fhir/R4/codesystem-service-place.html", "11", "Office"))
        )
      ),
      physicalType = null,
      managingOrganization = Reference.createReference(null, "Organization", "ORG001", null, null, "CDR"),
      partOf = Reference.createReference(null, "Location", "PLOC001", null, null, "CDR"),
      updated = null,
      identifiers = Seq.empty,
      alias = Seq.empty,
      operationalStatus = null,
      extension = null
    )
  )

  testQuery(
    testName = "should return expected FHIR Location given the input zh_appt_location row",
    query = LocationApptTableInfo,
    inputs = loadedDependencies,
    expectedOutput = expectedOutput,
    runtimeVariables = runtimeVariables
  )
} 
 
