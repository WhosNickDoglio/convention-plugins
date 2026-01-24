// Copyright (C) 2025 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.convention.handlers

import com.dropbox.gradle.plugins.dependencyguard.DependencyGuardPluginExtension
import dev.whosnickdoglio.convention.internal.KOTLIN_COMPOSE_PLUGIN_ID
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.model.ObjectFactory
import org.gradle.api.plugins.ExtensionAware
import org.jetbrains.dokka.gradle.DokkaPlugin
import org.jetbrains.kotlin.gradle.dsl.KotlinBaseExtension
import org.jetbrains.kotlin.gradle.dsl.abi.AbiValidationExtension
import org.jetbrains.kotlin.gradle.dsl.abi.ExperimentalAbiValidation

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

    public fun dependencyGuard(name: String? = null): Unit =
        with(project) {
            val configurationName =
                name
                    ?: if (pluginManager.hasPlugin("com.android.base")) {
                        "releaseRuntimeClasspath"
                    } else {
                        "runtimeClasspath"
                    }

            pluginManager.apply("com.dropbox.dependency-guard")
            extensions
                .getByType(DependencyGuardPluginExtension::class.java)
                .configuration(configurationName)
        }

    public fun composeGuard(): Unit =
        with(project) {
            if (pluginManager.hasPlugin(KOTLIN_COMPOSE_PLUGIN_ID)) {
                pluginManager.apply("com.joetr.compose.guard")
            } else {
                logger.error(
                    "Cannot apply the Compose Guard to a project without the Kotlin Compose plugin."
                )
            }
        }

    public fun dokka(): Unit = with(project) { pluginManager.apply(DokkaPlugin::class.java) }

    @OptIn(ExperimentalAbiValidation::class)
    public fun abi(): Unit =
        with(project) {
            if (pluginManager.hasPlugin("com.android.base")) {
                logger.warn(
                    "Cannot enable BCV, AGP 9 currently does not support it: https://youtrack.jetbrains.com/projects/KT/issues/KT-83410/"
                )
                return@with
            }

            extensions.getByType(KotlinBaseExtension::class.java).apply {
                abiValidation.enabled.set(true)
            }

            // youtrack.jetbrains.com/issue/KT-78525
            tasks.named("check").configure { dependsOn(tasks.named("checkLegacyAbi")) }
        }
}

private val KotlinBaseExtension.abiValidation: AbiValidationExtension
    get() = (this as ExtensionAware).extensions.getByType(AbiValidationExtension::class.java)
