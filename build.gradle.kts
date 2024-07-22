plugins {
  alias(libs.plugins.android.application) apply false
  alias(libs.plugins.kotlin.gradlePlugin) apply false
}

tasks.register<Delete>("clean") {
  delete(rootProject.layout.buildDirectory)
}
