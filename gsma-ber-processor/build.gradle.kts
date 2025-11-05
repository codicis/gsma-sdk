plugins {
    id("java-library")
    id("maven-publish")
    id("io.github.sgtsilvio.gradle.maven-central-publishing")
    signing
}

group = "io.github.codicis.gsma-sdk"
version = "0.1.0"

dependencies {
    implementation(libs.javapoet)
    compileOnly(libs.google.auto.service.annotations)
    annotationProcessor(libs.google.auto.service)
}