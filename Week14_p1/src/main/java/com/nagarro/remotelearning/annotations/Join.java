package com.nagarro.remotelearning.annotations;

import com.sun.istack.internal.NotNull;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Join {
    @NotNull
    String tableToJoin();

    @NotNull
    String joinByColumn();

}
