plugins {
    alias(libs.plugins.kakapo.kotlinMultiplatform)
    id("org.jetbrains.kotlin.native.cocoapods")
}

kotlin {
    cocoapods {
        summary = "Shared KMP module"
        homepage = "Link to your project homepage"
        version = "1.0.0"
        ios.deploymentTarget = "15.0"

        framework {
            baseName = "Shared"
            export(projects.shared)
        }
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.XcodeTask>() {
    destinationDir.set(file("/Volumes/All About Me/cache/kmpDerivedData/${project.name}"))
}