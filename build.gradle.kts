plugins {
    kotlin("jvm") version "1.5.21"
    kotlin("plugin.serialization") version "1.5.21"
    id("com.palantir.graal") version "0.9.0"
}

var templrMainClassName = "com.github.bvfnbk.templr.MainKt"

group = "com.github.bvfnbk"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.2.2")
    implementation("io.insert-koin:koin-core:3.1.2")
    implementation("com.github.ajalt.clikt:clikt:3.2.0")
    implementation("org.freemarker:freemarker:2.3.31")

    // Test
    testImplementation("io.insert-koin:koin-test:3.1.2")

    // Maven dependency issues (spek-runtime-2.0.16 not found...)
    testImplementation("org.spekframework.spek2:spek-dsl-jvm:2.0.15")
    testRuntimeOnly("org.spekframework.spek2:spek-runner-junit5:2.0.15")

    testImplementation("com.willowtreeapps.assertk:assertk-jvm:0.24")
    testImplementation("io.mockk:mockk:1.12.0")
    testRuntimeOnly("org.jetbrains.kotlin:kotlin-reflect:1.5.21")

}

tasks {
    test {
        useJUnitPlatform()
    }
}
tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

graal {
    mainClass(templrMainClassName)
    outputName("templr")
}
