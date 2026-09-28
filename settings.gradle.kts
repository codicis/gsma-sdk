pluginManagement {
    plugins {
        id("io.github.sgtsilvio.gradle.maven-central-publishing") version ("0.5.0")
        id("io.github.codicis.asn1") version "0.3.1"
    }
}
dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
}
rootProject.name = "gsma-sdk"
include("gsma-tap-codec")
include("gsma-sdk-platform")