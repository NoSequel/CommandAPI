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
     * Get the description of the command to display within the help message
     *
     * @return the description of the command
     */
    String description() default "Default command description";

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

    /**
     * Get the weight for the command within the help usage message
     *
     * @return the weight
     */
    int weight() default 0;

    boolean userOnly() default false;
}
