import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

android {
    namespace = "jp.co.yumemi.droidtraining"
    compileSdk = 33

    defaultConfig {
        applicationId = "jp.co.yumemi.droidtraining"
        minSdk = 27
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = JvmTarget.JVM_1_8.target
    }
    buildFeatures {
        dataBinding = true
    }
}

dependencies {
    implementation(project(path = ":api"))
    implementation(project(path = ":ui"))
    implementation(project(path = ":repository"))
    implementation(project(path = ":use_case"))
    implementation(project(path = ":model"))

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}
