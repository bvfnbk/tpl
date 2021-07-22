package com.github.bvfnbk.templr

import com.github.bvfnbk.templr.api.TemplrApplication
import com.github.bvfnbk.templr.api.model.ApplicationArguments
import com.github.bvfnbk.templr.api.service.IOService
import com.github.bvfnbk.templr.api.service.ModelService
import com.github.bvfnbk.templr.api.service.TemplateService
import java.io.OutputStreamWriter
import java.io.Writer

/**
 * @author bvfnbk
 */
class DefaultTemplrApplication(
    private val modelService: ModelService,
    private val ioService: IOService,
    private val templateService: TemplateService
) : TemplrApplication {

    override fun run(arguments: ApplicationArguments) {
        // Configure template engine to use given charset:
        templateService.configure(arguments.charset)

        // Load model
        val context = modelService.load(arguments.model, arguments.charset)

        // Create writer:
        val writer = when {
            arguments.writeToFile -> ioService.createOutputStreamWriter(
                arguments.output!!,
                arguments.charset
            )
            else -> ioService.createOutputStreamWriter(System.out, arguments.charset)
        }

        writer.use {
            // Apply template:
            templateService.apply(arguments.template, context, it)
        }
    }
}
