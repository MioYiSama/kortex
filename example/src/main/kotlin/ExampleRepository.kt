package com.example

import net.mioyi.kortex.di.Bean

interface ExampleRepository {
    fun f(): List<String>
}

@Bean
class ExampleRepositoryImpl : ExampleRepository {
    override fun f(): List<String> {
        return listOf("Repository")
    }
}