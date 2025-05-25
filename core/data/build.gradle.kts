plugins {
    alias(libs.plugins.kakapo.kotlinMultiplatform)
}

kotlin {
    sourceSets.commonMain.dependencies {
        implementation(projects.core.common)
        implementation(projects.core.database)
        implementation(projects.core.preference)
    }
}