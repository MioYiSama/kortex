package net.mioyi.kortex.util.ksp

import com.google.devtools.ksp.processing.Resolver

inline fun <reified T> Resolver.getSymbolsWithAnnotation() =
    getSymbolsWithAnnotation(T::class.qualifiedName!!)