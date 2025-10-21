plugins {
    id("java-library")
    id("maven-publish")
    id("java-test-fixtures")
    alias(libs.plugins.codicis.asn1)
    alias(libs.plugins.maven.central.publishing)
    signing
}

group = "io.github.codicis.gsma-sdk"
version = "0.1.3"


dependencies {
    api(libs.asn1.bean)
    testFixturesImplementation(libs.junit.jupiter.api)
    testFixturesImplementation(libs.junit.jupiter.params)
}

asn1 {
    model {
        register("compileAsnTap") {
            packageName.set("io.github.codicis.gsma")
            sourceFiles.from(fileTree("src/main/asn1"))
            outputDir.set(layout.buildDirectory.dir("generated/sources/asn1/$name"))
        }
    }
}

java {
    withJavadocJar()
    withSourcesJar()
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            pom {
                configureMetadata(
                    moduleName = "GSMA TAP Codec",
                    moduleDescription = "GSMA TAP 0312 encoder/decoder"
                )
            }
        }
    }
}
testing {
    suites {
        named("test", JvmTestSuite::class) {
            useJUnitJupiter(libs.versions.junit.get())
        }
    }
}

signing {
    useGpgCmd()
    sign(publishing.publications["mavenJava"])
}