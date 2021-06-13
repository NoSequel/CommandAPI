package io.github.nosequel.command.data.impl;

import io.github.nosequel.command.annotation.Subcommand;
import io.github.nosequel.command.data.CommandData;

import java.lang.reflect.Method;

public class SubcommandData extends CommandData<Subcommand> {

    /**
     * Constructor to make a new {@link CommandData} object
     *
     * @param object the object to create it for
     * @param method the method to register to the command data
     */
    public SubcommandData(Object object, Method method) {
        super(object, method);
    }

    /**
     * Get the type of the annotation
     *
     * @return the type
     */
    @Override
    public Class<Subcommand> getAnnotationType() {
        return Subcommand.class;
    }
}