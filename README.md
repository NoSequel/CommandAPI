# Command API
An opensource command API for the spigot API

# Usage
### Making a new command implementation:

```java
import io.github.nosequel.command.annotation.Command;
import io.github.nosequel.command.annotation.Param;
import io.github.nosequel.command.annotation.Subcommand;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class ExampleCommand {

    @Command(label = "example")
    public void example(Player player, @Param(name = "text", value = "hello") String text, @Param(name = "description") String description) {
        player.sendMessage(ChatColor.RED + text);
        player.sendMessage(ChatColor.YELLOW + description);
    }

    @Subcommand(parentLabel = "sheep", label = "example")
    public void sheep(Player player, String entityType) {
        final Location location = player.getLocation();
        final EntityType type = EntityType.valueOf(entityType);
        
        location.getWorld().spawnEntity(location, type);
    }

}
```

### Registering the command you've just created:

```java
import io.github.nosequel.command.CommandHandler;
import org.bukkit.plugin.java.JavaPlugin;

public class ExamplePlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        new CommandHandler("commands").registerCommand(new ExampleCommand());
    }
}
```