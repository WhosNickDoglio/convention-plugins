// Copyright (C) 2025 Nicholas Doglio
// SPDX-License-Identifier: MIT
package dev.whosnickdoglio.convention

import org.gradle.api.Plugin
import org.gradle.api.Project

internal class RootProjectPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        // apply gradle doctor
        // updateDaemonJvm
    }
}
