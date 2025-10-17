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

signing {
    useGpgCmd()
    sign(publishing.publications["gsmaPlatform"])
}