plugins {
    id("java-library")
    alias(libs.plugins.maven.central.publishing)
    signing
}

version = "0.1.0"
group = "io.github.codicis.gsma-sdk"


dependencies {
    implementation(libs.jackson.databind)
    implementation(project(":gsma-tap-codec"))
    testImplementation(testFixtures(project(":gsma-tap-codec")))
}

testing {
    suites {
        named("test", JvmTestSuite::class) {
            useJUnitJupiter(libs.versions.junit.get())
        }
    }
}