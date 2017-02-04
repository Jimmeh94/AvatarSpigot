package avatar.commands;

import avatar.Avatar;
import avatar.game.quest.QuestReference;
import avatar.game.user.UserPlayer;
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
        UserPlayer userPlayer = a.getUserManager().findUserPlayer(player).get();
        if(label.equalsIgnoreCase("quest")){
            if(args.length == 2){
                if(args[0].equalsIgnoreCase("start")){
                    Optional<QuestReference> reference = QuestReference.getReference(args[1]);
                    if(reference.isPresent()){
                        userPlayer.getQuestManager().add(reference.get().getQuest(userPlayer));
                        userPlayer.getQuestManager().setActiveQuest(reference.get());
                    }
                }
            } else if(args[0].equalsIgnoreCase("menu")){
                userPlayer.getQuestManager().displayQuestMenu();
            }
        }

        return true;
    }
}
