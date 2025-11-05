// Copyright (C) 2025 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.convention

import com.autonomousapps.kit.GradleBuilder
import com.autonomousapps.kit.truth.TestKitTruth.Companion.assertThat
import dev.whosnickdoglio.convention.fixture.GradlePluginFixture
import org.junit.Test

class KotlinJvmProjectPluginFunctionalTest {

    @Test
    fun `given the jvm plugin is applied when the lint task is run then the build should succeed`() {
        // given
        val project = GradlePluginFixture().gradleProject
        // when
        val result = GradleBuilder.build(project.rootDir, ":project:lint")
        // then
        assertThat(result).task(":project:lint").succeeded()
    }
}
