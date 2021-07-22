package com.github.bvfnbk.templr.api.service

import java.io.*
import java.nio.charset.Charset

/**
 * Utility factory methods to create input/output streams and readers and writers.
 *
 * @author bvfnbk
 */
interface IOService {
    // Streams
    fun createFileInputStream(source: File): FileInputStream
    fun createFileOutputStream(target: File): FileOutputStream

    // Reader
    fun createInputStreamReader(source: File, charset: Charset = Charsets.UTF_8): InputStreamReader
    fun createInputStreamReader(inputStream: FileInputStream, charset: Charset = Charsets.UTF_8): InputStreamReader

    // Writer
    fun createOutputStreamWriter(outputStream: PrintStream, charset: Charset = Charsets.UTF_8): OutputStreamWriter
    fun createOutputStreamWriter(outputStream: FileOutputStream, charset: Charset = Charsets.UTF_8): OutputStreamWriter
    fun createOutputStreamWriter(target: File, charset: Charset = Charsets.UTF_8): OutputStreamWriter
}
