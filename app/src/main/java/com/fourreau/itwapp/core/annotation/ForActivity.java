package com.fourreau.itwapp.core.annotation;

import java.lang.annotation.Retention;

import javax.inject.Qualifier;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by Pierre on 22/04/2015.
 */
@Qualifier
@Retention(RUNTIME)
public @interface ForActivity {
}