// Copyright (C) 2025 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.convention.handlers

import com.dropbox.gradle.plugins.dependencyguard.DependencyGuardPluginExtension
import javax.inject.Inject
import org.gradle.api.Project

public abstract class GuardHandler @Inject constructor(private val project: Project) {

    public fun dependency(name: String = "runtimeClasspath"): Unit =
        with(project) {
            pluginManager.apply("com.dropbox.dependency-guard")
            extensions.getByType(DependencyGuardPluginExtension::class.java).configuration(name)
        }

    public fun compose(): Unit =
        with(project) {
            if (pluginManager.hasPlugin("KOTLIN_COMPOSE_PLUGIN_ID")) {
                pluginManager.apply("com.joetr.compose.guard")
            } else {
                logger.error(
                    "Cannot apply Compose Guard to a project without the Kotlin Compose plugin."
                )
            }
        }
}
