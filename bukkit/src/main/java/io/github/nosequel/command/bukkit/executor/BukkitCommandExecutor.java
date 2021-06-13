package io.github.nosequel.command.bukkit.executor;

import io.github.nosequel.command.executor.CommandExecutor;
import lombok.RequiredArgsConstructor;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

@RequiredArgsConstructor
public class BukkitCommandExecutor implements CommandExecutor {

    private final CommandSender sender;

    /**
     * Send a message to an executor
     *
     * @param message the message to send
     */
    @Override
    public void sendMessage(String message) {
        this.sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }
}