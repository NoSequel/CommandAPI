package io.github.nosequel.command.data.impl;

import io.github.nosequel.command.annotation.Command;
import io.github.nosequel.command.annotation.Subcommand;
import io.github.nosequel.command.data.CommandData;
import lombok.Getter;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

public class BaseCommandData extends CommandData<Command> {

    @Getter
    private final Set<SubcommandData> subcommandData = new HashSet<>();

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

    public boolean isParentOf(Subcommand subcommand) {
        return subcommand.parentLabel().equalsIgnoreCase(this.getCommand().label());
    }

}
