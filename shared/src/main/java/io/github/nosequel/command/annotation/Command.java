package io.github.nosequel.command.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Command {

    /**
     * The label required for the command to process
     *
     * @return the label
     */
    String label();

    /**
     * The permission required to execute the command
     *
     * @return the permission required
     */
    String permission() default "";

    /**
     * Get the description of the command to display within the help message
     *
     * @return the description of the command
     */
    String description() default "Default command description";

    /**
     * The sub-labels which can be used to execute
     * the command.
     *
     * @return the aliases
     */
    String[] aliases() default {};

    boolean userOnly() default false;

}
