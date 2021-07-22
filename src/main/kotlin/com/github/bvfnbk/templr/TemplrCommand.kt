package com.github.bvfnbk.templr

import com.github.ajalt.clikt.completion.CompletionCandidates
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.context
import com.github.ajalt.clikt.output.CliktHelpFormatter
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.options.*
import com.github.ajalt.clikt.parameters.types.file
import com.github.bvfnbk.templr.api.TemplrApplication
import com.github.bvfnbk.templr.api.model.ApplicationArguments
import java.io.File
import java.nio.charset.Charset

/**
 * @author bvfnbk
 */
class TemplrCommand(private val application: TemplrApplication) : CliktCommand(
    help = "Runs the templr application. Writes the result to stdout by default."
) {
    init {
        context { helpFormatter = CliktHelpFormatter(showDefaultValues = true) }
    }

    private val properties: Map<String, String> by option(
        "-D",
        help = "A key/value pair to be passed to the application.",
        metavar = "KEY=VALUE"
    ).associate()

    private val charset by option(
        "-c",
        "--charset",
        help = "The input/output charset to be used.",
        metavar = "CHARSET",
        completionCandidates = CompletionCandidates.Fixed(Charset.availableCharsets().keys)
    ).convert {
        Charset.forName(it)
    }.default(Charsets.UTF_8, defaultForHelp = "UTF-8")

    private val model by option("-m", "--model", help = "The path to the JSON model.")
        .file()
        .required()

    private val template by option("-t", "--template", help = "The path to the freemarker template.")
        .file()
        .required()

    private val output: File? by option("-O", "--output", help = "The path to the file to write the result to.")
        .file()

    override fun run() {
        application.run(
            ApplicationArguments(
                charset,
                model,
                template,
                output,
                properties
            )
        )
    }
}
