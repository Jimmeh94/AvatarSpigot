package avatar.game.entity.npc.spawn;

import avatar.Avatar;
import avatar.game.entity.npc.NPCVillager;
import avatar.game.quest.quests.test.DemoQuest;
import avatar.game.user.UserPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;

public class NPCOldMan extends NPCVillager {

    public NPCOldMan() {
        super(Villager.Profession.NITWIT, "Old Man", new Location(Bukkit.getWorlds().get(0), -858.5, 5, 350.5, -135f, 0f));
    }

    @Override
    public void onInteraction(Player player) {
        UserPlayer userPlayer = Avatar.INSTANCE.getUserManager().findUserPlayer(player).get();

        if(userPlayer.getQuestManager().canTakeQuest(DemoQuest.reference)){
            userPlayer.getQuestManager().add(DemoQuest.reference.getQuest(userPlayer));
        }
    }
}
