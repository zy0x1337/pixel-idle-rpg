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

    // Gradle 8+ does not support 'classifier' in Version Catalog entries.
    // Natives must be declared as full GAV strings with classifier appended.
    val gdx = libs.versions.libgdx.get()
    natives("com.badlogicgames.gdx:gdx-platform:$gdx:natives-armeabi-v7a")
    natives("com.badlogicgames.gdx:gdx-platform:$gdx:natives-arm64-v8a")
    natives("com.badlogicgames.gdx:gdx-platform:$gdx:natives-x86")
    natives("com.badlogicgames.gdx:gdx-platform:$gdx:natives-x86_64")

    implementation(libs.kotlinx.coroutines.android)
}

// Unpacks native .so files from the platform JARs into jniLibs so Android can find them.
fun DependencyHandlerScope.natives(dependency: Any) {
    implementation(dependency)
}
