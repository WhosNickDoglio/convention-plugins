package dev.whosnickdoglio.convention

import dev.whosnickdoglio.convention.configuration.configureJvm
import dev.whosnickdoglio.convention.configuration.configureLint
import dev.whosnickdoglio.convention.configuration.configureSpotless
import dev.whosnickdoglio.convention.configuration.configureTests
import dev.whosnickdoglio.convention.configuration.versionCatalog
import io.gitlab.arturbosch.detekt.Detekt
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

public class KotlinPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        val libs = target.versionCatalog()
        with(target) {
            val extension = extensions.create("convention", ConventionExtension::class.java)

            pluginManager.apply("org.jetbrains.kotlin.jvm")
            if (extension.kover.get()) {
                pluginManager.apply("org.jetbrains.kotlinx.kover")
            }
            pluginManager.apply("io.gitlab.arturbosch.detekt")
            pluginManager.apply("com.autonomousapps.dependency-analysis")
            pluginManager.apply("com.squareup.sort-dependencies")

            tasks.withType(Detekt::class.java).configureEach {
                it.jvmTarget = JvmTarget.JVM_22.target
            }

            configureJvm(libs.findVersion("jdk").get().requiredVersion.toInt())
            configureLint()
            configureSpotless(libs.findVersion("ktfmt").get().requiredVersion)
            configureTests()
        }
    }
}
