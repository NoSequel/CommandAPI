package io.github.nosequel.command;

import io.github.nosequel.command.data.CommandExecutingData;
import io.github.nosequel.command.data.ParameterData;
import io.github.nosequel.command.data.impl.BaseCommandData;
import io.github.nosequel.command.data.impl.SubcommandData;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class CommandExecutor extends Command {

    private final BaseCommandData data;

    /**
     * Constructor to make a new {@link CommandExecutor}
     * object with a provided {@link BaseCommandData} object.
     *
     * @param data the data to use for the command
     */
    public CommandExecutor(BaseCommandData data) {
        super(data.getCommand().label());

        this.data = data;
        this.setPermission(data.getCommand().permission());

        if (data.getCommand().aliases().length > 0) {
            this.setAliases(Arrays.asList(data.getCommand().aliases()));
        }
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] strings) {
        final CommandExecutingData executingData = this.getData(strings);
        final String[] args = executingData.getArgs();

        if (!executingData.getPermission().isEmpty() && !sender.hasPermission(executingData.getPermission())) {
            sender.sendMessage(ChatColor.RED + "No permission.");
            return false;
        }

        if (executingData.getCommandData().getMethod().getParameters()[0].getType().equals(Player.class) && !(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "You cannot execute this command as console.");
            return false;
        }

        final ParameterData[] parameterDatum = executingData.getCommandData().getParameterData();
        final Object[] data = new Object[parameterDatum.length];

        for (int i = 0; i < parameterDatum.length; i++) {
            final ParameterData parameterData = parameterDatum[i];

            if (i >= args.length) {
                sender.sendMessage(executingData.getCommandData().getUsageMessage(label));
                return true;
            }

            try {
                data[i] = parameterData.getAdapter().convert(sender, args[i]);
            } catch (Exception exception) {
                parameterData.getAdapter().handleException(sender, args[i], exception);
            }
        }

        try {
            executingData.getCommandData().invoke(sender, data);
        } catch (InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Get a {@link CommandExecutingData} object from the {@link CommandExecutor#data}
     * command data object.
     *
     * @param strings the strings to find the label from
     * @return the data
     */
    private CommandExecutingData getData(String[] strings) {
        if (strings.length >= 1) {
            final SubcommandData data = this.getByLabel(this.data.getSubcommandData(), strings[0]);

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
                data,
                data.getCommand().permission()
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
    private SubcommandData getByLabel(Collection<SubcommandData> datum, String label) {
        for (SubcommandData data : datum) {
            if (data.getCommand().label().equalsIgnoreCase(label)) {
                return data;
            }
        }

        return null;
    }
}