// Copyright (C) 2025 Nicholas Doglio
// SPDX-License-Identifier: MIT
package dev.whosnickdoglio.convention.configuration

import com.ncorti.ktfmt.gradle.KtfmtExtension
import org.gradle.api.Project

internal fun Project.configureKtfmt() {
    pluginManager.apply("com.ncorti.ktfmt.gradle")
    extensions.getByType(KtfmtExtension::class.java).apply {
        kotlinLangStyle()
        removeUnusedImports.set(true)
    }
}
