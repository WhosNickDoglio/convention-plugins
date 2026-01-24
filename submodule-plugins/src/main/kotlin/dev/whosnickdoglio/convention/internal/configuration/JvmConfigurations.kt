// Copyright (C) 2025 Nicholas Doglio
// SPDX-License-Identifier: MIT
package dev.whosnickdoglio.convention.internal.configuration

import org.gradle.api.Project
import org.gradle.api.tasks.compile.JavaCompile
import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.gradle.jvm.toolchain.JvmVendorSpec
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinBaseExtension
import org.jetbrains.kotlin.gradle.dsl.abi.ExperimentalAbiValidation
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

@OptIn(ExperimentalAbiValidation::class)
internal fun Project.configureJvm(toolchainVersion: Int, jvmTargetVersion: Int) {
    extensions.getByType(KotlinBaseExtension::class.java).apply {
        explicitApi()
        jvmToolchain {
            languageVersion.set(JavaLanguageVersion.of(toolchainVersion))
            vendor.set(JvmVendorSpec.AZUL)
        }
    }
    val isAndroid = isAndroidProject()

    tasks.withType(KotlinCompile::class.java).configureEach {
        compilerOptions {
            if (!isAndroid) {
                freeCompilerArgs.add("-Xjdk-release=$jvmTargetVersion")
            }
            allWarningsAsErrors.set(true)
            jvmTarget.set(JvmTarget.fromTarget(jvmTargetVersion.toString()))
        }
    }
    tasks.withType(JavaCompile::class.java).configureEach {
        sourceCompatibility = jvmTargetVersion.toString()
        targetCompatibility = jvmTargetVersion.toString()
    }
}
