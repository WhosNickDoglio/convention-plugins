// Copyright (C) 2025 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.convention.handlers

import com.dropbox.gradle.plugins.dependencyguard.DependencyGuardPluginExtension
import dev.whosnickdoglio.convention.internal.KOTLIN_COMPOSE_PLUGIN_ID
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.model.ObjectFactory

public abstract class PublishedHandler
@Inject
constructor(private val objectFactory: ObjectFactory, private val project: Project) {

    public fun codeCoverage(action: Action<KoverHandler>) {
        project.pluginManager.apply("org.jetbrains.kotlinx.kover")
        action.execute(objectFactory.newInstance(KoverHandler::class.java))
    }

    public fun codeCoverage() {
        project.pluginManager.apply("org.jetbrains.kotlinx.kover")
    }

    public fun dependencyGuard(name: String = "runtimeClasspath"): Unit =
        with(project) {
            pluginManager.apply("com.dropbox.dependency-guard")
            extensions.getByType(DependencyGuardPluginExtension::class.java).configuration(name)
        }

    public fun composeGuard(): Unit =
        with(project) {
            if (pluginManager.hasPlugin(KOTLIN_COMPOSE_PLUGIN_ID)) {
                pluginManager.apply("com.joetr.compose.guard")
            } else {
                logger.error(
                    "Cannot apply Compose Guard to a project without the Kotlin Compose plugin."
                )
            }
        }
}
