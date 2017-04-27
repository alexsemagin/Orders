package ru.rarus.restart.orders.di.scope;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by kosigo on 15.02.17.
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface UserScope {
}
