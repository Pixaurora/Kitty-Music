plugins {
    `kotlin-dsl`
}

repositories {
    maven {
        name = "Quilt"
        url = uri("https://maven.quiltmc.org/repository/release")
    }
    // Currently needed for Intermediary and other temporary dependencies
    maven {
        name = "Fabric"
        url = uri("https://maven.fabricmc.net/")
    }
    maven {
        name = "Ornithe Releases"
        url = uri("https://maven.ornithemc.net/releases")
    }
    gradlePluginPortal()
}

dependencies {
    implementation(libs.quilt.loom)
    implementation(libs.ploceus)

    implementation(libs.gson)

    // Enable using version catalog in local plugins
    // https://github.com/gradle/gradle/issues/15383
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
}

kotlin {
    jvmToolchain(21)
}
