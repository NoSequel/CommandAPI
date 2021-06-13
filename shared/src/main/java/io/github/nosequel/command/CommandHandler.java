package io.github.nosequel.command;

import io.github.nosequel.command.adapter.TypeAdapter;
import io.github.nosequel.command.adapter.impl.DoubleTypeAdapter;
import io.github.nosequel.command.annotation.Command;
import io.github.nosequel.command.annotation.Subcommand;
import io.github.nosequel.command.data.impl.BaseCommandData;
import io.github.nosequel.command.data.impl.SubcommandData;
import io.github.nosequel.command.adapter.impl.IntegerTypeAdapter;
import lombok.Getter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

public abstract class CommandHandler {

    @Getter
    private static CommandHandler commandHandler;

    private final Map<Class<?>, TypeAdapter<?>> typeAdapters = new HashMap<>();
    private final List<BaseCommandData> commands = new ArrayList<>();

    public CommandHandler() {
        commandHandler = this;

        this.registerTypeAdapter(Integer.class, new IntegerTypeAdapter());
        this.registerTypeAdapter(Double.class, new DoubleTypeAdapter());
    }

    /**
     * Register a command to the handler
     *
     * @param object the object to get the command data from
     */
    public void registerCommand(Object object) {
        final List<Method> commands = this.getMethods(Command.class, object);
        final List<Method> subCommands = this.getMethods(Subcommand.class, object);

        for (Method command : commands) {
            this.register(new BaseCommandData(object, command));
        }

        for (Method subCommand : subCommands) {
            for (BaseCommandData command : this.commands) {
                if (command.isParentOf(subCommand.getAnnotation(Subcommand.class))) {
                    command.getSubcommandData().add(new SubcommandData(object, subCommand));
                }
            }
        }
    }

    /**
     * Register the command to the {@link CommandHandler#commands} list.
     *
     * @param data the command to register
     */
    public void register(BaseCommandData data) {
        this.commands.add(data);
    }

    /**
     * Get all methods annotated with a {@link Annotation} in an object
     *
     * @param annotation the annotation which the method must be annotated with
     * @param object     the object with the methods
     * @param <T>        the type of the annontation
     * @return the list of methods
     */
    private <T extends Annotation> List<Method> getMethods(Class<T> annotation, Object object) {
        return Arrays.stream(object.getClass().getMethods())
                .filter(method -> method.getAnnotation(annotation) != null)
                .collect(Collectors.toList());
    }


    /**
     * Find a type adapter by a provided {@link Class}
     *
     * @param clazz the class to find the type adapter by
     * @return the type adapter, or null
     */
    public TypeAdapter<?> findTypeAdapter(Class<?> clazz) {
        return this.typeAdapters.get(clazz);
    }


    /**
     * Register a new {@link TypeAdapter} to the {@link CommandHandler#typeAdapters} field
     *
     * @param clazz   the class to register the type adapter to
     * @param adapter the type adapter to register
     * @param <T>     the type of the class
     */
    public <T> void registerTypeAdapter(Class<T> clazz, TypeAdapter<T> adapter) {
        this.typeAdapters.put(clazz, adapter);
    }
}