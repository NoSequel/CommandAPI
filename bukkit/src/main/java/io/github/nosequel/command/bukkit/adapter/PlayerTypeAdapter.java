package io.github.nosequel.command.bukkit.adapter;

import io.github.nosequel.command.adapter.TypeAdapter;
import io.github.nosequel.command.executor.CommandExecutor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlayerTypeAdapter implements TypeAdapter<Player> {

    /**
     * Convert a {@link String} to the {@link Player} object type
     *
     * @param executor the executor of the command
     * @param source   the source to convert
     * @return the converted object
     */
    @Override
    public Player convert(CommandExecutor executor, String source) throws Exception {
        if (source.equalsIgnoreCase("@SELF")) {
            if (!(executor instanceof Player)) {
                return null;
            }

            return (Player) executor;
        }

        return Bukkit.getPlayer(source);
    }
}