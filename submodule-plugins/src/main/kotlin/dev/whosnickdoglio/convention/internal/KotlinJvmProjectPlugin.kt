// Copyright (C) 2025 Nicholas Doglio
// SPDX-License-Identifier: MIT
package dev.whosnickdoglio.convention.internal

import dev.whosnickdoglio.convention.internal.configuration.applyLintingPlugins
import dev.whosnickdoglio.convention.internal.configuration.configureJvm
import dev.whosnickdoglio.convention.internal.configuration.configureLint
import dev.whosnickdoglio.convention.internal.configuration.configureTests
import dev.whosnickdoglio.convention.internal.configuration.getVersionOrError
import dev.whosnickdoglio.convention.internal.configuration.versionCatalog
import dev.whosnickdoglio.convention.ConventionExtension
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
