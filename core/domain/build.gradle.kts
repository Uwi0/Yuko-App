plugins {
    alias(libs.plugins.kakapo.kotlinMultiplatform)
}


kotlin {
    sourceSets.commonMain.dependencies {
        implementation(projects.core.common)
        implementation(projects.core.data)
        implementation(projects.core.model)
    }
    sourceSets.commonTest.dependencies {
        implementation(libs.kotlin.test)
        implementation(libs.turbine)
        implementation(libs.kotlin.coroutines.test)
        implementation(libs.kotest.assertions.core)
    }
}