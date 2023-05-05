package com.nagarro.remotelearning.annotations;

import com.sun.istack.internal.NotNull;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Column {
    @NotNull
    String name();

    @NotNull
    String type();

    boolean primaryKey() default false;

    boolean allowNull() default true;

    boolean unique() default false;

}
