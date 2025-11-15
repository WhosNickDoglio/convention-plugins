// Copyright (C) 2025 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.convention.handlers

import javax.inject.Inject
import kotlinx.kover.gradle.plugin.dsl.KoverProjectExtension
import kotlinx.kover.gradle.plugin.dsl.KoverReportsConfig
import org.gradle.api.Action
import org.gradle.api.Project

public abstract class KoverHandler @Inject constructor(project: Project) {

    private val extension = project.extensions.getByType(KoverProjectExtension::class.java)

    public fun reports(block: Action<KoverReportsConfig>) {
        extension.reports(block)
    }
}
