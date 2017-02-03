package avatar.game.entity.npc.spawn;

import avatar.Avatar;
import avatar.game.entity.npc.NPCVillager;
import avatar.game.user.UserPlayer;
import avatar.util.text.Messager;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;

import java.util.Optional;

public class NPCConcernedCitizen extends NPCVillager {

    public NPCConcernedCitizen(Location location) {
        super(Villager.Profession.LIBRARIAN, "Concerned Citizen", location);
    }

    @Override
    public void onInteraction(Player player) {
        UserPlayer userPlayer = Avatar.INSTANCE.getUserManager().findUserPlayer(player).get();

        if(!userPlayer.getQuestManager().hasCompleted("test")){
            Messager.sendMessage(player, ChatColor.GRAY + "[Concerned Citizen] I heard the old man to my left needs help!", Optional.<Messager.Prefix>empty());
        }
    }
}
