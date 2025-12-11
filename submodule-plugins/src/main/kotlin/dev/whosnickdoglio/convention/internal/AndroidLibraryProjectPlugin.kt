// Copyright (C) 2025 Nicholas Doglio
// SPDX-License-Identifier: MIT
package dev.whosnickdoglio.convention.internal

import dev.whosnickdoglio.convention.AndroidLibraryConventionExtension
import dev.whosnickdoglio.convention.internal.configuration.applyLintingPlugins
import dev.whosnickdoglio.convention.internal.configuration.configureAndroid
import dev.whosnickdoglio.convention.internal.configuration.configureJvm
import dev.whosnickdoglio.convention.internal.configuration.configureLint
import dev.whosnickdoglio.convention.internal.configuration.configureTests
import dev.whosnickdoglio.convention.internal.configuration.getVersionOrError
import dev.whosnickdoglio.convention.internal.configuration.versionCatalog
import org.gradle.api.Plugin
import org.gradle.api.Project

internal class AndroidLibraryProjectPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        val libs = target.versionCatalog()
        with(target) {
            pluginManager.apply("com.android.library")
            pluginManager.apply(CACHE_FIX_PLUGIN_ID)
            val convention =
                extensions.create("convention", AndroidLibraryConventionExtension::class.java)

            convention.guard { dependency("releaseRuntimeClasspath") }

            val jvmTargetVersion = libs.getVersionOrError("jdkTarget")

            configureAndroid()

            applyLintingPlugins(jvmTargetVersion)

            configureJvm(
                toolchainVersion = libs.getVersionOrError("jdk").toInt(),
                jvmTargetVersion = jvmTargetVersion.toInt(),
            )
            configureLint()
            configureTests()
        }
    }
}
