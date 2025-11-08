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
                /*listOf(
                    file("src/main/asn1/MAP-BS-Code.asn"),
                    file("src/main/asn1/MAP-TS-Code.asn"),
                    file("src/main/asn1/MAP-SS-Code.asn"),
                    file("src/main/asn1/MAP-ExtensionDataTypes.asn"),
                    file("src/main/asn1/MAP-CommonDataTypes.asn"),
                    file("src/main/asn1/MAP-SS-DataTypes.asn"),
                    file("src/main/asn1/MAP-OM-DataTypes.asn"),
                    file("src/main/asn1/MAP-ER-DataTypes.asn"),
                    file("src/main/asn1/MAP-SM-DataTypes.asn"),
                    file("src/main/asn1/MAP-MS-DataTypes.asn"),
                    file("src/main/asn1/MAP-LCS-DataTypes.asn"),
                    file("src/main/asn1/MAP-GR-DataTypes.asn"),
                    file("src/main/asn1/MAP-CH-DataTypes.asn"),
                    file("src/main/asn1/SS-DataTypes.asn"),
                    file("src/main/asn1/CS1-DataTypes.asn"),
                    file("src/main/asn1/CS2-DataTypes.asn"),
                    //file("src/main/asn1/Remote-Operations-Useful-Definitions.asn"),
                    //file("src/main/asn1/Remote-Operations-Information-Objects.asn"),
                    //file("src/main/asn1/Remote-Operations-Generic-ROS-PDUs.asn"),
                    //file("src/main/asn1/MAP-Errors.asn"),
                    //file("src/main/asn1/MAP-LocationServiceOperations.asn").
                    //file("src/main/asn1/MAP-Group-Call-Operations.asn")
                )
            )*/
            outputDir.set(layout.buildDirectory.dir("generated/sources/asn1/$name"))
        }
    }
}

dependencies {
    api(libs.asn1.bean)
}

