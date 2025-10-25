plugins {
    kotlin("jvm") version "2.2.20"
    id("application")
    signing
}

version = "0.1.0"
group = "io.github.codicis.gsma-sdk"

application {
    mainClass.set("io.github.codicis.gsma.cli.MainKt")
}
dependencies {
    implementation(libs.clikt)
    implementation(project(":gsma-tap-codec"))
    implementation(project(":gsma-tap-json"))
}
