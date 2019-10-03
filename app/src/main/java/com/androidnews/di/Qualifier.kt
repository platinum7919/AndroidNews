package com.androidnews.di

import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import javax.inject.Qualifier

@Qualifier
@Retention(RetentionPolicy.RUNTIME)
annotation class ApiServiceQualifier


@Qualifier
@Retention(RetentionPolicy.RUNTIME)
annotation class DatabaseQualifier