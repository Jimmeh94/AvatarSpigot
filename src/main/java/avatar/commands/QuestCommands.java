package avatar.commands;

import avatar.Avatar;
import avatar.game.quest.QuestReference;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Optional;

public class QuestCommands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        Avatar a = Avatar.INSTANCE;
        if(label.equalsIgnoreCase("quest")){
            if(args.length == 2){
                if(args[0].equalsIgnoreCase("start")){
                    Optional<QuestReference> reference = QuestReference.getReference(args[1]);
                    if(reference.isPresent()){
                        a.getUserManager().findUserPlayer(player).get().getQuestManager().add(reference.get());
                        a.getUserManager().findUserPlayer(player).get().getQuestManager().setActiveQuest(reference.get());
                    }
                }
            } else if(args[0].equalsIgnoreCase("menu")){
                a.getUserManager().findUserPlayer(player).get().getQuestManager().displayQuestMenu();
            }
        }

        return true;
    }
}
