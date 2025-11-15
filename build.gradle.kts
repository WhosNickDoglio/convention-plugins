// Copyright (C) 2025 Nicholas Doglio
// SPDX-License-Identifier: MIT

plugins {
    alias(libs.plugins.doctor)
    alias(libs.plugins.convention.gradle) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.detekt) apply false
    alias(libs.plugins.lint) apply false
    alias(libs.plugins.ktfmt) apply false
    alias(libs.plugins.dependencyAnalysis) apply false
    alias(libs.plugins.kover) apply false
    alias(libs.plugins.sortDependencies) apply false
    alias(libs.plugins.testKit) apply false
    alias(libs.plugins.bestPractices) apply false
    alias(libs.plugins.publish) apply false
}

// https://docs.gradle.org/8.9/userguide/gradle_daemon.html#daemon_jvm_criteria
tasks.updateDaemonJvm.configure {
    languageVersion = JavaLanguageVersion.of(libs.versions.jdk.get())
    vendor = JvmVendorSpec.AZUL
}
