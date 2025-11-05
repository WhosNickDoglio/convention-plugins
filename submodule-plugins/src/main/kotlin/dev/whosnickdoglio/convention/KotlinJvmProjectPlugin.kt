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

/**
 * Convention plugin for JVM Kotlin projects. Sets up some common linting tools like Detekt, Android
 * Lint, Spotless with Ktfmt, and sort-dependencies.
 *
 * ```
 * convention {
 *
 * }
 * ```
 */
internal class KotlinJvmProjectPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        val libs = target.versionCatalog()
        with(target) {
            pluginManager.apply("org.jetbrains.kotlin.jvm")
            extensions.create("convention", ConventionExtension::class.java)

            val jvmTargetVersion = libs.getVersionOrError("jdkTarget")

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
