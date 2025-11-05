// Copyright (C) 2025 Nicholas Doglio
// SPDX-License-Identifier: MIT
package dev.whosnickdoglio.convention.extension

import javax.inject.Inject
import org.gradle.api.Project

/**
 * A Gradle extension that provides configuration methods for common build conventions.
 *
 * This extension can be accessed via the `convention` DSL.
 */
public abstract class ConventionExtension @Inject constructor(private val project: Project) {

    /**
     * Enables code coverage reporting using the Kover plugin.
     *
     * This method applies the 'org.jetbrains.kotlinx.kover' plugin to the project, which provides
     * code coverage analysis for Kotlin projects.
     */
    public fun enableCodeCoverageWithKover() {
        project.pluginManager.apply("org.jetbrains.kotlinx.kover")
    }
}
