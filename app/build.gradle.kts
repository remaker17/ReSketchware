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

        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        named("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
        isCoreLibraryDesugaringEnabled = true
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.androidx.compose.compiler.get()
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
        freeCompilerArgs = freeCompilerArgs + listOf(
            "-opt-in=kotlin.RequiresOptIn",
            "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
            "-opt-in=kotlinx.coroutines.FlowPreview",
        )
    }
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))

    implementation(libs.bundles.androidx.compose.bundle)
    implementation(libs.bundles.core)
    implementation(libs.crashhandler)

    //implementation(libs.gson)
    //implementation(libs.eclipse.jdt)
    //implementation(libs.google.guava)
    //implementation(libs.android.r8)
    //implementation(libs.android.sdklib) {
        //exclude(group = "com.intellij", module = "annotations")
    //}

    //implementation(projects.apksigner)
    coreLibraryDesugaring(libs.android.desugarJdkLibs)
}