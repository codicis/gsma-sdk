import org.gradle.api.publish.maven.MavenPom

fun MavenPom.configureMetadata(moduleName: String, moduleDescription: String) {

    name.set(moduleName)
    description.set(moduleDescription)
    url.set("https://github.com/codicis/gsma-sdk")

    licenses {
        license {
            name.set("The Apache License, Version 2.0")
            url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
        }
    }

    developers {
        developer {
            id.set("codicis")
            name.set("codecis")
            email.set("codecis@icloud.com")
        }
    }

    scm {
        connection.set("scm:git:https://github.com/codicis/gsma-sdk.git")
        developerConnection.set("scm:git:ssh://github.com:codicis/gsma-sdk.git")
        url.set("https://github.com/codicis/gsma-sdk")
    }

}