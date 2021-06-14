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
     * Check if the executor has a permission
     *
     * @param permission the permission to check for
     * @return whether the executor has the permission
     */
    @Override
    public boolean hasPermission(String permission) {
        return this.sender.hasPermission(permission);
    }

    /**
     * Check if the executor is a user, instead of the console/terminal.
     *
     * @return whether the executor is a user or not
     */
    @Override
    public boolean isUser() {
        return this.sender instanceof Player;
    }

    /**
     * Get the player object of the command sender
     */
    public Player getPlayer() {
        return (Player) this.sender;
    }
}