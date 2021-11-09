package com.met.tech.injection.scope

import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy.RUNTIME
import javax.inject.Scope
import kotlin.annotation.AnnotationTarget.*

@Scope
@Retention(RUNTIME)
@Target(
    ANNOTATION_CLASS, CLASS, FUNCTION,
    PROPERTY_GETTER, PROPERTY_SETTER
)
annotation class FragmentScope