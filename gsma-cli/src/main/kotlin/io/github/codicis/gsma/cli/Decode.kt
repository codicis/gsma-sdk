package io.github.codicis.gsma.cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.Context
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import com.github.ajalt.clikt.parameters.types.enum
import com.github.ajalt.clikt.parameters.types.file
import com.github.ajalt.clikt.parameters.types.path
import io.github.codicis.gsma.tap.TapFiles
import java.io.PrintWriter
import kotlin.system.exitProcess

class Decode : CliktCommand() {
    override fun help(context: Context): String {
        return "Decode binary GSMA file to  text"
    }

    private val input by option("-i", "--input", help = "Input file").path(mustExist = true).required()
    private val format by option("-f", "--format", help = "Output format (${Format.entries})").enum<Format>().required()
    private val output by option("-o", "--output", help = "Output file").file()

    override fun run() {
        echo("Decoding file: ${input.toUri()}")

        val conf = mutableMapOf("outputType" to "CONSOLE").apply {
            output?.let {
                echo("Writing output to: ${it.path}")
                put("filePath", it.absolutePath)
                put("outputType", "FILE")
            }
        }
        when (format) {
            Format.Json -> TapFiles.transform(input, "Json", conf.toMap())
            Format.`Json-pretty` -> {
                conf["prettyPrint"] = "true"
                TapFiles.transform(input, "Json", conf.toMap())
            }
            Format.Text -> {
                val writer = output?.printWriter() ?: PrintWriter(System.out)
                writer.println(TapFiles.read(input))
            }
        }
        exitProcess(0)
    }
}