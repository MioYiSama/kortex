package net.mioyi.kvertx.di.ksp

import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.getAnnotationsByType
import com.google.devtools.ksp.getConstructors
import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.validate
import net.mioyi.kortex.di.Bean

internal class BeanProcessor(
    private val codeGenerator: CodeGenerator
) : SymbolProcessor {
    override fun process(resolver: Resolver): List<KSAnnotated> {
        val symbols = resolver.getSymbolsWithAnnotation(Bean::class.qualifiedName!!)

        val validSymbols = mutableListOf<KSClassDeclaration>()
        val invalidSymbols = mutableListOf<KSAnnotated>()

        for (symbol in symbols) {
            if (symbol.validate() && symbol is KSClassDeclaration) {
                validSymbols.add(symbol)
            } else {
                invalidSymbols.add(symbol)
            }
        }

        processValidSymbols(validSymbols)

        return invalidSymbols
    }

    private fun processValidSymbols(validSymbols: List<KSClassDeclaration>) {
        if (validSymbols.isEmpty()) {
            return
        }

        codeGenerator.createNewFile(
            Dependencies(false),
            "net.mioyi.kortex.di.generated",
            "Dependencies"
        ).use { stream ->
            stream.writer().use { writer ->
                writer.write("package net.mioyi.kortex.di.generated\n")
                writer.write("\n")
                writer.write("import net.mioyi.kortex.di.Dependency\n")
                writer.write("\n")
                writer.write("fun dependencies() = buildList {\n")

                for (symbol in validSymbols) {
                    if (!symbol.getConstructors().any { it.parameters.isEmpty() }) {
                        throw RequireNoArgConstructorException()
                    }

                    @OptIn(KspExperimental::class)
                    val qualifier = symbol.getAnnotationsByType(Bean::class).single().qualifier

                    if (!qualifier.all { 32 <= it.code && it.code < 127 }) {
                        throw InvalidQualifierException()
                    }

                    val className = symbol.qualifiedName!!.asString()
                    writer.write("    add(Dependency($className(), \"$qualifier\"))\n")
                }

                writer.write("}\n")
            }
        }
    }
}
