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
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation(fileTree(mapOf(
        "dir" to "libs",
        "include" to listOf("*.jar", "*.aar")
    )))

    implementation(libs.bundles.core)
    implementation(libs.gson)
    implementation(libs.eclipse.jdt)
    implementation(libs.google.guava)
    implementation(libs.android.r8)
    implementation(libs.android.sdklib) {
        exclude(group = "com.intellij", module = "annotations")
    }
    debugImplementation(libs.leakcanary)
}