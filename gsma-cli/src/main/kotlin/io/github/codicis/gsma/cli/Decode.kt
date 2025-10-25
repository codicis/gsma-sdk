package io.github.codicis.gsma.cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.Context
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import com.github.ajalt.clikt.parameters.types.enum
import com.github.ajalt.clikt.parameters.types.file
import com.github.ajalt.clikt.parameters.types.path
import io.github.codicis.gsma.json.TapJsonService
import io.github.codicis.gsma.tap.DataInterChange
import io.github.codicis.gsma.tap.TapFiles
import java.nio.file.StandardOpenOption

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
        val dataInterChange = TapFiles.read(input.toUri(), StandardOpenOption.READ)
        var result: String
        when (format) {
            Format.Json -> {
                result = TapJsonService().toJson<DataInterChange>(dataInterChange, false)
            }

            Format.`Json-pretty` -> {
                result = TapJsonService().toJson<DataInterChange>(dataInterChange, true)
            }

            Format.Text -> {
                result = dataInterChange.toString()
            }
        }
        if (output != null) {
            output!!.printWriter().use { writer -> writer.println(result) }
            echo("Output written to: ${output!!.path}")
        } else {
            echo(result)
        }
    }
}