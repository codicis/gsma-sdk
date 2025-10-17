plugins {
    id("java-library")
    id("maven-publish")
    id("io.github.codicis.asn1") version "0.2.0"
    id("io.github.sgtsilvio.gradle.maven-central-publishing")
    signing
}

group="io.github.codicis.gsma-sdk"
version="0.1.1"

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

java {
    withJavadocJar()
    withSourcesJar()
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            pom {
                name = project.name
                description = "GSMA TAP 0312 encode/decode"
                url.set("https://github.com/codicis/gsma-sdk")

                organization {
                    name.set("Codicis")
                    url.set("https://github.com/codicis")
                }

                licenses {
                    license {
                        name = "The Apache License, Version 2.0"
                        url = "https://www.apache.org/licenses/LICENSE-2.0.txt"
                    }
                }
                developers {
                    developer {
                        id = "codicis"
                        name = "codecis"
                        email = "codecis@icloud.com"
                    }
                }
                scm {
                    connection = "scm:git:https://github.com/codicis/gsma-sdk.git"
                    developerConnection = "scm:git:ssh://github.com/codicis/gsma-sdk.git"
                    url = "https://github.com/codicis/gsma-sdk"
                }
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

signing {
    useGpgCmd()
    sign(publishing.publications["mavenJava"])
}