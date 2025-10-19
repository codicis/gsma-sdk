plugins {
    id("java-platform")
    id("maven-publish")
    id("io.github.sgtsilvio.gradle.maven-central-publishing")
    signing
}

group="io.github.codicis.gsma-sdk"
version="0.1.1"

dependencies {
    constraints {
        api(project(":gsma-tap-codec"))
    }
}

publishing {
    publications {
        create<MavenPublication>("gsmaPlatform") {
            from(components["javaPlatform"])
            pom {
                configureMetadata(
                    moduleName = "GSMA SDK Platform BOM",
                    moduleDescription = "GSMA SDK Platform BOM for consistent dependency alignment across modules."
                )
            }
        }
    }
}

signing {
    useGpgCmd()
    sign(publishing.publications["gsmaPlatform"])
}