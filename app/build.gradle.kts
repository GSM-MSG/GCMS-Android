import java.io.FileInputStream
import java.util.Properties

plugins {
    id("com.android.application")
    id("dagger.hilt.android.plugin")
    id("kotlin-android")
    id("kotlin-kapt")
}

android {
    compileSdk = Versions.COMPILE_SDK_VERSION

    defaultConfig {
        applicationId = "com.msg.gcms"
        minSdk = Versions.MIN_SDK_VERSION
        targetSdk = Versions.TARGET_SDK_VERSION
        versionCode = 5
        versionName = "2.0.3"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField(
            "String",
            "CLIENT_ID",
            getApiKey("CLIENT_ID")
        )
        buildConfigField(
            "String",
            "REDIRECT_URI",
            getApiKey("REDIRECT_URI")
        )
        buildConfigField(
            "String",
            "BASE_URL",
            getApiKey("BASE_URL")
        )
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
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.2.0-alpha03"
    }
}

dependencies {

    implementation(Dependency.AndroidX.APP_COMPAT)
    implementation(Dependency.AndroidX.CORE_KTX)
    implementation(Dependency.AndroidX.FRAGMENT_KTX)
    implementation(Dependency.AndroidX.CONSTRAINT_LAYOUT)
    implementation(Dependency.AndroidX.LIFECYCLE_VIEWMODEL_KTX)
    implementation(Dependency.AndroidX.RECYCLER_VIEW)

    implementation(Dependency.AndroidX.ROOM_KTX)
    implementation(Dependency.AndroidX.LEGACY_SUPPORT)
    implementation(platform(Dependency.Google.FIREBASE_BOM))
    implementation(Dependency.Google.FIREBASE_ANALYTICS)
    implementation(Dependency.Google.FIREBASE)
    implementation(Dependency.Google.GMS_PLAY_SERVICE_AUTH)
    implementation(Dependency.Google.GMS_PLAY_SERVICE_BASE)
    kapt(Dependency.AndroidX.ROOM_COMPILER)

    implementation(Dependency.Kotlin.COROUTINES_CORE)
    implementation(Dependency.Kotlin.COROUTINES_ANDROID)

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

    implementation(Dependency.Rx.RX_BINNDING)
    implementation(Dependency.Rx.RX_JAVA)
    implementation(Dependency.Rx.RX_ANDROID)
    implementation(Dependency.Coroutine.COROUTINE)

    implementation(Dependency.ImageLoad.COIL)

    implementation(Dependency.Google.LIVEDATA)

    implementation(Dependency.Libraries.SHIMMER)

    implementation(Dependency.Lottie.LOTTIE)

    implementation(Dependency.Libraries.GAUTH)

    implementation(Dependency.Compose.ACTIVITY_COMPOSE)
    implementation(Dependency.Compose.COMPOSE)
    implementation(Dependency.Compose.COMPOSE_PREVIEW)
    implementation(Dependency.Compose.COMPOSE_MATERIAL)
    androidTestImplementation(Dependency.Compose.COMPOSE_JUNIT)
    debugImplementation(Dependency.Compose.COMPOSE_TOOLING)
    debugImplementation(Dependency.Compose.COMPOSE_TEST)

    implementation(Dependency.Google.GOOGLE_PLAY_UPDATE)
    implementation(Dependency.Google.GOOGLE_PLAY_KOTLIN_UPDATE)
}

fun getApiKey(propertyKey: String): String {
    val propFile = rootProject.file("./local.properties")
    val properties = Properties()
    properties.load(FileInputStream(propFile))
    return properties.getProperty(propertyKey)
}
