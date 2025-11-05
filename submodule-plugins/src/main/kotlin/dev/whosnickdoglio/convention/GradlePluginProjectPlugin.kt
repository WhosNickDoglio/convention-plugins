// Copyright (C) 2025 Nicholas Doglio
// SPDX-License-Identifier: MIT
package dev.whosnickdoglio.convention

import dev.whosnickdoglio.convention.configuration.applyLintingPlugins
import dev.whosnickdoglio.convention.configuration.configureJvm
import dev.whosnickdoglio.convention.configuration.configureLint
import dev.whosnickdoglio.convention.configuration.configureTests
import dev.whosnickdoglio.convention.configuration.getVersionOrError
import dev.whosnickdoglio.convention.configuration.versionCatalog
import dev.whosnickdoglio.convention.extension.ConventionExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.plugin.devel.tasks.ValidatePlugins

internal class GradlePluginProjectPlugin : Plugin<Project> {
    override fun apply(target: Project) =
        with(target) {
            val libs = versionCatalog()
            pluginManager.apply("org.jetbrains.kotlin.jvm")
            pluginManager.apply("java-gradle-plugin")
            pluginManager.apply("com.autonomousapps.plugin-best-practices-plugin")
            pluginManager.apply("com.autonomousapps.testkit")
            extensions.create("convention", ConventionExtension::class.java)

            val jvmTargetVersion = libs.getVersionOrError("jdkTarget")

            applyLintingPlugins(jvmTargetVersion)

            configureJvm(
                toolchainVersion = libs.getVersionOrError("jdk").toInt(),
                jvmTargetVersion = jvmTargetVersion.toInt(),
            )
            configureLint()
            configureTests()

            tasks.withType(ValidatePlugins::class.java).named("validatePlugins").configure {
                validatePlugins ->
                validatePlugins.enableStricterValidation.set(true)
            }
        }
}
