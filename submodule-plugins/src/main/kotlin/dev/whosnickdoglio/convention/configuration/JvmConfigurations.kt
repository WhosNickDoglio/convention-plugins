/*
 * MIT License
 *
 * Copyright (c) 2025 Nicholas Doglio
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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
