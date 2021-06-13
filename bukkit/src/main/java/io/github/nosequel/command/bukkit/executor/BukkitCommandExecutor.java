package io.github.nosequel.command.bukkit.executor;

import io.github.nosequel.command.executor.CommandExecutor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Getter
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

    /**
     * Get the player object of the command sender
     */
    public Player getPlayer() {
        return (Player) this.sender;
    }
}