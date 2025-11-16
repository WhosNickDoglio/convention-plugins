// Copyright (C) 2025 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.convention

import javax.inject.Inject
import org.gradle.api.Project
import org.gradle.api.model.ObjectFactory

public abstract class JvmConventionExtension
@Inject
constructor(objects: ObjectFactory, project: Project) : CoreConventionExtension(objects, project)
