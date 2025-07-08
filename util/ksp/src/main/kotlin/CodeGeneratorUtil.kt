package net.mioyi.kortex.util.ksp

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import java.io.Writer

inline fun CodeGenerator.writeNewFile(packageName: String, fileName: String, f: Writer.() -> Unit) {
    createNewFile(
        Dependencies(true),
        packageName,
        fileName
    ).use { stream ->
        stream.bufferedWriter().use { writer ->
            writer.write("package $packageName\n")
            writer.write("\n")

            writer.f()
        }
    }
}