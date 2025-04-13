plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    kotlin("plugin.serialization") version "1.9.0"
}

android {
    namespace = "com.example.avantitigestiondeincidencias"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.avantitigestiondeincidencias"
        minSdk = 32
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation ("androidx.constraintlayout:constraintlayout-compose:1.1.0")
    implementation ("androidx.compose.foundation:foundation:1.7.6")
    implementation ("com.google.accompanist:accompanist-pager-indicators:0.30.1")

    // APACHE POI para el excel
    implementation ("org.apache.poi:poi:5.2.2")
    implementation ("org.apache.poi:poi-ooxml:5.2.2")

    //Navigation Host
    implementation ("androidx.navigation:navigation-compose:2.8.5")

    // Serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.8.0")

    // Permisos
    implementation ("com.google.accompanist:accompanist-permissions:0.29.0-alpha")

    //Supabase
    implementation(platform("io.github.jan-tennert.supabase:bom:3.1.1"))
    implementation("io.github.jan-tennert.supabase:postgrest-kt")
    implementation("io.github.jan-tennert.supabase:auth-kt")
    implementation("io.github.jan-tennert.supabase:realtime-kt")

    // Okhttp de Ktor para realizar las peticiones a la base de datos
    implementation("io.ktor:ktor-client-okhttp:3.1.1")

    // Corrutinas de kotlinx
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.10.1") // Para Android
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.1")

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}