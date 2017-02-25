package avatar.game.entity.npc.spawn;

import avatar.Avatar;
import avatar.game.dialogue.DialogueReference;
import avatar.game.entity.npc.NPCVillager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;

public class CZBilly extends NPCVillager {

    public CZBilly() {
        super(new Location(Bukkit.getWorlds().get(0), -754, 5, 329.9, 129.9f, 1.5f), Villager.Profession.FARMER, "Billy");
    }

    @Override
    public void onInteraction(Player player) {
        Avatar.INSTANCE.getUserManager().findUserPlayer(player).get().getDialogueManager().giveDialogue(DialogueReference.BROTHERS);
    }
}
