pluginManagement {
    plugins {
        id("io.github.sgtsilvio.gradle.maven-central-publishing") version "0.4.1"
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