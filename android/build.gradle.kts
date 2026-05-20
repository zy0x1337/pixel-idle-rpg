plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "dev.zy0x.pixelidlerpg"
    compileSdk = libs.versions.androidCompileSdk.get().toInt()

    defaultConfig {
        applicationId = "dev.zy0x.pixelidlerpg"
        minSdk        = libs.versions.androidMinSdk.get().toInt()
        targetSdk     = libs.versions.androidTargetSdk.get().toInt()
        versionCode   = 1
        versionName   = "0.1.0"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    sourceSets {
        named("main") {
            assets.srcDirs("../assets")
            jniLibs.srcDirs("libs")
        }
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(project(":core"))

    implementation(libs.libgdx.android)
    natives(libs.libgdx.android.natives.armeabi.v7a)
    natives(libs.libgdx.android.natives.arm64.v8a)
    natives(libs.libgdx.android.natives.x86)
    natives(libs.libgdx.android.natives.x86.64)

    implementation(libs.kotlinx.coroutines.android)
}

fun DependencyHandlerScope.natives(dependency: Any) {
    implementation(dependency)
}
