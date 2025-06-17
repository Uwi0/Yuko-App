plugins {
    alias(libs.plugins.kakapo.kotlinMultiplatform)
}

kotlin {
    sourceSets.commonMain.dependencies {
        implementation(libs.kotlin.coroutines.core)
    }
}