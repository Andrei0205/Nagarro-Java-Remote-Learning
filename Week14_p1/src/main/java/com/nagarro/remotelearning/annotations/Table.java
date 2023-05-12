package com.nagarro.remotelearning.annotations;

import com.sun.istack.internal.NotNull;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Table {
    @NotNull
    String name();
}