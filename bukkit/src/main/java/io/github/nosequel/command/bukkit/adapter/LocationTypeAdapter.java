package io.github.nosequel.command.bukkit.adapter;

import io.github.nosequel.command.adapter.TypeAdapter;
import io.github.nosequel.command.executor.CommandExecutor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;

public class LocationTypeAdapter implements TypeAdapter<Location> {

    /**
     * Convert a {@link String} to the {@link Location} object type
     *
     * @param executor the executor of the command
     * @param source   the source to convert
     * @return the converted object
     */
    @Override
    public Location convert(CommandExecutor executor, String source) throws Exception {
        final String[] split = source.split(",");

        if (split.length < 4) {
            throw new IllegalArgumentException("format is supposed to be \"world,x,y,z\"");
        }

        final World world = Bukkit.getWorld(split[0]);

        if (world == null) {
            throw new IllegalArgumentException("provided world does not exist");
        }

        return new Location(
                world,
                Integer.parseInt(split[1]),
                Integer.parseInt(split[2]),
                Integer.parseInt(split[3])
        );
    }

    /**
     * Handle a thrown exception while converting the object.
     *
     * @param executor  the sender which executed the command
     * @param source    the source which was attempted to convert
     * @param exception the exception to handle
     */
    @Override
    public void handleException(CommandExecutor executor, String source, Exception exception) {
        executor.sendMessage(ChatColor.RED + exception.getMessage());
    }
}