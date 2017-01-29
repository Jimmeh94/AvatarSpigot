package avatar.commands;

import avatar.manager.DialogueManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChoiceCommands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(label.equalsIgnoreCase("choice")){
            Player player = (Player)sender;
            if(args.length == 0){
                return true;
            } else {
                int groupID = Integer.valueOf(args[0].split(".")[0]);
                String choiceID = args[0].split(".")[1];
                DialogueManager.callCallback(groupID, choiceID);
            }
        }

        return true;
    }
}
