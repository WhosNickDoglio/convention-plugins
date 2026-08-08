// Copyright (C) 2025 Nicholas Doglio
// SPDX-License-Identifier: MIT
package dev.whosnickdoglio.convention.internal.configuration

import com.ncorti.ktfmt.gradle.KtfmtExtension
import dev.whosnickdoglio.convention.ConventionOptions
import org.gradle.api.Project

internal fun Project.configureKtfmt() {
    val options = ConventionOptions(this)
    if (!options.ktfmtDisabled.getOrElse(false)) {
        pluginManager.apply("com.ncorti.ktfmt.gradle")
        extensions.getByType(KtfmtExtension::class.java).apply {
            kotlinLangStyle()
            removeUnusedImports.set(true)
        }
    }
}
