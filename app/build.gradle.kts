plugins {
  id("com.android.application")
  id("org.jetbrains.kotlin.android")
}

android {
  namespace = "app.resketchware"
  compileSdk = 34

  defaultConfig {
    applicationId = "app.resketchware"
    minSdk = 23
    targetSdk = 28
    versionCode = 1
    versionName = "1.0"
  }

  buildTypes {
    release {
      isMinifyEnabled = true
      isShrinkResources = true
    }
  }

  buildFeatures {
    buildConfig = true
  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
  }

  kotlinOptions {
    jvmTarget = "17"
  }

  packaging {
    resources {
      excludes += "/META-INF/{AL2.0,LGPL2.1}"
    }
  }

  dependenciesInfo {
    includeInApk = false
  }
}

dependencies {
  implementation(project(":apksigner"))

  implementation(libs.bundles.core)
  implementation("androidx.core:core-splashscreen:1.0.1")
  implementation(libs.gson)
  implementation(libs.eclipse.jdt)
  implementation(libs.google.guava)
  implementation(libs.android.r8)
  implementation(libs.android.sdklib) {
    exclude(group = "com.intellij", module = "annotations")
    exclude(group = "jakarta.activation", module = "jakarta.activation-api")
  }
}
