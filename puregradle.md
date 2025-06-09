// Project metadata
group = 'com.optum.pure'
version = '1.0.0'
description = 'Java 21 Upgrade Project'

// Java 21 Configuration
java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

plugins {
    id 'java'
    id 'eclipse'
    id 'idea'
    id 'org.springframework.boot' version '3.2.5'
    id 'io.spring.dependency-management' version '1.1.4'
}

// Git branch logic for version
def gitBranch() {
    def branch = ""
    def proc = "git describe --all".execute()
    proc.in.eachLine { line -> branch = line }
    proc.err.eachLine { line -> println line }
    proc.waitFor()
    branch
}

println gitBranch()

if (gitBranch().equals("heads/master") || gitBranch().equals("remotes/origin/master")) {
    project.version = '2.0-SNAPSHOT'
} else {
    project.version = '1.0-SNAPSHOT'
}

repositories {
    maven {
        url 'https://repo1.uhc.com/artifactory/repoauth'
        credentials {
            username = "runx_ohhlload"
            password = "ohLOA35U"
        }
    }
    maven {
        url 'https://repo1.uhc.com/artifactory/libs-releases/'
    }
    mavenCentral()
}

dependencyManagement {
    imports {
        mavenBom 'com.amazonaws:aws-java-sdk-bom:1.12.734'
        mavenBom "org.springframework.boot:spring-boot-dependencies:3.2.5"
    }
}

sourceCompatibility = JavaVersion.VERSION_1_8
targetCompatibility = JavaVersion.VERSION_1_8

dependencies {
    annotationProcessor "org.projectlombok:lombok:1.18.22"

    implementation 'com.amazonaws:aws-java-sdk-s3:1.12.734'
    implementation 'org.apache.logging.log4j:log4j-api:2.22.0'
    implementation 'org.apache.logging.log4j:log4j-core:2.22.0'

    implementation 'com.fasterxml.jackson.core:jackson-annotations:2.16.2'
    implementation 'com.fasterxml.jackson.core:jackson-core:2.16.2'
    implementation 'com.fasterxml.jackson.core:jackson-databind:2.16.2'
    implementation 'org.json:json:20231013'
    implementation 'com.google.code.gson:gson:2.9.1'

    implementation 'javax.json:javax.json-api:1.0-b01'
    implementation 'org.glassfish:javax.json:1.1'

    implementation 'org.elasticsearch.client:elasticsearch-rest-high-level-client:7.17.15'

    implementation 'org.projectlombok:lombok:1.18.22'
    compileOnly 'org.projectlombok:lombok:1.18.30'
    annotationProcessor 'org.projectlombok:lombok:1.18.30'

    implementation 'org.springframework.kafka:spring-kafka'
    implementation 'org.springframework.boot:spring-boot-starter-web'

    implementation 'commons-io:commons-io:2.15.1'
    implementation 'org.yaml:snakeyaml:2.0'
    implementation 'javax.ws.rs:javax.ws.rs-api:2.1'

    // Unit Testing
    testImplementation 'junit:junit:4.13.2'
    testImplementation 'org.jmockit:jmockit:1.19'
    testImplementation 'org.springframework.boot:spring-boot-starter-test'
    testImplementation 'org.mockito:mockito-core:5.12.0'
    testImplementation 'org.junit.jupiter:junit-jupiter-api:5.10.2'
    testRuntimeOnly 'org.junit.jupiter:junit-jupiter-engine:5.10.2'
    testImplementation 'org.powermock:powermock-api-mockito:1.6.5'
    testImplementation 'org.powermock:powermock-module-junit4:1.6.5'
    testImplementation 'org.mockito:mockito-core:1.10.19'
}

test {
    useJUnitPlatform()
}
