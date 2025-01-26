package dev.whosnickdoglio.convention

import javax.inject.Inject
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property

public abstract class ConventionExtension
@Inject
constructor(private val objectFactory: ObjectFactory) {

    public val kover: Property<Boolean> =
        objectFactory.property(Boolean::class.java).convention(true)
}
