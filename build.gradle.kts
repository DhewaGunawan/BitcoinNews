// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.androidDynamicFeature) apply false
    alias(libs.plugins.kotlinKapt) apply false
    alias(libs.plugins.kotlinParcelize) apply false
    alias(libs.plugins.daggerHilt) apply false
}

buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(libs.dagger.hilt.android.gradle.plugin)
    }
}

