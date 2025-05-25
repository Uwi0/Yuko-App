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
            export(projects.core.model)
            export(libs.androidx.lifecycle.viewmodel)
            freeCompilerArgs += "-Xbinary=bundleId=com.kakapo.oakane"
            xcf.add(this)
        }
    }
    
    sourceSets {
        commonMain.dependencies {
            api(projects.core.model)
            implementation(projects.core.database)
            implementation(projects.core.data)
            implementation(projects.core.preference)

            api(project.dependencies.platform(libs.koin.bom))
            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)

            implementation(libs.datastore)
            implementation(libs.datastore.preferences)

        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}


