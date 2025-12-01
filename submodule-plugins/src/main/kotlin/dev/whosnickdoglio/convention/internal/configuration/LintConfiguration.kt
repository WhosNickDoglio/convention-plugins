// Copyright (C) 2025 Nicholas Doglio
// SPDX-License-Identifier: MIT
package dev.whosnickdoglio.convention.internal.configuration

import com.android.build.api.dsl.Lint
import java.io.File
import org.gradle.api.Project

internal val agpPlugins = listOf("com.android.application", "com.android.library")

internal fun Project.isAndroidProject(): Boolean =
    agpPlugins.any { id -> pluginManager.hasPlugin(id) }

internal fun Project.configureLint() {
    if (!isAndroidProject()) {
        pluginManager.apply("com.android.lint")
        extensions
            .getByType(Lint::class.java)
            .configure(baselineLineFile = file("lint-baseline.xml"))
    }
}

internal fun Lint.configure(baselineLineFile: File, disabledRules: Set<String> = emptySet()) {
    htmlReport = false
    xmlReport = false
    textReport = true
    absolutePaths = false
    checkTestSources = true
    warningsAsErrors = true
    baseline = baselineLineFile
    disable.apply {
        addAll(disabledRules)
        add("GradleDependency")
        add("ObsoleteLintCustomCheck")
        add("AndroidGradlePluginVersion")
    }
}
