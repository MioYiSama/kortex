plugins {
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.ksp) apply false
}

group = "net.mioyi.kortex"

allprojects {
    version = "0.0.1"

    repositories {
        mavenCentral()
        google()
    }
}