// Copyright (C) 2025 Nicholas Doglio
// SPDX-License-Identifier: MIT
package dev.whosnickdoglio.convention.internal

import dev.whosnickdoglio.convention.KmpConventionExtension
import dev.whosnickdoglio.convention.internal.configuration.applyLintingPlugins
import dev.whosnickdoglio.convention.internal.configuration.configureJvm
import dev.whosnickdoglio.convention.internal.configuration.configureTests
import dev.whosnickdoglio.convention.internal.configuration.getVersionOrError
import dev.whosnickdoglio.convention.internal.configuration.versionCatalog
import org.gradle.api.Plugin
import org.gradle.api.Project

internal class KotlinMultiplatformProjectPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        val libs = target.versionCatalog()
        with(target) {
            pluginManager.apply("org.jetbrains.kotlin.multiplatform")
            extensions.create("convention", KmpConventionExtension::class.java)

            val jvmTargetVersion = libs.getVersionOrError("jdkTarget")

            applyLintingPlugins(jvmTargetVersion)

            configureJvm(
                toolchainVersion = libs.getVersionOrError("jdk").toInt(),
                jvmTargetVersion = jvmTargetVersion.toInt(),
            )
            configureTests()
        }
    }
}
