package com.example

import net.mioyi.kortex.di.inject
import net.mioyi.kortex.web.Controller

@Controller
class ExampleController {
    private val service: ExampleService by inject()

    fun f(): List<String> {
        return service.f() + "Controller"
    }
}