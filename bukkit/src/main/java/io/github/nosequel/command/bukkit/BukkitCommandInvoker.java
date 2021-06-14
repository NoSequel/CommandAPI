package io.github.nosequel.command.bukkit;

import io.github.nosequel.command.CommandInvoker;
import io.github.nosequel.command.bukkit.executor.BukkitCommandExecutor;
import io.github.nosequel.command.data.CommandExecutingData;
import io.github.nosequel.command.data.impl.BaseCommandData;
import io.github.nosequel.command.data.impl.SubcommandData;
import io.github.nosequel.command.data.ParameterData;
import lombok.Getter;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collection;

@Getter
public class BukkitCommandInvoker extends Command implements CommandInvoker {

    private final BaseCommandData data;

    /**
     * Constructor to make a new {@link CommandInvoker}
     * object with a provided {@link BaseCommandData} object.
     *
     * @param data the data to use for the command
     */
    public BukkitCommandInvoker(BaseCommandData data) {
        super(data.getCommand().label());

        this.data = data;

        if (data.getCommand().aliases().length > 0) {
            this.setAliases(Arrays.asList(data.getCommand().aliases()));
        }
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] strings) {
        this.execute(new BukkitCommandExecutor(sender), label, strings);
        return false;
    }

    @Override
    public void execute(io.github.nosequel.command.executor.CommandExecutor executor, String label, String[] strings) {
        CommandInvoker.super.execute(executor, label, strings);
    }
}