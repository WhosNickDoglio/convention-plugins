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
