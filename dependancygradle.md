// ---- PLUGIN MANAGEMENT: Ensures Spring Boot 3.x+ plugins can be resolved ----
pluginManagement {
    repositories {
        mavenCentral() // Main public repo for plugins
        gradlePluginPortal() // Official Gradle plugins
        maven {
            url 'https://repo1.uhc.com/artifactory/repoauth' // Your company Artifactory for plugins
            credentials {
                username = "runx_ohhlload"
                password = "ohLOA35U"
            }
        }
    }
}

// ---- PLUGINS: Modern Gradle plugin syntax ----
plugins {
    id 'java'
    id 'eclipse'
    id 'idea'
    id 'org.springframework.boot' version '3.5.0'
    id 'io.spring.dependency-management' version '1.1.7'
}

// ---- JAVA VERSION: Uses Java 21 toolchain ----
java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

// ---- REPOSITORIES: For dependencies, NOT plugins ----
repositories {
    mavenCentral()
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
}

// ---- VERSIONING: Sets project version based on git branch ----
def gitBranch() {
    def branch = ""
    def proc = "git describe --all".execute()
    proc.in.eachLine { line -> branch = line }
    proc.err.eachLine { line -> println line }
    proc.waitFor()
    branch
}
print gitBranch()

if (gitBranch().equals("heads/master") || gitBranch().equals("remotes/origin/master")) {
    project.version = '2.0-SNAPSHOT'
} else {
    project.version = '1.0-SNAPSHOT'
}

// ---- SPRING BOM: Ensures dependency compatibility ----
dependencyManagement {
    imports {
        mavenBom 'com.amazonaws:aws-java-sdk-bom:1.12.734'
        mavenBom "org.springframework.boot:spring-boot-dependencies:3.5.0" // Spring Boot BOM
    }
}

// ---- DEPENDENCIES: No duplicates, Java 21 compatible ----
dependencies {
    // Annotation processor
    annotationProcessor "org.projectlombok:lombok:1.18.22"

    // AWS SDK
    implementation 'com.amazonaws:aws-java-sdk-s3:1.12.734'

    // Logging
    implementation 'org.apache.logging.log4j:log4j-api:2.22.0'
    implementation 'org.apache.logging.log4j:log4j-core:2.22.0'

    // Spring & Kafka
    implementation 'org.springframework.kafka:spring-kafka'
    implementation 'org.springframework.boot:spring-boot-starter-web'

    // JSON & Serialization
    implementation 'javax.json:javax.json-api:1.0-b01'
    implementation 'org.glassfish:javax.json:1.1'
    implementation 'org.json:json:20231013'
    implementation 'com.google.code.gson:gson:2.9.1'
    implementation 'com.fasterxml.jackson.core:jackson-annotations:2.16.2'
    implementation 'com.fasterxml.jackson.core:jackson-core:2.16.2'
    implementation 'com.fasterxml.jackson.core:jackson-databind:2.16.2'

    // Elasticsearch
    implementation 'org.elasticsearch.client:elasticsearch-rest-high-level-client:7.17.15'

    // Utility libraries
    implementation 'commons-io:commons-io:2.8'
    implementation 'org.yaml:snakeyaml:2.0'

    // Javax WS RS
    implementation 'javax.ws.rs:javax.ws.rs-api:2.1'

    // Lombok (implementation for compile-time, annotationProcessor for IDE support)
    implementation 'org.projectlombok:lombok:1.18.22'

    // Unit Testing (JUnit, JMockit, Mockito, PowerMock)
    testImplementation 'junit:junit:4.13.2'
    testImplementation 'org.jmockit:jmockit:1.19'
    testImplementation 'org.springframework.boot:spring-boot-starter-test'
    testImplementation 'org.mockito:mockito-core:5.12.0'
    testImplementation 'org.junit.jupiter:junit-jupiter-api:5.10.2'
    testRuntimeOnly 'org.junit.jupiter:junit-jupiter-engine:5.10.2'
    testImplementation 'org.powermock:powermock-api-mockito:1.6.5'
    testImplementation 'org.powermock:powermock-module-junit4:1.6.5'
}

// ---- TEST TASK: Enables JUnit 5 platform ----
test {
    useJUnitPlatform()
}
