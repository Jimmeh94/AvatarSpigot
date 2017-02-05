package avatar.game.entity.npc.spawn;

import avatar.Avatar;
import avatar.game.dialogue.DialogueReference;
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

public class NPCOldMan extends NPCVillager {

    private Qualifier during, after;

    public NPCOldMan() {
        super(Villager.Profession.FARMER, "Old Man", new Location(Bukkit.getWorlds().get(0), -858.5, 5, 350.5, -135f, 0f));

        during = new Qualifier(Qualifier.What.QUEST, Qualifier.When.DURING, DemoQuest.reference.getID());
        after = new Qualifier(Qualifier.What.QUEST, Qualifier.When.AFTER, DemoQuest.reference.getID());
    }

    @Override
    public void onInteraction(Player player) {
        UserPlayer userPlayer = Avatar.INSTANCE.getUserManager().findUserPlayer(player).get();

        if(userPlayer.getQuestManager().canTakeQuest(DemoQuest.reference)){
            userPlayer.getDialogueManager().giveDialogue(DialogueReference.DEMO);
            //userPlayer.getQuestManager().add(DemoQuest.reference.getQuest(userPlayer));
        } else if(during.valid(userPlayer)){
            Messager.sendMessage(player, ChatColor.GRAY + "[Old Man] Have you found them yet?", Optional.<Messager.Prefix>empty());
        } else if(after.valid(userPlayer)){
            Messager.sendMessage(player, ChatColor.GRAY + "[Old Man] I can see clearly now the rain is goooooone!", Optional.<Messager.Prefix>empty());
        }
    }
}
