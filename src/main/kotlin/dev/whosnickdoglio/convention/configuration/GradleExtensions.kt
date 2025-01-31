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

package dev.whosnickdoglio.convention.configuration

import io.gitlab.arturbosch.detekt.Detekt
import kotlin.jvm.optionals.getOrNull
import org.gradle.api.GradleException
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension

internal fun Project.versionCatalog(catalogName: String = "libs"): VersionCatalog {
    val catalog = extensions.getByType(VersionCatalogsExtension::class.java)

    return catalog.named(catalogName)
}

internal fun VersionCatalog.getVersionOrError(key: String): String {
    val version =
        findVersion(key).getOrNull()
            ?: throw GradleException(
                "Please set a version in your version catalog with the key $key"
            )

    return version.requiredVersion
}

internal fun Project.applyLintingPlugins(jvmTarget: String) {
    pluginManager.apply("io.gitlab.arturbosch.detekt")
    tasks.withType(Detekt::class.java).configureEach { it.jvmTarget = jvmTarget }
    pluginManager.apply("com.autonomousapps.dependency-analysis")
    pluginManager.apply("com.squareup.sort-dependencies")
    val libs = versionCatalog()
    configureSpotless(libs.findVersion("ktfmt").get().requiredVersion)
}
