plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.kotlinParcelize)
    alias(libs.plugins.kotlinKapt)
    alias(libs.plugins.daggerHilt)
    id("kotlin-android")
}

apply {
    from("../shared_dependencies.gradle")
}

android {
    namespace = "com.example.core"
    compileSdk = 34

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        debug {
            // Test minify at debug
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "BASE_API_URL", "\"https://newsapi.org/v2/\"")
        }
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "BASE_API_URL", "\"https://newsapi.org/v2/\"")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(libs.jetbrains.kotlin)

    // room
    api(libs.androidx.room.runtime)
    // Can't use KSP because hilt doesn't support
    kapt(libs.androidx.room.compiler)
    androidTestImplementation(libs.androidx.room.testing)

    // retrofit
    api(libs.squareup.retrofit2)
    api(libs.squareup.retrofit2.converter.gson)
    api(libs.squareup.okhttp3)

    // coroutine
    implementation(libs.jetbrains.kotlinx.coroutines.core)
    implementation(libs.jetbrains.kotlinx.coroutines.android)
    implementation(libs.androidx.room)

    // other
    api(libs.androidx.lifecycle.livedata)
    api(libs.androidx.activity)
    api(libs.androidx.fragment)

    // encryption SQLCipher
    implementation(libs.android.database.sqlcipher)
    implementation(libs.androidx.sqlite.ktx)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.annotation)
}