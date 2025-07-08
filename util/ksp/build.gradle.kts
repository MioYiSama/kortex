plugins {
    alias(libs.plugins.kotlin.jvm)
}

group = "net.mioyi.kortex.util.ksp"

dependencies {
    implementation(libs.ksp)
}