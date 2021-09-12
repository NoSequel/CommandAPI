package io.github.nosequel.command.data.impl;

import io.github.nosequel.command.annotation.Command;
import io.github.nosequel.command.annotation.Subcommand;
import io.github.nosequel.command.data.CommandData;
import lombok.Getter;

import java.lang.reflect.Method;
import java.util.LinkedHashSet;
import java.util.Set;

public class BaseCommandData extends CommandData<Command> {

    @Getter
    private final Set<SubcommandData> subcommandData = new LinkedHashSet<>();

    /**
     * Constructor to make a new {@link CommandData} object
     *
     * @param object the object to create it for
     * @param method the method to register to the command data
     */
    public BaseCommandData(Object object, Method method) {
        super(object, method);
    }

    /**
     * Get the type of the annotation
     *
     * @return the type
     */
    @Override
    public Class<Command> getAnnotationType() {
        return Command.class;
    }

    /**
     * Check if the command is user-only
     *
     * @return whether it's user-only or not
     */
    @Override
    public boolean isUserOnly() {
        return this.getCommand().userOnly();
    }

    public boolean isParentOf(Subcommand subcommand) {
        return subcommand.parentLabel().equalsIgnoreCase(this.getCommand().label());
    }

}
