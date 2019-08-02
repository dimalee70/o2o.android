package kz.dragau.larek.di

import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import javax.inject.Scope

@Scope
annotation class PerScene

@Scope
annotation class PerFlow

@Scope
@Retention(RetentionPolicy.CLASS)
annotation class CustomApplicationScope
