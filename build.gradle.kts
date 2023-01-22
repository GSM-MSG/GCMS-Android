buildscript {
    dependencies {
        classpath(Dependency.Google.GMS_GOOGLE_SERVICE)
        classpath(Dependency.GradlePlugin.GRADLE_HILT)
        classpath(Dependency.GradlePlugin.GRADLE_KTLINT)
    }
}

plugins {
    id(Dependency.GradlePlugin.KTLINT_PLUGIN) version Versions.KTLINT_PLUGIN
    id(Dependency.GradlePlugin.ANDROID_APPLICATION_PLUGIN) version Versions.GRADLE_ANDROID apply false
    id(Dependency.GradlePlugin.ANDROID_LIBRARY_PLUGIN) version Versions.GRADLE_ANDROID apply false
    id(Dependency.GradlePlugin.KOTLIN_ANDROID_PLUGIN) version Versions.GRADLE_KOTLIN apply false
}

apply(plugin = Dependency.GradlePlugin.KOTLINTER_PLUGIN)
