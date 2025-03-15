# Gradle Convention Plugins

[![CI](https://github.com/WhosNickDoglio/convention-plugins/actions/workflows/ci.yml/badge.svg)](https://github.com/WhosNickDoglio/convention-plugins/actions/workflows/ci.yml)
[![Maven Central Version](https://img.shields.io/maven-central/v/dev.whosnickdoglio/convention-plugins)](https://central.sonatype.com/artifact/dev.whosnickdoglio/convention-plugins/overview)
[![Contributor Covenant](https://img.shields.io/badge/Contributor%20Covenant-2.1-4baaaa.svg)](.github/CODE_OF_CONDUCT.md)
[![codecov](https://codecov.io/github/WhosNickDoglio/convention-plugins/graph/badge.svg?token=73spSKaSVx)](https://codecov.io/github/WhosNickDoglio/convention-plugins)
[![Maintainability](https://api.codeclimate.com/v1/badges/dae2d3864c78fbe7ff22/maintainability)](https://codeclimate.com/github/WhosNickDoglio/convention-plugins/maintainability)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

A handful of JVM and Android specific Gradle convention plugins to simplify some project setup

## Usage 

```kotlin
plugins {
    id("dev.whosnickdoglio.convention.kotlin") version "LATEST_VERSION"
    id("dev.whosnickdoglio.convention.gradle") version "LATEST_VERSION"
}
```

Snapshots are also published to Maven Central Portal and can be consumed by adding the following repository to your build.gradle file
```kotlin
maven(url = "https://central.sonatype.com/repository/maven-snapshots/")
```

## Plugins

### `dev.whosnickdoglio.convention.kotlin`

Used where you would normally use `org.jetbrains.kotlin.jvm` (But if you're creating a Gradle plugin
you'll want `dev.whosnickdoglio.convention.gradle` instead)

Plugins that are applied and expected to be on the classpath:
- `org.jetbrains.kotlin.jvm`
- `io.gitlab.arturbosch.detekt`
- `com.autonomousapps.dependency-analysis`
- `com.squareup.sort-dependencies`
- `com.android.lint`
- `com.diffplug.spotless` (Configured to use `ktfmt` KotlinLang code style)


Also configures JDK toolchains and the java target version

### `dev.whosnickdoglio.convention.gradle`

Used where you'd normally use `java-gradle-plugin`

Plugins that are applied and expected to be on the classpath:
- `java-gradle-plugin`
- `org.jetbrains.kotlin.jvm`
- `io.gitlab.arturbosch.detekt`
- `com.autonomousapps.dependency-analysis`
- `com.squareup.sort-dependencies`
- `com.android.lint`
- `com.diffplug.spotless` (Configured to use `ktfmt` KotlinLang code style)
- `com.autonomousapps.plugin-best-practices-plugin`
- `com.autonomousapps.testkit` (For functional tests fo your plugin)


Also configures JDK toolchains & the java target version and sets `validatePlugins` to strict

# License

	MIT License

	Copyright (c) 2025 Nicholas Doglio

	Permission is hereby granted, free of charge, to any person obtaining a copy
	of this software and associated documentation files (the "Software"), to deal
	in the Software without restriction, including without limitation the rights
	to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
	copies of the Software, and to permit persons to whom the Software is
	furnished to do so, subject to the following conditions:

	The above copyright notice and this permission notice shall be included in all
	copies or substantial portions of the Software.

	THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
	IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
	FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
	AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
	LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
	OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
	SOFTWARE.
