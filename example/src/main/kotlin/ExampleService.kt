package com.example

import net.mioyi.kortex.di.Bean
import net.mioyi.kortex.di.inject

interface ExampleService {
    fun f(): List<String>
}

@Bean
class ExampleServiceImpl : ExampleService {
    private val repository: ExampleRepository by inject()

    override fun f(): List<String> {
        return repository.f() + "Service"
    }
}