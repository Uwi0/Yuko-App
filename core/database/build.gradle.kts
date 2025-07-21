plugins {
    alias(libs.plugins.kakapo.kotlinMultiplatform)
    alias(libs.plugins.appCash.sqlDelight)
}

kotlin {
    sourceSets.commonMain {
        dependencies {
            implementation(libs.sqldelight.coroutines)
            implementation(projects.core.common)
        }
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

    sourceSets.commonTest {
        dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.turbine)
            implementation(libs.kotlin.coroutines.test)
            implementation(libs.kotest.assertions.core)
        }
    }

    sourceSets.iosTest {
        dependencies {
            implementation(libs.sqldelight.navtive.driver)
        }
    }

    sourceSets.androidUnitTest {
        dependencies {
            implementation(libs.sqldelight.native.jvm)
        }
    }
}

sqldelight {
    databases {
        create("Database") {
            packageName.set("com.kakapo")
            srcDirs("src/commonMain/sqldelight")
            schemaOutputDirectory.set(file("src/commonMain/sqldelight/databases"))
        }
    }
}
