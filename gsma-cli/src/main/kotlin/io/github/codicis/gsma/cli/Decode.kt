package io.github.codicis.gsma.cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.Context
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import com.github.ajalt.clikt.parameters.types.enum
import com.github.ajalt.clikt.parameters.types.file
import com.github.ajalt.clikt.parameters.types.path
import io.github.codicis.gsma.tap.TapFiles

class Decode : CliktCommand() {
    override fun help(context: Context): String {
        return "Decode binary GSMA file to  text"
    }

    private val input by option("-i", "--input", help = "Input file").path(mustExist = true).required()
    private val format by option("-f", "--format", help = "Input format (${Format.entries})").enum<Format>().required()
    private val output by option("-o", "--output", help = "Output file").file()

    override fun run() {
        echo("Decoding file: ${input.toUri()}")
        output?.let { echo("Writing output to: ${it.path}") }
        val dataInterChange = TapFiles.read(input)
        val result: String = when (format) {
            Format.Json -> TapFiles.transform(input, "Json", mapOf()) as String
            Format.`Json-pretty` -> TapFiles.transform(input, "Json", mapOf("prettyPrint" to true)) as String
            Format.Text -> dataInterChange.toString()
        }
        if (output != null) {
            output!!.printWriter().use { writer -> writer.println(result) }
            echo("Output written to: ${output!!.path}")
        } else {
            echo(result)
        }
    }
}