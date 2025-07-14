plugins {
    alias(libs.plugins.kakapo.kotlinMultiplatform)
}


kotlin {
    sourceSets.commonMain.dependencies {
        implementation(projects.core.common)
        implementation(projects.core.data)
        implementation(projects.core.model)
    }
}