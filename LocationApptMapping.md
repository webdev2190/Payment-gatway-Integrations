id	C	E	T	element	datatype	datatype2	datatype3	datatype4	desc	comment/notes	cdr be table	column	ETL Notes	
				updated	DateTime				Time of last update	For the transaction layer, this is the date of the transaction.  For the current state, it's the date of the last transaction.				
				id	Identifier				Unique identifier of this particular location					
						use : String,							""	
						type: CodeableConcept							""	
							system: String							
							version: String							
							value: Code							
							display: String							
							validated: Boolean							
						system : String					zh_appt_location	client_ds_id	"CDR:"+client_ds_id+"APPTLOC"	
						value : String					zh_appt_location	locationid		
						period : Period							""	
							start: Timestamp							
							end: Timestamp							
				identifiers	List[Identifier]								""	
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
				status	Code				The current status of the location	Active / Inactive			""	
						String								
				operationalStatus	Coding				The operation status of the location	Typically it's only for Beds and I don't think we'll have to encode that level for MVP.			""	
						system: String								
						version: String								
						value: Code								
						display: String								
						validated: Boolean								
				name	String				Human-readable name	Display-only, should not be used for computation.	zh_appt_location	locationname		
				alias	List[String]								""	
														
									Other ways of referring to the location	Display-only, should not be used for computation.				
				characteristics	List[CodeableConcept]					http://hl7.org/fhir/location-characteristic, extensible.			""	
						system: String								
						version: String								
						value: Code								
						display: String								
						validated: Boolean								
				description	String				Human-readable, longer description of the location	Display-only, should not be used for computation.			""	
				types	List[CodeableConcept]					"http://www.hl7.org/fhir/R4/codesystem-service-place.html

At least one concept here should represents the ""place of service"" for this location e.g., ""office"""			""	
						system: String								
						version: String								
						value: Code								
						display: String								
						validated: Boolean								
				telcoms	List[ContactPoint]				Methods for contacting the location	Phone, email, fax, web site etc			""	
						system: String								
						value: String								
						use: String								
						rank: Int								
						period: Period								
				address	Address				Specific address of the location	The physical location				
						use: Coding							"work"	
						type: Coding							"physical"	
						line: Array of String					zh_appt_location	address1		
						city: String					zh_appt_location	city		
						district: String 							""	
						state: String					zh_appt_location	state		
						postalCode: String					zh_appt_location	zipcode		
						country: String							""	
						period: Period							""	
				physicalType	CodeableConcept				The physical type of location such as Room, Unit/Floor, Building, Campus				""	
						system: String								
						version: String								
						value: Code								
						display: String								
						validated: Boolean								
				position	String				Geo-spacial information (lat/long/alt) of the location	Not yet used,  datatype should be changed to more accurately represent the decimal values of lat/long/alt			""	
				managingOrganization	reference(Organization)				The organization that 				""	
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
				partOf	reference(Location)				Parent locations	Only supports hierarchical location definitions			""	
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
				extensions	List[Extension]				Arbitrary name/value pairs				""	
						URI: String,								
						value: String								
