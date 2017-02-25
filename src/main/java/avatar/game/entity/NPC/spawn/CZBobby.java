package avatar.game.entity.npc.spawn;

import avatar.Avatar;
import avatar.game.dialogue.DialogueReference;
import avatar.game.entity.npc.NPCVillager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;

public class CZBobby extends NPCVillager {

    public CZBobby() {
        super(new Location(Bukkit.getWorlds().get(0), -756.2, 5, 328.1, -45f, 0f), Villager.Profession.BLACKSMITH, "Bobby");
    }

    @Override
    public void onInteraction(Player player) {
        Avatar.INSTANCE.getUserManager().findUserPlayer(player).get().getDialogueManager().giveDialogue(DialogueReference.BROTHERS);
    }
}
