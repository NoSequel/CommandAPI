package io.github.nosequel.command.annotation;

public @interface Param {

    String name();

    String value() default "";

}