package io.github.codicis.gsma.cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.Context
import com.github.ajalt.clikt.core.main
import com.github.ajalt.clikt.core.subcommands


/**
 * Represents the file format used for various operations, such as encoding,
 * decoding, validating, or converting files within the GSMA context.
 *
 * The formats currently supported are:
 * - `tap`: Represents the TAP file format.
 * - `rap`: Represents the RAP file format.
 *
 * Examples:
 *  - gsma-cli decode --format tap --input file.tap --output file.txt
 *  - gsma-cli encode --format rap --input file.json --output file.rap
 *  - gsma-cli validate --format tap --input file.tap
 *  - gsma-cli convert --from tap --to avro --input file.tap --output file.avro
 */
enum class Format { Json, Text, `Json-pretty` }

class GsmaCli : CliktCommand(name = "gsma-cli") {
    override fun help(context: Context): String {
        return """
            GSMA Interchange CLI Tool
        """.trimIndent()
    }

    init {
        subcommands(
            Tap()
        )
    }

    override fun run() = Unit
}

fun main(args: Array<String>) = GsmaCli().main(args)