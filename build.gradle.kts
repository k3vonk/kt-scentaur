import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    val kotlinPluginVersion = "1.6.21"

    id("org.springframework.boot") version "2.7.0"
    kotlin("jvm") version kotlinPluginVersion
    kotlin("plugin.spring") version kotlinPluginVersion
    id("org.jlleitschuh.gradle.ktlint") version "10.3.0"
}

group = "com.gajyoung"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
}

dependencies {
    implementation(platform("org.springframework.boot:spring-boot-dependencies:2.7.0"))
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    kotlin("reflect")
    kotlin("kotlin-stdlib-jdk8")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
