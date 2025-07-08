import com.google.devtools.ksp.KspExperimental

plugins {
    application
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.ksp)
}

group = "com.example"

dependencies {
    implementation(project(":di"))
    ksp(project(":di:ksp"))

    implementation(project(":web"))
}

application {
    mainClass = "com.example.MainKt"
}

ksp {
    @OptIn(KspExperimental::class)
    useKsp2 = true
}