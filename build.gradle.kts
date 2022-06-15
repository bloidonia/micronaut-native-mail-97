plugins {
    id("groovy") 
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("io.micronaut.application") version "3.4.1"
    id("io.micronaut.graalvm") version "3.4.1"
}

version = "0.1"
group = "micronaut"

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {
    annotationProcessor("info.picocli:picocli-codegen")

    implementation("commons-logging:commons-logging:1.2")
    implementation("info.picocli:picocli")
    implementation("io.micronaut:micronaut-jackson-databind")
    implementation(platform("io.micronaut.email:micronaut-email-bom:1.2.2-SNAPSHOT"))
    implementation("io.micronaut.email:micronaut-email-amazon-ses")
    implementation("io.micronaut.picocli:micronaut-picocli")
    implementation("jakarta.annotation:jakarta.annotation-api")
    implementation("io.micronaut:micronaut-validation")

    runtimeOnly("ch.qos.logback:logback-classic")
}


application {
    mainClass.set("micronaut.NativemailCommand")
}
java {
    sourceCompatibility = JavaVersion.toVersion("11")
    targetCompatibility = JavaVersion.toVersion("11")
}

micronaut {
    testRuntime("spock2")
    processing {
        incremental(true)
        annotations("micronaut.*")
    }
}



