plugins {
    alias(libs.plugins.kakapo.kotlinMultiplatform)
}

kotlin {
    compilerOptions {
        freeCompilerArgs.add("-Xannotation-default-target=param-property")
    }
    sourceSets.commonMain.dependencies {
        implementation(projects.core.common)
    }
}