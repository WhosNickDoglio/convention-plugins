// Copyright (C) 2025 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.convention.configuration

import org.gradle.api.Project
import org.gradle.api.tasks.compile.JavaCompile
import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.gradle.jvm.toolchain.JvmVendorSpec
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

internal fun Project.configureJvm(toolchainVersion: Int, jvmTargetVersion: Int) {
    extensions.getByType(KotlinJvmProjectExtension::class.java).apply {
        explicitApi()
        jvmToolchain { toolchain ->
            toolchain.languageVersion.set(JavaLanguageVersion.of(toolchainVersion))
            toolchain.vendor.set(JvmVendorSpec.AZUL)
        }
    }
    tasks.withType(KotlinCompile::class.java).configureEach { kotlinCompile ->
        kotlinCompile.compilerOptions {
            freeCompilerArgs.add("-Xjdk-release=$jvmTargetVersion")
            allWarningsAsErrors.set(true)
            jvmTarget.set(JvmTarget.fromTarget(jvmTargetVersion.toString()))
        }
    }
    tasks.withType(JavaCompile::class.java).configureEach { javaCompile ->
        javaCompile.options.release.set(jvmTargetVersion)
    }
}
