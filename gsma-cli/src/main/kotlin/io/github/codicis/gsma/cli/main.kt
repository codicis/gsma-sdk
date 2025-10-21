package io.github.codicis.gsma.cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.Context
import com.github.ajalt.clikt.core.main
import com.github.ajalt.clikt.core.subcommands
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import com.github.ajalt.clikt.parameters.types.enum
import com.github.ajalt.clikt.parameters.types.file
import com.github.ajalt.clikt.parameters.types.path
import io.github.codicis.gsma.tap.TapFiles
import java.nio.file.StandardOpenOption


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
enum class Format { tap, rap }

class GsmaCli : CliktCommand(name = "gsma-cli") {
    override fun help(context: Context): String {
        return """
            GSMA Interchange CLI Tool
        """.trimIndent()
    }

    init {
        subcommands(
            Decode(),
            Encode(),
            Validate(),
            Convert(),
            Inspect(),
            Scheme()
        )
    }

    override fun run() = Unit
}

class Decode : CliktCommand() {
    override fun help(context: Context): String {
        return "Decode binary GSMA file to  text"
    }

    private val format by option("-f", "--format", help = "Input format (tap, rap)").enum<Format>().required()
    private val input by option("-i", "--input", help = "Input file").path(mustExist = true).required()
    private val output by option("-o", "--output", help = "Output file").file()

    override fun run() {
        echo("Decoding $format file: $input")
        output?.let { echo("Writing output to: ${it.path}") }
        val dataInterChange = TapFiles.read(input, StandardOpenOption.READ)
        echo(dataInterChange)
        if (output != null) {
            output!!.printWriter().use { writer -> writer.println(dataInterChange) }
            echo("Output written to: ${output!!.path}")
        } else {
            echo(dataInterChange)
        }
    }
}

class Encode : CliktCommand() {
    override fun help(context: Context): String {
        return "Encode structured input into GSMA format"
    }

    private val format by option("-f", "--format", help = "Output format (tap, rap)").enum<Format>().required()
    private val input by option("-i", "--input", help = "Input JSON file").file(mustExist = true).required()
    private val output by option("-o", "--output", help = "Output GSMA file").file()

    override fun run() {
        echo("Encoding to $format from: ${input.path}")
        output?.let { echo("Writing output to: ${it.path}") }
        // TODO: Wire into encoder logic
    }
}

class Validate : CliktCommand() {
    override fun help(context: Context): String {
        return "Validate file against schema/version"
    }

    private val format by option("-f", "--format", help = "Format to validate").enum<Format>().required()
    private val input by option("-i", "--input", help = "File to validate").file(mustExist = true).required()

    override fun run() {
        echo("Validating $format file: ${input.path}")
        // TODO: Wire into schema validation
    }
}

class Convert : CliktCommand(name = "convert") {
    override fun help(context: Context): String {
        return "Convert between formats (e.g., TAP → Avro)"
    }

    private val from by option("--from", help = "Source format").enum<Format>().required()
    private val to by option("--to", help = "Target format").enum<Format>().required()
    private val input by option("-i", "--input", help = "Input file").file(mustExist = true).required()
    private val output by option("-o", "--output", help = "Output file").file()

    override fun run() {
        echo("Converting from $from to $to: ${input.path}")
        output?.let { echo("Writing output to: ${it.path}") }

        // TODO: Wire into format conversion logic
    }
}

class Inspect : CliktCommand(name = "inspect") {
    override fun help(context: Context): String {
        return "Print header metadata, version, record count"
    }

    private val input by option("-i", "--input", help = "File to inspect").path(mustExist = true).required()

    override fun run() {
        echo("Inspecting file: $input")
        val chg = TapFiles.read(input, StandardOpenOption.READ)
        // TODO: Wire into header inspection logic
        echo(chg.transferBatch.batchControlInfo)
        echo(chg.transferBatch.callEventDetails.callEventDetail.size)
    }
}

class Scheme : CliktCommand(name = "schema") {
    override fun help(context: Context): String {
        return "List supported formats and schema versions"
    }

    override fun run() {
        echo("Supported formats: ${Format.tap}-3.11, ${Format.tap}-1.05")
    }
}

fun main(args: Array<String>) = GsmaCli().main(args)