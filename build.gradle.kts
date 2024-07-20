plugins {
    java
    id("org.springframework.boot") version "3.3.1"
    id("io.qameta.allure") version "2.11.2"
}

tasks.withType(Wrapper::class) {
    gradleVersion = "8.9"
}

group = "io.dorum"
version = "1.0-SNAPSHOT"

val allureVersion = "2.28.0"
val aspectJVersion = "1.9.22.1"
val springVersion = "3.3.1"
val testNGVersion = "7.10.2"
val seleniumVersion = "4.22.0"
val lombokVersion = "1.18.34"
val bonigarciaVersion = "5.9.1"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    sourceCompatibility = "21"
    targetCompatibility = "21"
    options.compilerArgs.add("-parameters")
}

val agent: Configuration by configurations.creating {
    isCanBeConsumed = true
    isCanBeResolved = true
}

allure {
    version.set(allureVersion)
    adapter.autoconfigure.set(true)
    report.dependsOnTests.set(true)
}


tasks.test {
    useTestNG {
        suites("src/test/resources/testng.xml")
//        threadCount = 2
    }
    testLogging {
        events("passed", "skipped", "failed")
    }
    doFirst {
        jvmArgs = listOf(
            "-javaagent:${agent.singleFile}"
        )
    }
    doFirst {
        println("Starting testNG tests")
    }
    finalizedBy(tasks.allureReport)
}

tasks.allureReport() {
    doFirst {
        println("Creating allure report")
    }
    doLast {
        println("Completed allure report creation")
    }
}

dependencies {
    agent("org.aspectj:aspectjweaver:$aspectJVersion")
    // https://mvnrepository.com/artifact/org.aspectj/aspectjweaver
    runtimeOnly("org.aspectj:aspectjweaver:1.9.22.1")

    // https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-test
    implementation("org.springframework.boot:spring-boot-starter-test:$springVersion") {
        exclude(group = "org.springframework.boot", module = "spring-boot-starter-logging")
    }
    // https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-log4j2
    implementation("org.springframework.boot:spring-boot-starter-log4j2:$springVersion")

    // https://mvnrepository.com/artifact/org.testng/testng
    implementation("org.testng:testng:$testNGVersion")

    // https://mvnrepository.com/artifact/org.seleniumhq.selenium/selenium-java
    implementation("org.seleniumhq.selenium:selenium-java:$seleniumVersion")

    // https://mvnrepository.com/artifact/org.projectlombok/lombok
    implementation("org.projectlombok:lombok:$lombokVersion")
    annotationProcessor("org.projectlombok:lombok:$lombokVersion")
    testAnnotationProcessor("org.projectlombok:lombok:$lombokVersion")

    // https://mvnrepository.com/artifact/io.github.bonigarcia/webdrivermanager
    implementation("io.github.bonigarcia:webdrivermanager:$bonigarciaVersion")

    // https://mvnrepository.com/artifact/io.qameta.allure/allure-bom
    testImplementation(platform("io.qameta.allure:allure-bom:$allureVersion"))

    // https://mvnrepository.com/artifact/io.qameta.allure/allure-testng
    implementation("io.qameta.allure:allure-testng:$allureVersion")

    // https://mvnrepository.com/artifact/com.github.automatedowl/allure-environment-writer
    implementation("com.github.automatedowl:allure-environment-writer:1.0.0")
}

repositories {
    mavenCentral()
}