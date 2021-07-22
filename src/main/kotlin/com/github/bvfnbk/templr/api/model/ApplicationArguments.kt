package com.github.bvfnbk.templr.api.model

import java.io.File
import java.nio.charset.Charset

/**
 * The application arguments encapsulate the arguments received from the commandline.
 *
 * @author bvfnbk
 */
data class ApplicationArguments(
    val charset: Charset,
    val model: File,
    val template: File,
    val output: File?,
    val properties: Map<String, String>
) {
    val writeToStdOut = output == null
    val writeToFile = output != null
}
