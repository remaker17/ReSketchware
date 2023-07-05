@file:Suppress("UnstableApiUsage")

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.gradlePlugin) apply false
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}