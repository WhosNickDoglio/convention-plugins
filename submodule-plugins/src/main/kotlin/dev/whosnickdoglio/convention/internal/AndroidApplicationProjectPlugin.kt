// Copyright (C) 2025 Nicholas Doglio
// SPDX-License-Identifier: MIT
package dev.whosnickdoglio.convention.internal

import dev.whosnickdoglio.convention.CoreConventionExtension
import dev.whosnickdoglio.convention.internal.configuration.applyLintingPlugins
import dev.whosnickdoglio.convention.internal.configuration.configureAndroid
import dev.whosnickdoglio.convention.internal.configuration.configureJvm
import dev.whosnickdoglio.convention.internal.configuration.configureLint
import dev.whosnickdoglio.convention.internal.configuration.configureTests
import dev.whosnickdoglio.convention.internal.configuration.getVersionOrError
import dev.whosnickdoglio.convention.internal.configuration.versionCatalog
import org.gradle.api.Plugin
import org.gradle.api.Project

internal class AndroidApplicationProjectPlugin : Plugin<Project> {
    // apply "base" stuff
    // dependency guard
    // compose guard
    // coreLibraryDesugaring
    override fun apply(target: Project) {
        val libs = target.versionCatalog()
        with(target) {
            pluginManager.apply("com.android.application")
            pluginManager.apply(CACHE_FIX_PLUGIN_ID)
            pluginManager.apply(KOTLIN_ANDROID_PLUGIN_ID)
            pluginManager.apply(KOTLIN_COMPOSE_PLUGIN_ID)
            extensions.create("convention", CoreConventionExtension::class.java)

            val jvmTargetVersion = libs.getVersionOrError("jdkTarget")

            applyLintingPlugins(jvmTargetVersion)

            configureAndroid()

            configureJvm(
                toolchainVersion = libs.getVersionOrError("jdk").toInt(),
                jvmTargetVersion = jvmTargetVersion.toInt(),
            )
            configureLint()
            configureTests()
        }
    }
}
