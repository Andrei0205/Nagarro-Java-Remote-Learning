package com.nagarro.remotelearning.annotations;

import com.sun.istack.internal.NotNull;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Column {
    public String name() default "";

    @NotNull
    public String type();

    public Constraints constraints() default @Constraints;

}
