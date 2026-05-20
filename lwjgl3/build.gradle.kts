plugins {
    alias(libs.plugins.kotlin.jvm)
    id("application")
}

application {
    mainClass.set("dev.zy0x.pixelidlerpg.lwjgl3.Lwjgl3Launcher")
}

dependencies {
    implementation(project(":core"))
    implementation(libs.libgdx.lwjgl3)
    implementation(libs.libgdx.lwjgl3.natives)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    jvmToolchain(17)
}

tasks.jar {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    archiveBaseName.set("pixel-idle-rpg-desktop")
    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
    manifest { attributes["Main-Class"] = application.mainClass.get() }
}
