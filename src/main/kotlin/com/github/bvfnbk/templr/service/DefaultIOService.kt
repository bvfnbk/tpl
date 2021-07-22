package com.github.bvfnbk.templr.service

import com.github.bvfnbk.templr.api.service.IOService
import java.io.*
import java.nio.charset.Charset

/**
 * @author bvfnbk
 */
class DefaultIOService : IOService {
    override fun createFileInputStream(source: File): FileInputStream = FileInputStream(source)
    override fun createFileOutputStream(target: File): FileOutputStream = FileOutputStream(target)

    override fun createInputStreamReader(source: File, charset: Charset): InputStreamReader =
        createInputStreamReader(createFileInputStream(source), charset)

    override fun createInputStreamReader(inputStream: FileInputStream, charset: Charset): InputStreamReader =
        InputStreamReader(inputStream, charset)

    override fun createOutputStreamWriter(outputStream: FileOutputStream, charset: Charset): OutputStreamWriter =
        OutputStreamWriter(outputStream, charset)

    override fun createOutputStreamWriter(target: File, charset: Charset): OutputStreamWriter =
        createOutputStreamWriter(createFileOutputStream(target), charset)

    override fun createOutputStreamWriter(outputStream: PrintStream, charset: Charset): OutputStreamWriter =
        OutputStreamWriter(outputStream, charset)
}
