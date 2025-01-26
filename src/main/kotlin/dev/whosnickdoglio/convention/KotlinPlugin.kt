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

import dev.whosnickdoglio.convention.configuration.configureJvm
import dev.whosnickdoglio.convention.configuration.configureLint
import dev.whosnickdoglio.convention.configuration.configureSpotless
import dev.whosnickdoglio.convention.configuration.configureTests
import dev.whosnickdoglio.convention.configuration.versionCatalog
import io.gitlab.arturbosch.detekt.Detekt
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

public class KotlinPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        val libs = target.versionCatalog()
        with(target) {
            val extension = extensions.create("convention", ConventionExtension::class.java)

            pluginManager.apply("org.jetbrains.kotlin.jvm")
            if (extension.kover.get()) {
                pluginManager.apply("org.jetbrains.kotlinx.kover")
            }
            pluginManager.apply("io.gitlab.arturbosch.detekt")
            pluginManager.apply("com.autonomousapps.dependency-analysis")
            pluginManager.apply("com.squareup.sort-dependencies")

            tasks.withType(Detekt::class.java).configureEach {
                it.jvmTarget = JvmTarget.JVM_22.target
            }

            configureJvm(libs.findVersion("jdk").get().requiredVersion.toInt())
            configureLint()
            configureSpotless(libs.findVersion("ktfmt").get().requiredVersion)
            configureTests()
        }
    }
}
