object Dependency {
    object GradlePlugin {
        const val ANDROID_APPLICATION_PLUGIN = "com.android.application"
        const val KOTLIN_ANDROID_PLUGIN = "org.jetbrains.kotlin.android"
        const val ANDROID_LIBRARY_PLUGIN = "com.android.library"
        const val KTLINT_PLUGIN = "org.jlleitschuh.gradle.ktlint"
        const val GRADLE_HILT = "com.google.dagger:hilt-android-gradle-plugin:${Versions.HILT}"
    }

    object Kotlin {
        const val KOTLIN_STDLIB = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.KOTLIN_VERSION}"
        const val COROUTINES_CORE =
            "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.KOTLINX_COROUTINES}"
        const val COROUTINES_ANDROID =
            "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.KOTLINX_COROUTINES}"
    }

    object AndroidX {
        const val CONSTRAINT_LAYOUT =
            "androidx.constraintlayout:constraintlayout:${Versions.CONSTRAINT_LAYOUT}"

        const val CORE_KTX = "androidx.core:core-ktx:${Versions.CORE_KTX}"
        const val APP_COMPAT = "androidx.appcompat:appcompat:${Versions.APP_COMPAT}"

        const val ACTIVITY_KTX = "androidx.activity:activity-ktx:${Versions.ACTIVITY_KTX}"
        const val FRAGMENT_KTX = "androidx.fragment:fragment-ktx:${Versions.FRAGMENT_KTX}"

        const val RECYCLER_VIEW = "androidx.recyclerview:recyclerview:${Versions.RECYCLERVIEW}"

        const val LIFECYCLE_VIEWMODEL_KTX =
            "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.LIFECYCLE_KTX}"
        const val LIFECYCLE_LIVEDATA_KTX =
            "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.LIFECYCLE_KTX}"

        const val ROOM_RUNTIME = "androidx.room:room-runtime:${Versions.ROOM}"
        const val ROOM_KTX = "androidx.room:room-ktx:${Versions.ROOM}"
        const val ROOM_COMPILER = "androidx.room:room-compiler:${Versions.ROOM}"

        const val LEGACY_SUPPORT = "androidx.legacy:legacy-support-v4:${Versions.LEGACY}"

        const val SPLASH_SCREEN = "androidx.core:core-splashscreen:${Versions.SPLASH}"

    }

    object Google {
        const val FIREBASE_ANALYTICS = "com.google.firebase:firebase-analytics-ktx"
        const val FIREBASE_BOM = "com.google.firebase:firebase-bom:${Versions.FIREBASE_BOM}"
        const val GMS_PLAY_SERVICE_AUTH =
            "com.google.android.gms:play-services-auth:${Versions.GMS_PLAY_SERVICE_AUTH}"
        const val GMS_GOOGLE_SERVICE =
            "com.google.gms:google-services:${Versions.GMS_GOOGLE_SERVICE}"
        const val HILT_ANDROID = "com.google.dagger:hilt-android:${Versions.HILT}"
        const val HILT_ANDROID_COMPILER = "com.google.dagger:hilt-android-compiler:${Versions.HILT}"
        const val MATERIAL = "com.google.android.material:material:${Versions.MATERIAL}"
        const val GMS_PLAY_SERVICE_BASE =
            "com.google.android.gms:play-services-base:${Versions.GMS_PLAY_SERVICE_BASE}"
        const val FIREBASE = "com.google.firebase:firebase-auth-ktx:${Versions.FIREBASE}"
        const val LIVEDATA = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.LIVEDATA}"
        const val GOOGLE_PLAY_UPDATE = "com.google.android.play:app-update:${Versions.GOOGLE_PLAY_UPDATE}"
        const val GOOGLE_PLAY_KOTLIN_UPDATE = "com.google.android.play:app-update-ktx:${Versions.GOOGLE_PLAY_UPDATE}"
    }

    object Libraries {
        const val RETROFIT = "com.squareup.retrofit2:retrofit:${Versions.RETROFIT}"
        const val RETROFIT_CONVERTER_GSON =
            "com.squareup.retrofit2:converter-gson:${Versions.RETROFIT}"
        const val OKHTTP = "com.squareup.okhttp3:okhttp:${Versions.OKHTTP}"
        const val OKHTTP_LOGGING_INTERCEPTOR =
            "com.squareup.okhttp3:logging-interceptor:${Versions.OKHTTP}"
        const val MOSHI = "com.squareup.moshi:moshi-kotlin:${Versions.MOSHI}"
        const val MOSHI_COMPILER = "com.squareup.moshi:moshi-kotlin-codegen:${Versions.MOSHI}"
        const val SHIMMER = "com.facebook.shimmer:shimmer:${Versions.SHIMMER}"
        const val GAUTH = "com.github.GSM-MSG:GAuth-Signin-Android:v${Versions.GAUTH}"
    }

    object UnitTest {
        const val JUNIT = "junit:junit:${Versions.JUNIT}"
        const val MOCKITO = "org.mockito:mockito-core:${Versions.MOCKITO}"
    }

    object AndroidTest {
        const val ANDROID_JUNIT = "androidx.test.ext:junit:${Versions.ANDROID_JUNIT}"
        const val ESPRESSO_CORE = "androidx.test.espresso:espresso-core:${Versions.ESPRESSO_CORE}"
    }

    object BottomNav {
        const val NAV_FRAGMENT = "androidx.navigation:navigation-fragment-ktx:${Versions.NAV}"
        const val NAV_UI = "androidx.navigation:navigation-ui-ktx:${Versions.NAV}"
    }

    object Rx {
        const val RX_BINNDING = "com.jakewharton.rxbinding4:rxbinding:${Versions.RX_BINDING}"
        const val RX_JAVA = "io.reactivex.rxjava3:rxjava:${Versions.RX_JAVA}"
        const val RX_ANDROID = "io.reactivex.rxjava3:rxandroid:${Versions.RX_ANDROID}"
    }

    object Coroutine {
        const val COROUTINE =
            "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.COROUTINE}"
    }

    object ImageLoad {
        const val COIL = "io.coil-kt:coil:${Versions.COIL_VERSION}"
    }

    object Lottie {
        const val LOTTIE = "com.airbnb.android:lottie:${Versions.LOTTIE_VERSION}"
    }

    object Compose {
        const val ACTIVITY_COMPOSE =
            "androidx.activity:activity-compose:${Versions.ACTIVITY_COMPOSE}"
        const val COMPOSE = "androidx.compose.ui:ui:${Versions.COMPOSE}"
        const val COMPOSE_PREVIEW = "androidx.compose.ui:ui-tooling-preview:${Versions.COMPOSE}"
        const val COMPOSE_JUNIT = "androidx.compose.ui:ui-test-junit4:${Versions.COMPOSE}"
        const val COMPOSE_TOOLING = "androidx.compose.ui:ui-tooling:${Versions.COMPOSE}"
        const val COMPOSE_TEST = "androidx.compose.ui:ui-test-manifest:${Versions.COMPOSE}"
        const val COMPOSE_MATERIAL =
            "androidx.compose.material:material:${Versions.COMPOSE_METARIAL}"
    }
}
