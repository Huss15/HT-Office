plugins {
    java
    id("org.springframework.boot") version "3.5.4"
    id("io.spring.dependency-management") version "1.1.7"
    id("com.diffplug.spotless") version "8.0.0"
}

group = "com.hassuna.tech"
version = "0.0.1-SNAPSHOT"
description = "HT-Office"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    // Spring Boot Starters (Versionen werden vom Spring Boot Plugin verwaltet)
    implementation("org.springframework.boot:spring-boot-starter-web-services")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")

    // E-Rechnung und PDF-Erstellung
    implementation("org.mustangproject:library:2.19.0")
    implementation("com.microsoft.playwright:playwright:1.45.0")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.13")
//    implementation("io.github.openhtmltopdf:openhtmltopdf-pdfbox:1.1.6")

    // Laufzeit- und Entwicklungsumgebung
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    runtimeOnly("org.postgresql:postgresql")

    compileOnly("org.projectlombok:lombok:1.18.30")
    annotationProcessor("org.projectlombok:lombok:1.18.30")

    // Test-Abhängigkeiten
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.named("check") {
    dependsOn("spotlessCheck")
} // `build.gradle.kts`
spotless {
    encoding("UTF-8")

    java {
        target("src/**/*.java")
        googleJavaFormat("1.22.0").reflowLongStrings()
        importOrder("java", "javax", "org", "com", "")
        removeUnusedImports()
        trimTrailingWhitespace()
        endWithNewline()
    }

    kotlinGradle {
        target("**/*.gradle.kts")
        ktlint("1.3.1")
        trimTrailingWhitespace()
        endWithNewline()
    }
}

tasks.named("check") {
    dependsOn("spotlessCheck")
}
