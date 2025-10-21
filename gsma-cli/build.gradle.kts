plugins {
    kotlin("jvm") version "2.2.20"
    id("application")
    signing
}

application {
    mainClass.set("io.github.codicis.gsma.cli.MainKt")
}
dependencies {
    implementation(libs.clikt)
    implementation(project(":gsma-tap-codec"))
}
