buildscript {
    repositories {
        google()
        mavenCentral()
        maven {
            url = uri("https://plugins.gradle.org/m2/")
        }
    }

    dependencies {
        classpath(Dependency.Google.GMS_GOOGLE_SERVICE)
        classpath(Dependency.GradlePlugin.GRADLE_ANDROID)
        classpath(Dependency.GradlePlugin.GRADLE_KOTLIN)
        classpath(Dependency.GradlePlugin.GRADLE_HILT)
        classpath(Dependency.GradlePlugin.GRADLE_KTLINT)
    }
}

plugins {
    id("org.jlleitschuh.gradle.ktlint") version "10.2.1"
    id("org.jetbrains.kotlin.android") version "1.6.21" apply false
}

apply(plugin = "org.jmailen.kotlinter")

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
