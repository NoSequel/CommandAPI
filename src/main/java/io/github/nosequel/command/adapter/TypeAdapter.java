package io.github.nosequel.command.adapter;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public interface TypeAdapter<T> {

    /**
     * Convert a {@link String} to the {@link T} object type
     *
     * @param executor the executor of the command
     * @param source   the source to convert
     * @return the converted object
     */
    T convert(CommandSender executor, String source) throws Exception;

    /**
     * Handle a thrown exception while converting the object.
     *
     * @param executor  the sender which executed the command
     * @param source    the source which was attempted to convert
     * @param exception the exception to handle
     */
    default void handleException(CommandSender executor, String source, Exception exception) {
        executor.sendMessage(ChatColor.RED + "Could not parse from " + source + ".");
    }

}