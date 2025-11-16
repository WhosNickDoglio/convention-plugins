// Copyright (C) 2025 Nicholas Doglio
// SPDX-License-Identifier: MIT
plugins {
    alias(libs.plugins.convention.gradle)
    alias(libs.plugins.kotlin.sam)
    alias(libs.plugins.kover)
    alias(libs.plugins.publish)
}

samWithReceiver { annotation(HasImplicitReceiver::class.qualifiedName!!) }

gradleTestKitSupport { disablePublication() }

version = providers.gradleProperty("VERSION_NAME").get()

gradlePlugin {
    plugins {
        register("convention.android.app") {
            id = "dev.whosnickdoglio.convention.android.app"
            implementationClass =
                "dev.whosnickdoglio.convention.internal.AndroidApplicationProjectPlugin"
        }

        register("convention.android.library") {
            id = "dev.whosnickdoglio.convention.android.library"
            implementationClass =
                "dev.whosnickdoglio.convention.internal.AndroidLibraryProjectPlugin"
        }

        register("convention.kotlin") {
            id = "dev.whosnickdoglio.convention.kotlin"
            implementationClass = "dev.whosnickdoglio.convention.internal.KotlinJvmProjectPlugin"
        }

        register("convention.kmp") {
            id = "dev.whosnickdoglio.convention.kmp"
            implementationClass =
                "dev.whosnickdoglio.convention.internal.KotlinMultiplatformProjectPlugin"
        }

        register("convention.gradle") {
            id = "dev.whosnickdoglio.convention.gradle"
            implementationClass = "dev.whosnickdoglio.convention.internal.GradlePluginProjectPlugin"
        }
    }
}

tasks.withType(AbstractPublishToMaven::class.java).configureEach {
    dependsOn(tasks.withType<Sign>())
}

dependencies {
    functionalTestImplementation(libs.junit)
    functionalTestImplementation(libs.testKit.supprt)
    functionalTestImplementation(libs.testKit.truth)
    functionalTestImplementation(libs.testParameterInjector)
    functionalTestImplementation(libs.truth)

    compileOnly(libs.android.gradle)
    compileOnly(libs.dependencyAnalysis.gradle)
    compileOnly(libs.dependencyGuard.gradle)
    compileOnly(libs.detekt.gradle)
    compileOnly(libs.kotlin.gradle)
    compileOnly(libs.kover.gradle)
    compileOnly(libs.ktfmt.gradle)
    compileOnly(libs.sortDependencies.gradle)

    testImplementation(libs.junit)
    testImplementation(libs.testParameterInjector)
    testImplementation(libs.truth)

    lintChecks(libs.androidx.gradle.lints)
}
