package avatar.commands;

import avatar.manager.DialogueManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ChoiceCommands implements CommandExecutor {

    //choice 0.test.optionName

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(label.equalsIgnoreCase("choice")){
            if(args.length == 0){
                return true;
            } else {
                int groupID = Integer.valueOf(args[0].split("\\.")[0]);
                String choiceID = args[0].split("\\.")[1] + "." + args[0].split("\\.")[2];
                DialogueManager.callCallback(groupID, choiceID);
            }
        }

        return true;
    }
}
