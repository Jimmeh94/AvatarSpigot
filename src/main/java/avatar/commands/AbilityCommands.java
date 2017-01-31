package avatar.commands;

import avatar.Avatar;
import avatar.game.ability.abilities.fire.Fireball;
import avatar.game.ability.type.Ability;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AbilityCommands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        Ability ability = null;

        if(label.equalsIgnoreCase("ability")){
            if(args.length == 1){
                switch (args[0]){
                    case "fireball": ability = new Fireball(Avatar.INSTANCE.getUserManager().findUserPlayer(player).get(), 1, 5L);
                }
                ability.fire();
            }
        }
        return true;
    }
}
