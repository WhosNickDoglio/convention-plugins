// Copyright (C) 2025 Nicholas Doglio
// SPDX-License-Identifier: MIT
package dev.whosnickdoglio.convention

import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.model.ObjectFactory

/**
 * A Gradle extension that provides configuration methods for common build conventions.
 *
 * This extension can be accessed via the `convention` DSL.
 */
public abstract class ConventionExtension
@Inject
constructor(private val objects: ObjectFactory, private val project: Project) {
    /**
     * Enables code coverage reporting using the Kover plugin.
     *
     * This method applies the 'org.jetbrains.kotlinx.kover' plugin to the project, which provides
     * code coverage analysis for Kotlin projects.
     */
    public fun codeCoverage(action: Action<KoverHandler>) {
        project.pluginManager.apply("org.jetbrains.kotlinx.kover")
        action.execute(objects.newInstance(KoverHandler::class.java))
    }
}
