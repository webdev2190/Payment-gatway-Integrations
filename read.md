buildscript {
    repositories {
        maven {
            url 'https://repo1.uhc.com/artifactory/repoauth'
            credentials {
//                username = System.getenv(runx_ohhlload)
//                password = System.getenv(ohLOA35U)
                username = "runx_ohhlload";
                password = "ohLOA35U";
            }
        }
    }
//TODO Java 21 Up gradation and change the spring boot version to 3.2.5

    plugins {
        id 'org.springframework.boot' version '3.2.5'
        id 'io.spring.dependency-management' version '1.1.4'
        id 'java'
    }

    java {
        toolchain {
            languageVersion = JavaLanguageVersion.of(21)
        }
    }
    repositories {
        mavenCentral()
    }



    dependencies {
        classpath("org.springframework.boot:spring-boot-gradle-plugin:2.7.20.optum-2")
        classpath ("io.spring.gradle:dependency-management-plugin:1.0.15.RELEASE")
        classpath ("org.sonarsource.scanner.gradle:sonarqube-gradle-plugin:2.7")
    }
}

apply plugin: 'java'
apply plugin: 'eclipse'
apply plugin: 'idea'
apply plugin: 'org.springframework.boot'
apply plugin: 'io.spring.dependency-management'
apply plugin: 'org.sonarqube'

version =  '0.1.0'
bootJar {
    archiveBaseName.set('pure-service')
}

///springboot 2.7.x creates *-plain.jar; disable so that the build only create jar from bootJar
jar {
    enabled = false
}

repositories {
    maven {
        url 'https://repo1.uhc.com/artifactory/repoauth'
        credentials {
//                username = System.getenv(runx_ohhlload)
//                password = System.getenv(ohLOA35U)
            username = "runx_ohhlload";
            password = "ohLOA35U";
        }
    }
    maven {
        url 'https://repo1.uhc.com/artifactory/libs-releases/'
    }
    maven {
        url 'https://repo1.uhc.com/artifactory/UHG-Snapshots/com/optum/'
        metadataSources {
            artifact() }
    }
}

configurations {
    jacoco
    jacocoRuntime
}

sourceCompatibility = 1.8
targetCompatibility = 1.8

dependencyManagement {
    imports {
            mavenBom 'com.amazonaws:aws-java-sdk-bom:1.12.734'
    }
    resolutionStrategy {
        cacheChangingModulesFor 0, 'seconds'
    }
}

sonarqube {
    properties {
        property 'sonar.projectName', 'pure'
        property 'sonar.jacoco.reportPaths', 'build/jacoco/tests.exec'
    }
}


def gitBranch() {
    def branch = ""
    def proc = "git describe --all".execute()
    proc.in.eachLine { line -> branch = line }
    proc.err.eachLine { line -> println line }
    proc.waitFor()
    branch
}
print gitBranch()

