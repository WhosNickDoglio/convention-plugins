// Copyright (C) 2025 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.convention.internal.configuration

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.LibraryExtension
import com.android.build.api.variant.AndroidComponentsExtension
import java.io.File
import org.gradle.api.GradleException
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog

internal fun Project.configureAndroid() {
    val android =
        extensions.findByType(LibraryExtension::class.java)
            ?: extensions.findByType(ApplicationExtension::class.java)

    val namespace = this.path.replace("/", "").replace(":", "").replace("-", ".")

    if (android == null) return
    val libs = versionCatalog()

    when (android) {
        is ApplicationExtension -> {
            android.configure(
                namespace = namespace,
                libs = libs,
                baselineFile = file("lint-baseline.xml"),
            )
        }
        is LibraryExtension -> {
            android.configureAndroid(
                androidNameSpace = namespace,
                libs = libs,
                baselineFile = file("lint-baseline.xml"),
            )
            with(extensions.getByType(AndroidComponentsExtension::class.java)) {
                beforeVariants(selector().withBuildType("debug")) { it.enable = false }
            }
        }

        else -> {
            GradleException("Unknown Android extension")
        }
    }
}

private fun ApplicationExtension.configure(
    namespace: String,
    libs: VersionCatalog,
    baselineFile: File,
) {
    defaultConfig {
        targetSdk = libs.findVersion("target-sdk").get().requiredVersion.toInt()
        minSdk = libs.findVersion("target-sdk").get().requiredVersion.toInt()
        // TODO expose these
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes { debug { matchingFallbacks += "release" } }

    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.toVersion(libs.getVersionOrError("jdkTarget"))
        targetCompatibility = JavaVersion.toVersion(libs.getVersionOrError("jdkTarget"))
    }

    configureAndroid(namespace, libs, baselineFile)
}

private fun CommonExtension<*, *, *, *, *, *>.configureAndroid(
    androidNameSpace: String,
    libs: VersionCatalog,
    baselineFile: File,
) {
    namespace = androidNameSpace

    compileSdk = libs.findVersion("compile-sdk").get().requiredVersion.toInt()
    defaultConfig { minSdk = libs.findVersion("min-sdk").get().requiredVersion.toInt() }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    lint.configure(baselineLineFile = baselineFile)
}
