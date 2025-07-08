plugins {
    alias(libs.plugins.kotlin.jvm)
}

group = "net.mioyi.kortex.di.ksp"

dependencies {
    implementation(project(":di"))
    implementation(project(":util:ksp"))

    implementation(libs.ksp)
}
