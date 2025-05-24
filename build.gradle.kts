plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    id("org.springframework.boot") version "3.4.4"
    id("io.spring.dependency-management") version "1.1.7"
    kotlin("plugin.jpa") version "1.9.25"
}

group = "Paladin"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}


repositories {
    mavenCentral()
    maven("https://packages.confluent.io/maven/")
    maven {
        name = "GitHubPackages"
        url = uri("https://maven.pkg.github.com/usepaladin/avro-schemas")
        credentials {
            username = project.findProperty("gpr.user") as String? ?: System.getenv("GITHUB_USERNAME")
            password = project.findProperty("gpr.token") as String? ?: System.getenv("GITHUB_TOKEN")
        }
    }
}


dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-websocket")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.springframework.kafka:spring-kafka")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    runtimeOnly("org.postgresql:postgresql")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.18.3")
    implementation("com.fasterxml.jackson.core:jackson-core:2.18.3")
    implementation("com.fasterxml.jackson.core:jackson-annotations:2.18.3")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.18.3")
    implementation("io.micrometer:micrometer-core:1.12.0")

    // Logging
    implementation("io.github.oshai:kotlin-logging-jvm:7.0.0")
    implementation("org.slf4j:slf4j-api:2.0.16")

    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.boot:spring-boot-testcontainers")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testImplementation("org.springframework.kafka:spring-kafka-test")
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.testcontainers:kafka")
    testImplementation("org.testcontainers:postgresql")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}


kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
