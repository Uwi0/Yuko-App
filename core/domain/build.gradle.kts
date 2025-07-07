plugins {
    alias(libs.plugins.kakapo.kotlinMultiplatform)
}


kotlin {
    sourceSets.commonMain.dependencies {
        implementation(projects.core.data)
    }
}