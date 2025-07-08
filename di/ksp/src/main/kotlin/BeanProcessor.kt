package net.mioyi.kvertx.di.ksp

import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.getAnnotationsByType
import com.google.devtools.ksp.getConstructors
import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.validate
import net.mioyi.kortex.di.Bean
import net.mioyi.kortex.util.ksp.getSymbolsWithAnnotation
import net.mioyi.kortex.util.ksp.writeNewFile
import java.io.Writer

internal class BeanProcessor(
    private val codeGenerator: CodeGenerator
) : SymbolProcessor {
    override fun process(resolver: Resolver): List<KSAnnotated> {
        val symbols = resolver.getSymbolsWithAnnotation<Bean>()

        val (valid, invalid) = symbols.partition { it.validate() }

        process(valid.filterIsInstance<KSClassDeclaration>())

        return invalid
    }

    private fun process(valid: List<KSClassDeclaration>) {
        if (valid.isEmpty()) {
            return
        }

        codeGenerator.writeNewFile(
            "net.mioyi.kortex.di.generated",
            "Dependencies"
        ) {
            write("import net.mioyi.kortex.di.Dependency\n")
            write("\n")
            write("fun dependencies() = buildList {\n")

            valid.forEach { writeDependency(it) }

            write("}\n")
        }
    }

    private fun Writer.writeDependency(symbol: KSClassDeclaration) {
        if (!symbol.getConstructors().any { it.parameters.isEmpty() }) {
            throw RequireNoArgConstructorException()
        }

        @OptIn(KspExperimental::class)
        val qualifier = symbol.getAnnotationsByType(Bean::class).single().qualifier

        if (!qualifier.all { 32 <= it.code && it.code < 127 }) {
            throw InvalidQualifierException()
        }

        val className = symbol.qualifiedName!!.asString()

        write("    add(Dependency($className(), \"$qualifier\"))\n")
    }
}
