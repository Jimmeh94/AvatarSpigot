package avatar.game.entity.npc.spawn;

import avatar.Avatar;
import avatar.game.entity.npc.NPCVillager;
import avatar.game.quest.quests.test.DemoQuest;
import avatar.game.user.UserPlayer;
import avatar.util.misc.Qualifier;
import avatar.util.text.Messager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;

import java.util.Optional;

public class NPCConcernedCitizen extends NPCVillager {

    private Qualifier qualifier;

    public NPCConcernedCitizen() {
        this(new Location(Bukkit.getWorlds().get(0), -844.5, 5, 303.5, -90f, 0f));
    }

    public NPCConcernedCitizen(Location location) {
        super(location, Villager.Profession.LIBRARIAN, "Concerned Citizen");

        this.qualifier = new Qualifier(Qualifier.What.QUEST, Qualifier.When.BEFORE, DemoQuest.reference.getID());
    }

    @Override
    public void onInteraction(Player player) {
        UserPlayer userPlayer = Avatar.INSTANCE.getUserManager().findUserPlayer(player).get();

        if(qualifier.valid(userPlayer) && userPlayer.getQuestManager().canTakeQuest(DemoQuest.reference)){
            Messager.sendMessage(player, ChatColor.GRAY + "[Concerned Citizen] I heard the old man to my left needs help!", Optional.<Messager.Prefix>empty());
        } else {
            Messager.sendMessage(player, ChatColor.GRAY + "[Concerned Citizen] It's nice of you to have helped him!", Optional.<Messager.Prefix>empty());
        }
    }
}
