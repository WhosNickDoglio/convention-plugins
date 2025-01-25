package dev.whosnickdoglio.convention.configuration

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension

internal fun Project.versionCatalog(catalogName: String = "libs"): VersionCatalog {
  val catalog = extensions.getByType(VersionCatalogsExtension::class.java)

  return catalog.named(catalogName)
}
