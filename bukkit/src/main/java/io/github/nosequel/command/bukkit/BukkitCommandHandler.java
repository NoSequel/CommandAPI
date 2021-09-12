package io.github.nosequel.command.bukkit;

import io.github.nosequel.command.CommandHandler;
import io.github.nosequel.command.bukkit.adapter.LocationTypeAdapter;
import io.github.nosequel.command.bukkit.adapter.PlayerTypeAdapter;
import io.github.nosequel.command.bukkit.help.DefaultBukkitHelpHandler;
import io.github.nosequel.command.data.impl.BaseCommandData;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandMap;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;

public class BukkitCommandHandler extends CommandHandler {

    private final String fallbackPrefix;
    private final CommandMap commandMap;

    public BukkitCommandHandler(String fallbackPrefix) {
        super();

        if ((this.commandMap = this.getCommandMap()) == null) {
            throw new IllegalArgumentException("Unable to find CommandMap inside of Bukkit#getPluginManager");
        }

        this.fallbackPrefix = fallbackPrefix;
        this.helpHandler = new DefaultBukkitHelpHandler();

        this.registerTypeAdapter(Player.class, new PlayerTypeAdapter());
        this.registerTypeAdapter(Location.class, new LocationTypeAdapter());
    }

    /**
     * Register the command to the {@link BukkitCommandHandler#commands} list.
     *
     * @param data the command to register
     */
    @Override
    public void register(BaseCommandData data) {
        super.register(data);
        this.commandMap.register(fallbackPrefix, new BukkitCommandInvoker(data));
    }

    /**
     * Get the {@link CommandMap} registered
     * inside of the {@link org.bukkit.plugin.SimplePluginManager} class
     *
     * @return the found command map, or null
     */
    private CommandMap getCommandMap() {
        try {
            final Field field = Bukkit.getPluginManager().getClass().getDeclaredField("commandMap");
            field.setAccessible(true);

            return (CommandMap) field.get(Bukkit.getPluginManager());
        } catch (NoSuchFieldException | IllegalAccessException exception) {
            exception.printStackTrace();
        }

        return null;
    }
}