dependencies {
    annotationProcessor "org.projectlombok:lombok:1.18.22"
    implementation 'org.apache.tomcat.embed:tomcat-embed-core:9.0.105'
    implementation 'org.springframework.boot:spring-boot-starter'
//Todo Start Change the spring boot version to 3.2.5
    implementation 'org.springframework.boot:spring-boot-starter'
    implementation 'org.springframework.boot:spring-boot-starter-web'
    implementation 'org.springframework.boot:spring-boot-starter-actuator'
//TODO End
    implementation group: 'org.springframework.kafka', name: 'spring-kafka',version: '2.9.11'
    implementation group: 'org.springframework', name: 'spring-web',version:'5.3.42.optum-1'
    implementation group: 'org.springframework', name: 'spring-webmvc',version:'5.3.42'
    implementation 'com.amazonaws:aws-java-sdk-s3:1.12.734'
    implementation 'org.apache.logging.log4j:log4j-api:2.17.1'
    implementation 'org.apache.logging.log4j:log4j-core:2.17.1'
    implementation group: 'javax.json', name: 'javax.json-api', version: '1.0-b01'
    implementation group: 'com.google.code.gson', name: 'gson'
    implementation group: 'org.glassfish', name: 'javax.json', version: '1.1'
    //elastic rest high level client
    implementation 'org.elasticsearch.client:elasticsearch-rest-high-level-client:7.17.15'
    implementation 'org.elasticsearch.client:elasticsearch-rest-client:7.17.15'
//    TODO New Version
    implementation 'org.elasticsearch.client:elasticsearch-rest-high-level-client:7.17.17'
    implementation 'org.elasticsearch:elasticsearch:7.17.17'

    // Logging (Log4j2) It is like track what your application is doing when it runs. kind of like a digital diary for your code.
    implementation 'org.apache.logging.log4j:log4j-api:2.22.1'
    implementation 'org.apache.logging.log4j:log4j-core:2.22.1'

    // JSON handling (Gson and Jackson) sending data in json format.
    implementation 'com.google.code.gson:gson:2.10.1'
    implementation 'com.fasterxml.jackson.core:jackson-core:2.17.0'
    implementation 'com.fasterxml.jackson.core:jackson-databind:2.17.0'
    implementation 'com.fasterxml.jackson.core:jackson-annotations:2.17.0'

    //JMockit dependencies (Testing)
    implementation 'com.google.code.gson:gson:2.10.1'
    implementation 'com.fasterxml.jackson.core:jackson-core:2.17.0'
    implementation 'com.fasterxml.jackson.core:jackson-databind:2.17.0'
    implementation 'com.fasterxml.jackson.core:jackson-annotations:2.17.0'

    //Kafka dependencies
    implementation group: 'org.apache.kafka', name: 'kafka-clients'
    implementation 'org.springframework.kafka:spring-kafka:3.1.2' //New Version
    implementation group: 'org.projectlombok', name: 'lombok', version: '1.18.22'
    implementation group: 'org.json', name: 'json', version: '20231013'
    testImplementation group: 'org.powermock', name: 'powermock-api-mockito', version: '1.6.5'
    testImplementation group: 'org.powermock', name: 'powermock-module-junit4', version: '1.6.5'
    testImplementation group: 'org.mockito', name: 'mockito-core', version: '1.10.19'
    jacoco group: 'org.jacoco', name: 'org.jacoco.ant', version: '0.7.9', classifier: 'nodeps'
    jacocoRuntime group: 'org.jacoco', name: 'org.jacoco.agent', version: '0.7.9', classifier: 'runtime'
    implementation group: 'com.fasterxml.jackson.core', name: 'jackson-annotations',version:'2.16.2'
    implementation group: 'com.fasterxml.jackson.core', name: 'jackson-core',version:'2.16.2'
    implementation group: 'com.fasterxml.jackson.core', name: 'jackson-databind',version:'2.16.2'
    testImplementation 'junit:junit:4.13.2'
    implementation group: 'commons-io', name: 'commons-io', version: '2.8'
//    implementation group: 'org.springframework.boot', name: 'spring-boot-starter-actuator'
    implementation 'org.xerial.snappy:snappy-java:1.1.10.4'
    implementation 'ch.qos.logback:logback-core:1.2.13'
    implementation 'ch.qos.logback:logback-classic:1.2.13'
    implementation group: 'org.yaml', name:'snakeyaml', version:'2.0'
    //PURE common lib dep
    if(gitBranch().equals("heads/master") || gitBranch().equals("remotes/origin/master")) {
        implementation group: 'ohhlpure.common.lib', name: 'ohhl-pure-shared-lib', version: '2.0-SNAPSHOT'
    }else {
        implementation group: 'ohhlpure.common.lib', name: 'ohhl-pure-shared-lib', version: '1.0-SNAPSHOT'
    }
}

task instrument(dependsOn: ['classes']) {
    ext.outputDir = file("${buildDir}/classes-instrumented")
    doLast {
        ant.taskdef(name: 'instrument',
                classname: 'org.jacoco.ant.InstrumentTask',
                classpath: configurations.jacoco.asPath)
        ant.instrument(destdir: outputDir) {
            fileset(dir: sourceSets.main.output.classesDirs.singleFile)
        }
    }
}

gradle.taskGraph.whenReady { graph ->
    if (graph.hasTask(instrument)) {
        tasks.withType(Test) {
            doFirst {
                systemProperty 'jacoco-agent.destfile', buildDir.path + '/jacoco/tests.exec'
                classpath = files(instrument.outputDir) + classpath + configurations.jacocoRuntime
            }
        }
    }
}

task report(dependsOn: ['instrument', 'test']) {
    doLast {
        ant.taskdef(name: 'report',
                classname: 'org.jacoco.ant.ReportTask',
                classpath: configurations.jacoco.asPath)
        ant.report() {
            executiondata {
                ant.file(file: buildDir.path + '/jacoco/tests.exec')
            }
            structure(name: 'Example') {
                classfiles {
                    fileset(dir: sourceSets.main.output.classesDirs.singleFile)
                }
                sourcefiles {
                    fileset(dir: 'src/main/java')
                }
            }
            xml(destfile: buildDir.path + '/reports/tests/jacocoTestReport.xml')
        }
    }
}

task copyDependencies {
    doLast {
        copy {
            from configurations.compile
            into 'dependencies'
        }
    }
}v
