plugins {
    id("java-library")
    id("maven-publish")
    id("io.github.codicis.asn1") version "0.2.0"
}

dependencies {
    api(libs.asn1.bean)
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

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
        }
    }
}
testing {
    suites {
        named("test", JvmTestSuite::class) {
            useJUnitJupiter()
        }
    }
}