package io.github.nosequel.command;

import io.github.nosequel.command.adapter.TypeAdapter;
import io.github.nosequel.command.adapter.impl.DoubleTypeAdapter;
import io.github.nosequel.command.adapter.impl.IntegerTypeAdapter;
import io.github.nosequel.command.adapter.impl.LocationTypeAdapter;
import io.github.nosequel.command.adapter.impl.PlayerTypeAdapter;
import io.github.nosequel.command.annotation.Command;
import io.github.nosequel.command.annotation.Subcommand;
import io.github.nosequel.command.data.impl.BaseCommandData;
import io.github.nosequel.command.data.impl.SubcommandData;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandMap;
import org.bukkit.entity.Player;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

public class CommandHandler {

    @Getter
    private static CommandHandler commandHandler;

    private final Map<Class<?>, TypeAdapter<?>> typeAdapters = new HashMap<>();
    private final List<BaseCommandData> commands = new ArrayList<>();

    private final CommandMap commandMap;
    private final String fallbackPrefix;

    public CommandHandler(String fallbackPrefix) {
        commandHandler = this;

        this.fallbackPrefix = fallbackPrefix;

        if ((this.commandMap = this.getCommandMap()) == null) {
            throw new IllegalArgumentException("Unable to find CommandMap inside of Bukkit#getPluginManager");
        }

        this.registerTypeAdapter(Player.class, new PlayerTypeAdapter());
        this.registerTypeAdapter(Location.class, new LocationTypeAdapter());
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
     * Register a command to the {@link CommandHandler#commandMap}
     * and the {@link CommandHandler#commands} list.
     *
     * @param data the command to register
     */
    private void register(BaseCommandData data) {
        this.commands.add(data);
        this.commandMap.register(fallbackPrefix, new CommandExecutor(data));
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

    /**
     * Get the {@link CommandMap} registered
     * inside of the {@link org.bukkit.plugin.SimplePluginManager} class
     *
     * @return the found command map, or null
     */
    private CommandMap getCommandMap() {
        try {
            final Field field = Bukkit.getPluginManager().getClass().getDeclaredField("commandMap");
            field.setAccessible(true);

            return (CommandMap) field.get(Bukkit.getPluginManager());
        } catch (NoSuchFieldException | IllegalAccessException exception) {
            exception.printStackTrace();
        }

        return null;
    }

}