package io.github.nosequel.command.data;

import io.github.nosequel.command.adapter.impl.FallbackTypeAdapter;
import io.github.nosequel.command.annotation.Subcommand;
import io.github.nosequel.command.CommandHandler;
import io.github.nosequel.command.adapter.TypeAdapter;
import io.github.nosequel.command.executor.CommandExecutor;
import lombok.Getter;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.stream.Collectors;

@Getter
public abstract class CommandData<T extends Annotation> {

    private final Object object;
    private final Method method;

    private final T command;
    private ParameterData[] parameterData;

    /**
     * Constructor to make a new {@link CommandData} object
     *
     * @param object the object to create it for
     * @param method the method to register to the command data
     */
    public CommandData(Object object, Method method) {
        this.object = object;
        this.method = method;

        if (!this.method.isAnnotationPresent(this.getAnnotationType())) {
            throw new IllegalArgumentException("provided method does not have a " + this.getAnnotationType().getSimpleName() + " annotation.");
        }

        this.command = method.getAnnotation(this.getAnnotationType());
        this.cacheParameterData();
    }

    /**
     * Get the type of the annotation
     *
     * @return the type
     */
    public abstract Class<T> getAnnotationType();

    /**
     * Cache the parameter data of the command
     */
    public void cacheParameterData() {
        final Parameter[] parameters = Arrays.copyOfRange(method.getParameters(), 1, method.getParameters().length);
        final CommandHandler commandHandler = CommandHandler.getCommandHandler();

        if (parameters.length > 0) {
            final ParameterData[] parameterData = new ParameterData[parameters.length];

            for (int i = 0; i < parameters.length; i++) {
                final Parameter parameter = parameters[i];
                TypeAdapter<?> typeAdapter = commandHandler.findTypeAdapter(parameter.getType());

                if (typeAdapter == null) {
                    typeAdapter = new FallbackTypeAdapter();
                }

                parameterData[i] = new ParameterData(
                        typeAdapter,
                        parameter,
                        i == parameters.length - 1
                );
            }

            this.parameterData = parameterData;
        }
    }

    /**
     * Invoke the command using the provided arguments.
     *
     * @param sender     the sender which invoked the command
     * @param parameters the parameters used to invoke the command
     * @throws InvocationTargetException thrown by {@link Method#invoke(Object, Object...)}
     * @throws IllegalAccessException    thrown by {@link Method#invoke(Object, Object...)}
     */
    public void invoke(CommandExecutor sender, Object... parameters) throws InvocationTargetException, IllegalAccessException {
        final Object[] objects = new Object[parameters == null ? 1 : parameters.length + 1];

        if (parameters != null) {
            System.arraycopy(parameters, 0, objects, 1, parameters.length);
        }

        objects[0] = sender;

        this.method.invoke(this.object, objects);
    }

    /**
     * Get the usage message to send whenever the
     * executor didn't provide the correct arguments.
     *
     * @param label the label to display
     * @return the usage message
     */
    public String getUsageMessage(String label) {
        final String arguments = " " + Arrays.stream(this.parameterData)
                .map(argument -> "<" + argument.getParameterName() + ">")
                .collect(Collectors.joining(" "));

        if (this.getCommand() instanceof Subcommand) {
            return "§c/" + label + " " + ((Subcommand) this.getCommand()).label() + arguments;
        }

        return "§c/" + label + arguments;
    }
}