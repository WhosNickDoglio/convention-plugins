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

import io.gitlab.arturbosch.detekt.Detekt
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
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
    `java-gradle-plugin`
    `java-test-fixtures`
}

group = "dev.whosnickdoglio"

version = "0.1"

dependencyGuard { configuration("classpath") }

doctor { warnWhenNotUsingParallelGC = false }

// https://docs.gradle.org/8.9/userguide/gradle_daemon.html#daemon_jvm_criteria
tasks.updateDaemonJvm.configure { jvmVersion = JavaLanguageVersion.of(libs.versions.jdk.get()) }

kotlin {
    explicitApi()
    jvmToolchain {
        languageVersion = JavaLanguageVersion.of(libs.versions.jdk.get().toInt())
        vendor = JvmVendorSpec.AZUL
    }
}

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

lint {
    htmlReport = false
    xmlReport = false
    textReport = true
    absolutePaths = false
    checkTestSources = true
    warningsAsErrors = true
    baseline = file("lint-baseline.xml")
    disable.add("GradleDependency")
}

spotless {
    format("misc") {
        target("*.md", ".gitignore")
        trimTrailingWhitespace()
        endWithNewline()
    }

    kotlin {
        ktfmt(libs.versions.ktfmt.get()).kotlinlangStyle()
        trimTrailingWhitespace()
        endWithNewline()
    }
    kotlinGradle {
        ktfmt(libs.versions.ktfmt.get()).kotlinlangStyle()
        trimTrailingWhitespace()
        endWithNewline()
    }
}

tasks.named<ValidatePlugins>("validatePlugins").configure { enableStricterValidation = true }

tasks.withType<KotlinCompile>().configureEach {
    compilerOptions {
        allWarningsAsErrors = true
        jvmTarget = JvmTarget.JVM_17
    }
}

tasks.withType<JavaCompile>().configureEach {
    sourceCompatibility = JavaVersion.VERSION_17.toString()
    targetCompatibility = JavaVersion.VERSION_17.toString()
}

tasks.withType<Detekt>().configureEach { jvmTarget = "22" }

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
