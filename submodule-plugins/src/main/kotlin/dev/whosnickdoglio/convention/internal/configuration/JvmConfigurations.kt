// Copyright (C) 2025 Nicholas Doglio
// SPDX-License-Identifier: MIT
package dev.whosnickdoglio.convention.internal.configuration

import org.gradle.api.Project
import org.gradle.api.tasks.compile.JavaCompile
import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.gradle.jvm.toolchain.JvmVendorSpec
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension
import org.jetbrains.kotlin.gradle.dsl.abi.ExperimentalAbiValidation
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

@OptIn(ExperimentalAbiValidation::class)
internal fun Project.configureJvm(toolchainVersion: Int, jvmTargetVersion: Int) {
    extensions.getByType(KotlinProjectExtension::class.java).apply {
        explicitApi()
        //        abiValidation { enabled.set(true) }
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

    // youtrack.jetbrains.com/issue/KT-78525
    tasks.named("check").configure { dependsOn(tasks.named("checkLegacyAbi")) }
}

// TODO
// private fun KotlinProjectExtension.abiValidation(configure: Action<AbiValidationExtension>) =
//    (this as org.gradle.api.plugins.ExtensionAware).extensions.configure("abiValidation",
// configure)
