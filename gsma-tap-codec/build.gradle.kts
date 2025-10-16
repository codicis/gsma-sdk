plugins {
    `java-library`
    id("io.github.codicis.asn1") version "0.2.0"
}

dependencies {
    api("com.beanit:asn1bean:1.14.0")
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
testing {
    suites {
        named("test", JvmTestSuite::class) {
            useJUnitJupiter()
        }
    }
}