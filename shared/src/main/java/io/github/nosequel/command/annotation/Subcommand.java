package io.github.nosequel.command.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Subcommand {

    /**
     * The label required for the command to process
     *
     * @return the label
     */
    String label();

    /**
     * The parent label of the subcommand
     *
     * @return the parent label
     */
    String parentLabel();

    /**
     * The permission required to execute the command
     *
     * @return the permission required
     */
    String permission() default "";

    /**
     * The sub-labels which can be used to execute
     * the command.
     *
     * @return the aliases
     */
    String[] aliases() default {};

    boolean userOnly();

}
