package io.github.nosequel.command;

import io.github.nosequel.command.annotation.Help;
import io.github.nosequel.command.data.CommandData;
import io.github.nosequel.command.data.CommandExecutingData;
import io.github.nosequel.command.data.ParameterData;
import io.github.nosequel.command.data.impl.BaseCommandData;
import io.github.nosequel.command.data.impl.SubcommandData;
import io.github.nosequel.command.exception.ConditionFailedException;
import io.github.nosequel.command.executor.CommandExecutor;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collection;

public interface CommandInvoker {

    BaseCommandData getData();

    /**
     * Handle the command execution for a command executor
     *
     * @param executor the executor of the command
     * @param label    the label used to perform the command
     * @param strings  the provided arguments
     */
    default void execute(CommandExecutor executor, String label, String[] strings) {
        final CommandExecutingData executingData = this.getData(strings);
        final CommandData<?> commandData = executingData.getCommandData();
        final String[] args = executingData.getArgs();

        if (!executingData.getPermission().isEmpty() && !executor.hasPermission(executingData.getPermission())) {
            executor.sendMessage("&cNo permission.");
            return;
        }

        if (!executor.isUser() && executingData.getCommandData().isUserOnly()) {
            executor.sendMessage("&cOnly users can perform this command.");
            return;
        }

        if (commandData instanceof BaseCommandData && commandData.getMethod().isAnnotationPresent(Help.class)) {
            executor.sendMessage(CommandHandler.getCommandHandler()
                    .getHelpHandler()
                    .getHelpMessage((BaseCommandData) commandData)
            );
        }

        final ParameterData[] parameterDatum = executingData.getCommandData().getParameterData();
        final Object[] data;

        if (parameterDatum != null) {
            data = new Object[parameterDatum.length];

            for (int i = 0; i < parameterDatum.length; i++) {
                final ParameterData parameterData = parameterDatum[i];

                if (i >= args.length && parameterData.getDefaultValue() == null) {
                    executor.sendMessage("&c" + executingData.getCommandData().getUsageMessage(label));
                    return;
                }

                try {
                    if (i >= args.length) {
                        data[i] = parameterData.getAdapter().convert(executor, parameterData.getDefaultValue());
                    } else if (parameterData.isLastIndex() && parameterData.isString()) {
                        final String[] array = Arrays.copyOfRange(args, i, args.length);
                        final StringBuilder builder = new StringBuilder();

                        for (String string : array) {
                            if (!builder.toString().isEmpty()) {
                                builder.append(" ");
                            }

                            builder.append(string);
                        }

                        data[i] = builder.toString();
                    } else {
                        data[i] = parameterData.getAdapter().convert(executor, args[i]);
                    }

                    if (data[i] == null) {
                        throw new NullPointerException("Conversion returned null");
                    }
                } catch (Exception exception) {
                    parameterData.getAdapter().handleException(executor, args[i], exception);
                    return;
                }
            }
        } else {
            data = new Object[0];
        }

        try {
            executingData.getCommandData().invoke(executor, data);
        } catch (InvocationTargetException | IllegalAccessException exception) {
            if (exception.getCause() instanceof ConditionFailedException) {
                executor.sendMessage("&cError: " + exception.getCause().getMessage());
            } else {
                exception.printStackTrace();
            }
        }
    }

    /**
     * Get a {@link CommandExecutingData} object from the {@link CommandInvoker#getData()}
     * command data object.
     *
     * @param strings the strings to find the label from
     * @return the data
     */
    default CommandExecutingData getData(String[] strings) {
        if (strings.length >= 1) {
            final SubcommandData data = this.getByLabel(this.getData().getSubcommandData(), strings[0]);

            if (data != null) {
                return new CommandExecutingData(
                        Arrays.copyOfRange(strings, 1, strings.length),
                        data,
                        data.getCommand().permission()
                );
            }
        }

        return new CommandExecutingData(
                strings,
                this.getData(),
                this.getData().getCommand().permission()
        );
    }

    /**
     * Get a {@link SubcommandData} object by the
     * sub command's label from a list of datum.
     *
     * @param datum the datum to find the sub command from
     * @param label the found label
     * @return the found data, or null
     */
    default SubcommandData getByLabel(Collection<SubcommandData> datum, String label) {
        for (SubcommandData data : datum) {
            if (data.getCommand().label().equalsIgnoreCase(label)) {
                return data;
            }
        }

        return null;
    }

}
