package io.github.codicis.gsma.cli;

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.subcommands

class Tap : CliktCommand(name = "tap") {

    init {
        subcommands(
            Decode()
        )
    }

    override fun run() = Unit
}

