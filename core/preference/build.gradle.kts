plugins {
    alias(libs.plugins.kakapo.kotlinMultiplatform)
}

kotlin {
    sourceSets.commonMain.dependencies {
        implementation(libs.datastore)
        implementation(libs.datastore.preferences)
    }
}