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
        register("compileAsn1") {
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
    repositories {
        maven {
            name = "CentralPortal"
            url = uri("https://central.sonatype.com/api/v1/publish")
            credentials {
                username = findProperty("sonatypeUsername") as String? ?: System.getenv("SONATYPE_USERNAME")
                password = findProperty("sonatypePassword") as String? ?: System.getenv("SONATYPE_PASSWORD")
            }
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