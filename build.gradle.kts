plugins {
    id("java")
    id("org.springframework.boot") version "3.3.3"
    id("io.spring.dependency-management") version "1.1.6"
}

group = "com.saaenmadsen.shardworld"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}


repositories {
    mavenCentral()
    maven {
        url = uri("https://repo.akka.io/maven")
    }
    maven {
        url = uri("https://pkgs.dev.azure.com/MicrosoftDeviceSDK/DuoSDK-Public/_packaging/Duo-SDK-Feed/maven/v1")
        name = "Duo-SDK-Feed"
    }
    maven { url = uri("https://repo.spring.io/milestone") }

}

val versions_ScalaBinary by extra { "2.13" }
val versions_AkkaVersion by extra { "2.9.3" }


val springCloudVersion by extra { "2023.0.3" }
val springCloudAzureVersion by extra { "5.15.0" }
val ollamaVersion by extra { "0.35.0" }

extra["springAiVersion"] = "1.0.0-M3"




dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("com.typesafe.akka:akka-actor-typed_${versions_ScalaBinary}:${versions_AkkaVersion}")
    implementation("com.typesafe.akka:akka-persistence-typed_${versions_ScalaBinary}:${versions_AkkaVersion}")
    implementation("com.typesafe.akka:akka-bom_${versions_ScalaBinary}:${versions_AkkaVersion}")

    testImplementation("com.typesafe.akka:akka-persistence-testkit_${versions_ScalaBinary}:${versions_AkkaVersion}")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    // Azure Connectivity
    implementation("com.azure.spring:spring-cloud-azure-starter")
    implementation("com.azure.spring:spring-cloud-azure-starter-jdbc-mysql")
    implementation("com.azure:azure-messaging-servicebus")
    implementation("com.azure:azure-identity")

    // Ollama AI:
    testImplementation("org.testcontainers:testcontainers:1.19.0")
    implementation("dev.langchain4j:langchain4j:${ollamaVersion}")
    implementation("dev.langchain4j:langchain4j-ollama:${ollamaVersion}")


    //Not used:
    //    implementation ("org.slf4j.slf4j-log4j12")
    //    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    //    testImplementation("org.junit.jupiter:junit-jupiter")

}

dependencyManagement { 
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:$springCloudVersion")
        mavenBom("com.azure.spring:spring-cloud-azure-dependencies:$springCloudAzureVersion")
        mavenBom("org.springframework.ai:spring-ai-bom:${property("springAiVersion")}")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}