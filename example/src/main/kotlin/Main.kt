package com.example

import kotlin.time.measureTime


fun main() {
    println(measureTime {
        println(ExampleController().f())
    })
}