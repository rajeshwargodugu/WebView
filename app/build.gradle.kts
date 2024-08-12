plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.gola.webview"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.gola.webview"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.test.ext:junit-ktx:1.2.1")

    // AndroidX Test - JUnit4
    testImplementation("junit:junit:4.13.2") // Replace with the latest version if available
    testImplementation("androidx.arch.core:core-testing:2.1.0")

    // AndroidX Test - Core
    androidTestImplementation("androidx.test:core:1.4.0")
    androidTestImplementation("androidx.test.ext:junit:1.1.5") // Replace with the latest version if available
    androidTestImplementation("androidx.test:runner:1.5.0") // Replace with the latest version if available

    // AndroidX Test - Rules
    androidTestImplementation("androidx.test:rules:1.5.0") // Replace with the latest version if available
}