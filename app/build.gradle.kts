plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("com.google.devtools.ksp") version "1.9.0-1.0.12"
}

android {
    namespace = "com.example.expensetracker"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.expensetracker"
        minSdk = 24
        targetSdk = 35
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
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test:runner:1.5.2")
    androidTestImplementation("androidx.test:rules:1.5.2")
    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
    testImplementation("androidx.room:room-testing:2.6.1")
    // Core dependencies
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)

    // Lifecycle and ViewModel
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)

    // Navigation
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    // Jetpack Compose
    implementation("androidx.compose.ui:ui:1.4.1")
    implementation("androidx.compose.material:material:1.4.1")
    implementation("androidx.compose.ui:ui-tooling-preview:1.4.1")
    implementation("androidx.activity:activity-compose:1.7.0")

    // Charts
    implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")

    // Room (Database)
    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
    implementation(libs.androidx.junit.ktx)
    ksp("androidx.room:room-compiler:2.6.1")

    // Testing Dependencies
    testImplementation("junit:junit:4.13.2") // JUnit for Unit Testing
    testImplementation("androidx.room:room-testing:2.6.1") // Room testing utilities
    testImplementation("org.mockito:mockito-core:4.6.1") // Mockito for mocking
    testImplementation("org.mockito.kotlin:mockito-kotlin:4.1.0") // Kotlin extensions for Mockito

    androidTestImplementation("androidx.test.ext:junit:1.1.5") // AndroidX JUnit
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1") // Espresso for UI Testing
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.4.1") // Compose UI testing

    // Debugging
    debugImplementation("androidx.compose.ui:ui-tooling:1.4.1")
}
