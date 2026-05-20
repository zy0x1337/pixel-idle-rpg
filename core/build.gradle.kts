plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
}

dependencies {
    // libGDX core
    api(libs.libgdx.core)

    // KTX extensions
    api(libs.ktx.app)
    api(libs.ktx.assets)
    api(libs.ktx.assets.async)
    api(libs.ktx.graphics)
    api(libs.ktx.math)
    api(libs.ktx.log)
    api(libs.ktx.scene2d)
    api(libs.ktx.style)
    api(libs.ktx.collections)
    api(libs.ktx.async)

    // Fleks ECS
    api(libs.fleks)

    // Serialization + Coroutines
    api(libs.kotlinx.serialization.json)
    api(libs.kotlinx.coroutines.core)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    jvmToolchain(17)
}
