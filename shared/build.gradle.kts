import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework

plugins {
    alias(libs.plugins.kakapo.kotlinMultiplatform)
    alias(libs.plugins.devtools.ksp)
    alias(libs.plugins.touchlab.skie)
    alias(libs.plugins.kmp.nativecoroutines)
}

kotlin {
    val xcf = XCFramework()

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "Shared"
            export(libs.androidx.lifecycle.viewmodel)
            freeCompilerArgs += "-Xbinary=bundleId=com.kakapo.oakane"
            xcf.add(this)
        }
    }
    
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.database)

            api(project.dependencies.platform(libs.koin.bom))
            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)

            implementation(libs.sqldelight.coroutines)
        }
        sourceSets.androidMain {
            dependencies {
                implementation(libs.sqldelight.android.driver)
            }
        }
        sourceSets.iosMain {
            dependencies {
                implementation(libs.sqldelight.navtive.driver)
            }
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}


