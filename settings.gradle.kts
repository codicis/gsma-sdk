pluginManagement {
    plugins {
        id("io.github.sgtsilvio.gradle.maven-central-publishing") version "0.4.1"
        id("io.github.codicis.asn1") version "0.2.0"
    }
}
dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
}
rootProject.name = "gsma-sdk"
include("gsma-tap-codec")
include("gsma-ber-processor")
include("gsma-sdk-platform")