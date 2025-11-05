// Copyright (C) 2025 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.convention

import org.gradle.api.Plugin
import org.gradle.api.Project

internal class AndroidLibraryProjectPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        // apply "base" plugin stuff
        // general Android configuration
        // apply compose guard
        // disable debug variant by default
        // cache fix plugin
    }
}
