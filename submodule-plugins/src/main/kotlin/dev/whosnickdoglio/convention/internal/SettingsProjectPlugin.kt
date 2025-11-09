// Copyright (C) 2025 Nicholas Doglio
// SPDX-License-Identifier: MIT
package dev.whosnickdoglio.convention.internal

import org.gradle.api.Plugin
import org.gradle.api.Project

internal class SettingsProjectPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        // apply usual plugins (develocity, foojay, common custom)
        // setup pluginManagement & dependencyResolutionManagement
        // enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
    }
}
