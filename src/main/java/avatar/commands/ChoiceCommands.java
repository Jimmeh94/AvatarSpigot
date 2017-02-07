package avatar.commands;

import avatar.Avatar;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChoiceCommands implements CommandExecutor {

    //choice 0.test.optionName

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(label.equalsIgnoreCase("choice")){
            if(args.length == 0){
                return true;
            } else {
                Avatar.INSTANCE.getUserManager().findUserPlayer((Player)sender).get().getDialogueManager().handleChoice(args[0]);
            }
        }

        return true;
    }
}
