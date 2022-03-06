plugins {
    id("com.android.application")
    id("dagger.hilt.android.plugin")
    kotlin("android")
    kotlin("kapt")
    id("kotlin-android")
}

android {
    compileSdk = Versions.COMPILE_SDK_VERSION

    defaultConfig {
        applicationId = "com.msg.gcms"
        minSdk = Versions.MIN_SDK_VERSION
        targetSdk = Versions.TARGET_SDK_VERSION
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = Versions.JAVA_VERSION
        targetCompatibility = Versions.JAVA_VERSION
    }
    kotlinOptions {
        jvmTarget = Versions.JAVA_VERSION.toString()
    }
    buildFeatures {
        dataBinding = true
    }
}

dependencies {

    implementation(Dependency.AndroidX.CORE_KTX)
    implementation(Dependency.AndroidX.APP_COMPAT)
    implementation(Dependency.AndroidX.CORE_KTX)
    implementation(Dependency.AndroidX.FRAGMENT_KTX)
    implementation(Dependency.AndroidX.CONSTRAINT_LAYOUT)
    implementation(Dependency.AndroidX.LIFECYCLE_VIEWMODEL_KTX)

    implementation(Dependency.AndroidX.ROOM_KTX)
    implementation(Dependency.AndroidX.LEGACY_SUPPORT)

    kapt(Dependency.AndroidX.ROOM_COMPILER)

    implementation(Dependency.Kotlin.COROUTINES_CORE)
    implementation(Dependency.Kotlin.COROUTINES_ANDROID)

    implementation(Dependency.Google.GMS)
    implementation(Dependency.Google.MATERIAL)
    implementation(Dependency.Google.HILT_ANDROID)
    kapt(Dependency.Google.HILT_ANDROID_COMPILER)

    implementation(Dependency.Libraries.RETROFIT)
    implementation(Dependency.Libraries.RETROFIT_CONVERTER_GSON)
    implementation(Dependency.Libraries.OKHTTP)
    implementation(Dependency.Libraries.OKHTTP_LOGGING_INTERCEPTOR)
    implementation(Dependency.Libraries.MOSHI)
    kapt(Dependency.Libraries.MOSHI_COMPILER)

    testImplementation(Dependency.UnitTest.JUNIT)
    testImplementation(Dependency.UnitTest.MOCKITO)

    androidTestImplementation(Dependency.AndroidTest.ANDROID_JUNIT)
    androidTestImplementation(Dependency.AndroidTest.ESPRESSO_CORE)

    implementation(Dependency.BottomNav.NAV_FRAGMENT)
    implementation(Dependency.BottomNav.NAV_UI)
}