/*
 * MIT License
 *
 * Copyright (c) 2025 Nicholas Doglio
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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
 * Convention plugin for JVM Kotlin projects. Sets up some common
 * linting tools like Detekt, Android Lint, Spotless with Ktfmt, and
 * sort-dependencies.
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
