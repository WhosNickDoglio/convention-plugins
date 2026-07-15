// Copyright (C) 2025 Nicholas Doglio
// SPDX-License-Identifier: MIT
package dev.whosnickdoglio.convention.fixture

import com.autonomousapps.kit.AbstractGradleProject
import com.autonomousapps.kit.GradleProject
import com.autonomousapps.kit.Source
import com.autonomousapps.kit.gradle.Plugin

// TODO configuration
fun GradlePluginFixture(): GradleTestFixtureProject = MyFixture()

abstract class GradleTestFixtureProject : AbstractGradleProject() {
    abstract val gradleProject: GradleProject
}

private val EMPTY_LINT_BASELINE =
    """
    <?xml version="1.0" encoding="UTF-8"?>
    <issues format="6" by="lint 8.9.0" type="baseline" client="gradle" dependencies="false" name="AGP (8.9.0)" variant="all" version="8.9.0">

    </issues>

    """
        .trimIndent()

private val defaultPlugins: List<Plugin>
    get() =
        listOf(
            Plugin(
                "dev.whosnickdoglio.convention.kotlin",
                AbstractGradleProject.PLUGIN_UNDER_TEST_VERSION,
            ),
            Plugin("org.jetbrains.kotlin.jvm", "2.4.10"),
            Plugin("dev.detekt", "2.0.0-alpha.5"),
            Plugin("com.autonomousapps.dependency-analysis", "3.16.1"),
            Plugin("com.squareup.sort-dependencies", "0.20.0"),
            Plugin("com.android.lint", "9.3.0"),
            Plugin("com.ncorti.ktfmt.gradle", "0.26.0"),
        )

private const val VERSION_CATALOG =
    """
                [versions]
                jdk = "25"
                jdkTarget = "17"
                kotlin = "2.4.10"
                detekt = "2.0.0-alpha.5"
                dependencyAnalysis = "3.16.1"
                ktfmt-gradle = "0.26.0"

                [plugins]
                kotlin-jvm = { id = "org.jetbrains.kotlin.jvm", version.ref = "kotlin" }
                detekt = { id = "dev.detekt", version.ref = "detekt" }
                dependency-analysis = { id = "com.autonomousapps.dependency-analysis", version.ref = "dependencyAnalysis" }
                sort-dependencies = { id = "com.squareup.sort-dependencies", version = "0.20.0" }
                android-lint = { id = "com.android.lint", version = "9.3.0" }
                ktfmt = { id = "com.ncorti.ktfmt.gradle", version.ref = "ktfmt-gradle"}
                """

private class MyFixture : GradleTestFixtureProject() {

    override val gradleProject: GradleProject = build()

    private fun build(): GradleProject {
        return newGradleProjectBuilder()
            .withRootProject { withVersionCatalog(VERSION_CATALOG.trimIndent()) }
            .withSubproject("project") {
                withFile("lint-baseline.xml", EMPTY_LINT_BASELINE)
                sources =
                    mutableListOf(
                        Source.kotlin(
                                """
                          package com.example.project

                            public class Example {
                                public fun sampleMethod() {
                                    println("Hello, World!")
                                }
                            }
                          """
                            )
                            .withPath(
                                /* packagePath= */ "com.example.project",
                                /* className= */ "Example",
                            )
                            .build()
                    )
                withBuildScript { plugins(defaultPlugins) }
            }
            .write()
    }
}
