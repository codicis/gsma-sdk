plugins {
    id("java-platform")
    id("maven-publish")
}

dependencies {
    constraints {
        api(project(":gsma-tap-codec"))
    }
}

publishing {
    publications {
        create<MavenPublication>("gsmaPlatform") {
            from(components["javaPlatform"])
        }
    }
}
