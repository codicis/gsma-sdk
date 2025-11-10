plugins {
    id("java-library")
    id("maven-publish")
    id("io.github.codicis.asn1")
    id("io.github.sgtsilvio.gradle.maven-central-publishing")
    signing
}

group = "io.github.codicis.gsma-sdk"
version = "0.1.1"

asn1 {
    model {
        register("compileAsn") {
            packageName.set("io.github.codicis.gsma")
            sourceFiles.from(fileTree("src/main/asn1"))
            outputDir.set(layout.buildDirectory.dir("generated/sources/asn1/$name"))
        }
    }
    model {
        register("compileGprsCdr") {
            packageName.set("io.github.codicis.gsma")
            sourceFiles.from(fileTree("src/main/asn1/gprscdr"))
            outputDir.set(layout.buildDirectory.dir("generated/sources/asn1/$name"))
        }
    }
}

dependencies {
    api(libs.asn1.bean)
}

