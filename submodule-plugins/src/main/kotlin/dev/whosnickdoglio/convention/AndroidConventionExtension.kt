// Copyright (C) 2025 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.convention

import javax.inject.Inject
import org.gradle.api.Project
import org.gradle.api.model.ObjectFactory

public abstract class AndroidConventionExtension(objects: ObjectFactory, project: Project) :
    CoreConventionExtension(objects, project)

public abstract class AndroidApplicationConventionExtension
@Inject
constructor(objects: ObjectFactory, project: Project) : AndroidConventionExtension(objects, project)

public abstract class AndroidLibraryConventionExtension
@Inject
constructor(objects: ObjectFactory, project: Project) : AndroidConventionExtension(objects, project)
