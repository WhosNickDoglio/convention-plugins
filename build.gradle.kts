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

plugins {
    alias(libs.plugins.convention.gradle)
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.detekt)
    alias(libs.plugins.lint)
    alias(libs.plugins.doctor)
    alias(libs.plugins.spotless)
    alias(libs.plugins.dependencyAnalysis)
    alias(libs.plugins.dependencyGuard)
    alias(libs.plugins.kover)
    alias(libs.plugins.sortDependencies)
    alias(libs.plugins.testKit)
    alias(libs.plugins.bestPractices)
    alias(libs.plugins.publish)
}

group = "dev.whosnickdoglio"

version = "0.1"

dependencyGuard { configuration("classpath") }

doctor { warnWhenNotUsingParallelGC = false }

// https://docs.gradle.org/8.9/userguide/gradle_daemon.html#daemon_jvm_criteria
tasks.updateDaemonJvm.configure { jvmVersion = JavaLanguageVersion.of(libs.versions.jdk.get()) }

gradleTestKitSupport { disablePublication() }

gradlePlugin {
    plugins {
        register("convention.kotlin") {
            id = "dev.whosnickdoglio.convention.kotlin"
            implementationClass = "dev.whosnickdoglio.convention.KotlinProjectPlugin"
        }

        register("convention.gradle") {
            id = "dev.whosnickdoglio.convention.gradle"
            implementationClass = "dev.whosnickdoglio.convention.GradlePluginProjectPlugin"
        }
    }
}

dependencies {
    functionalTestImplementation(libs.testKit.supprt)
    functionalTestImplementation(libs.testKit.truth)

    compileOnly(libs.android.gradle)
    compileOnly(libs.dependencyAnalysis.gradle)
    compileOnly(libs.detekt.gradle)
    compileOnly(libs.kotlin.gradle)
    compileOnly(libs.kover.gradle)
    compileOnly(libs.sortDependencies.gradle)
    compileOnly(libs.spotless.gradle)

    testImplementation(libs.junit)
    testImplementation(libs.testParameterInjector)
    testImplementation(libs.truth)

    lintChecks(libs.androidx.gradle.lints)
}
