// Copyright (C) 2025 Nicholas Doglio
// SPDX-License-Identifier: MIT
package dev.whosnickdoglio.convention

import dev.whosnickdoglio.convention.handlers.PublishedHandler
import javax.inject.Inject
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.model.ObjectFactory

/**
 * A Gradle extension that provides configuration methods for common build conventions.
 *
 * This extension can be accessed via the `convention` DSL.
 */
public abstract class CoreConventionExtension
@Inject
constructor(objects: ObjectFactory, internal val project: Project) {

    private val publishHandler = objects.newInstance(PublishedHandler::class.java)

    public fun published(action: Action<PublishedHandler>): Unit = action.execute(publishHandler)
}
