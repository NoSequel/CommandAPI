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

### Registering the command you've just created:

```java
import io.github.nosequel.CommandHandler;
import org.bukkit.plugin.java.JavaPlugin;

public class ExamplePlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        new CommandHandler("commands").registerCommand(new ExampleCommand());
    }
}
```