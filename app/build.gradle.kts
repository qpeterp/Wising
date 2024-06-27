plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")

    // Add the dependency for the Google services Gradle plugin
    id("com.google.gms.google-services") version "4.4.1" apply false
}

android {
    namespace = "com.qpeterp.wising"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.qpeterp.wising"
        minSdk = 24
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

    buildFeatures {
        viewBinding = true
    }

}

dependencies {
    // Rive
    // During initialization, you may need to add a dependency
    // for Jetpack Startup
    implementation("app.rive:rive-android:8.7.0")
    implementation("androidx.startup:startup-runtime:1.1.1")

    // colorPicker
    implementation ("com.github.skydoves:colorpickerview:2.3.0")

    // shorts
    implementation("androidx.viewpager2:viewpager2:1.1.0")


    // viewModel dependencies
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.8.0")

    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.0")

    // navigation
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.7")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.7")

    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}