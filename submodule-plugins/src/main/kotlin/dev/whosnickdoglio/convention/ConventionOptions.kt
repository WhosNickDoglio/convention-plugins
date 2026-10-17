// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.convention

import foundry.gradle.properties.PropertyResolver
import org.gradle.api.Project
import javax.inject.Inject

internal open class ConventionOptions @Inject constructor(project: Project) {

    private companion object {
        private const val CONVENTION_PREFIX = "convention."
    }

    private val propertyResolver = PropertyResolver(project.rootProject)

    internal val ktfmtDisabled = propertyResolver.booleanProvider(
        key = CONVENTION_PREFIX + "ktfmt.disabled",
        defaultValue = false,
    )

    internal val sortDependenciesDisabled = propertyResolver.booleanProvider(
        key = CONVENTION_PREFIX + "sortDependencies.disabled",
        defaultValue = false,
    )
}
