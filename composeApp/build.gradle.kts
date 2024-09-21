import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlin.serialization)
    id("com.google.firebase.crashlytics")
    alias(libs.plugins.google.gms.google.services)
    alias(libs.plugins.googleAndroidLibrariesMapsplatformSecretsGradlePlugin)
//    alias(libs.plugins.ksp)
//    alias(libs.plugins.room)
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }

    jvm("desktop") {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            freeCompilerArgs.add("-Xcontext-receivers")
        }
    }

    sourceSets {
        val desktopMain by getting

        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)

//            ktor
            implementation(libs.ktor.client.okhttp)

//            Koin
            implementation(libs.koin.android)
            implementation(libs.koin.androidx.compose)

//            System UI Controller
            implementation(libs.accompanist.systemuicontroller)

            implementation(project.dependencies.platform(libs.firebase.bom))
            implementation(libs.bundles.firebase)
            implementation(libs.play.service.auth)

//            implementation(libs.room.runtime.android)

            implementation(libs.multiplatform.markdown.renderer.android)
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.materialIconsExtended)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)
//            Ktor
            implementation(libs.bundles.ktor)
//          Koin
            api(libs.koin.core)
            implementation(libs.koin.compose)
//            Navigation
            implementation(libs.navigation.compose)
            implementation(libs.material.navigation)


            implementation(libs.adaptive)
            implementation(libs.adaptive.layout)
            implementation(libs.adaptive.navigation)
            implementation(libs.material3.adaptive.navigation.suite)
            implementation(libs.material3.window.size)

            api(libs.datastore)
            api(libs.datastore.preferences)
//
//            implementation(libs.room.runtime)
//            implementation(libs.room.ktx)
//            implementation(libs.sqlite.bundled)

            implementation(libs.multiplatform.markdown.renderer)
            implementation(libs.multiplatform.markdown.renderer.m3)
            implementation(libs.multiplatform.markdown.renderer.code)
        }
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutines.swing)
//            Ktor
            implementation(libs.ktor.client.okhttp)

//            implementation(libs.koin.jvm)
            implementation(libs.multiplatform.markdown.renderer.jvm)


        }
    }
}
//configurations.configureEach {
//    exclude("androidx.window.core", "window-core")
//}

android {
    namespace = "com.atech.research"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        applicationId = "com.atech.research"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    dependencies {
        debugImplementation(compose.uiTooling)
    }
}

compose.desktop {
    application {
        mainClass = "com.atech.research.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "ResearchHub"
            packageVersion = "1.0.0"
            jvmArgs(
                "--add-opens=java.base/sun.misc=ALL-UNNAMED",
                "--add-opens=java.base/jdk.internal.ref=ALL-UNNAMED",
                "--add-opens=java.base/java.lang=ALL-UNNAMED",
                "--add-opens=java.base/java.nio=ALL-UNNAMED",
                "--add-opens=java.base/sun.nio.ch=ALL-UNNAMED"
            )
            javaHome = "C:\\Users\\ROOT\\.jdks\\jbr-17.0.11"
            nativeDistributions {
                windows {
                    menuGroup = "Research Hub"
                    shortcut = true
                    iconFile.set(file("icon/icon.ico"))
                }

                // Enable bundling the JRE with the application for portability
                includeAllModules = true
                modules("java.desktop")
            }
        }
    }
}
//room {
//    schemaDirectory("$projectDir/schemas")
//}
//
//dependencies {
//    ksp(libs.room.compiler)
//}
