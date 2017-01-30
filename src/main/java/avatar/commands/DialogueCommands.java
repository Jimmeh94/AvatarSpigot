package avatar.commands;

import avatar.Avatar;
import avatar.game.dialogue.core.DialogueReference;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Optional;

public class DialogueCommands implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player)sender;
        if(label.equalsIgnoreCase("dialogue")){
            if(args.length == 1){
                if(args[0].equalsIgnoreCase("test")){
                    Optional<DialogueReference> optional = DialogueReference.getReference(args[0]);
                    if(optional.isPresent())
                        Avatar.INSTANCE.getUserManager().findUserPlayer(player).get().giveDialogue(optional.get());
                }
            }
        }
        return true;
    }
}
