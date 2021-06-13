# Command API
An opensource command API for the spigot API

# Usage
### Making a new command implementation:

```java
import io.github.nosequel.Command;
import io.github.nosequel.Param;
import io.github.nosequel.Subcommand;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class ExampleCommand {

    @Command(label = "example")
    public void example(BukkitCommandExecutor player, @Param(name = "text", value = "hello") String text, @Param(name = "description") String description) {
        player.sendMessage(ChatColor.RED + text);
        player.sendMessage(ChatColor.YELLOW + description);
    }

    @Subcommand(parentLabel = "example", label = "sheep")
    public void sheep(BukkitCommandExecutor player, String entityType) {
        final Location location = player.getPlayer().getLocation();
        final EntityType type = EntityType.valueOf(entityType);
        
        location.getWorld().spawnEntity(location, type);
    }

}
```

### Making a new type adapter implementation:

```java
import java.util.UUID;

public class UUIDTypeAdapter implements TypeAdapter<UUID> {

    /**
     * Convert a {@link String} to the {@link Double} object type
     *
     * @param executor the executor of the command
     * @param source   the source to convert
     * @return the converted object
     */
    @Override
    public UUID convert(CommandExecutor executor, String source) throws Exception {
        return UUID.fromString(source);
    }
    
}
```

### Registering the command you've just created:

```java
import com.sun.corba.se.impl.activation.CommandHandler;
import io.github.nosequel.CommandHandler;
import org.bukkit.plugin.java.JavaPlugin;

public class ExamplePlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        final CommandHandler commandHandler = new BukkitCommandHandler("commands");

        commandHandler.registerTypeAdapter(UUID.class, new UUIDTypeAdapter());
        commandHandler.registerCommand(new ExampleCommand());
    }
}
```