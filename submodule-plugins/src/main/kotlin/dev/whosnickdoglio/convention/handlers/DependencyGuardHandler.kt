// Copyright (C) 2025 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.convention.handlers

import com.dropbox.gradle.plugins.dependencyguard.DependencyGuardPluginExtension
import javax.inject.Inject
import org.gradle.api.Project

public abstract class DependencyGuardHandler @Inject constructor(project: Project) {

    private val extension = project.extensions.getByType(DependencyGuardPluginExtension::class.java)

    public fun configuration(name: String = "runtimeClasspath") {
        extension.configuration(name)
    }
}
