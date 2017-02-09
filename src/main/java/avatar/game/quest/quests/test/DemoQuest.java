package avatar.game.quest.quests.test;

import avatar.Avatar;
import avatar.game.area.Area;
import avatar.game.area.AreaReferences;
import avatar.game.area.instances.SpawnGardenBrothersInstance;
import avatar.game.quest.*;
import avatar.game.quest.builder.CheckpointBuilder;
import avatar.game.quest.builder.QuestBuilder;
import avatar.game.quest.condition.BoundArea;
import avatar.game.quest.condition.ClickDialogueChoice;
import avatar.game.quest.condition.ItemInteract;
import avatar.game.quest.condition.ReachArea;
import avatar.game.user.UserPlayer;
import avatar.util.misc.Items;
import avatar.util.text.Messager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

public class DemoQuest implements IQuestInitiator{

    public static QuestReference reference = QuestReference.DEMO;

    @Override
    public Quest getQuest(UserPlayer userPlayer){

        QuestBuilder questBuilder = Avatar.INSTANCE.getQuestBuilder();
        CheckpointBuilder checkpointBuilder = questBuilder.getCheckpointBuilder();
        Area test2 = Avatar.INSTANCE.getAreaManager().getAreaByReference(AreaReferences.SOUTHERN_BRIDGES).get();

        checkpointBuilder.description("Investigate the southern bridges")
                .targetLocation(Optional.<Location>empty())
                .condition(new ReachArea(test2))
                .buildCheckpoint();

        checkpointBuilder.description("Look for a clue")
                .targetLocation(Optional.<Location>empty())
                .condition(new ItemInteract(Items.SCROLL, new Location(Bukkit.getWorlds().get(0), -814, 6, 367), new Items.ItemCallback() {
                    @Override
                    public void handle(UserPlayer userPlayer) {
                        Messager.sendMessage(userPlayer.getPlayer(), Items.getScrollPrefixMessage(), Optional.<Messager.Prefix>empty());
                        Messager.sendMessage(userPlayer.getPlayer(), ChatColor.GRAY.toString() + ChatColor.ITALIC +
                                "Brother, we've stolen the old man's glasses! This is the last time he calls us ugly! Meet me at the garden!", Optional.<Messager.Prefix>empty());
                    }
                }))
                .condition(new BoundArea(test2))
                .buildCheckpoint();

        checkpointBuilder.description("Find the thieves in the garden")
                .targetLocation(Optional.<Location>empty())
                .condition(new ReachArea(Avatar.INSTANCE.getAreaManager().getAreaByReference(AreaReferences.GARDEN).get()))
                .completeAction(new ICheckpointCompleteAction() {
                    @Override
                    public void doAction(UserPlayer userPlayer) {
                        Area area = Avatar.INSTANCE.getAreaManager().getAreaByReference(AreaReferences.GARDEN).get();
                        area.addInstance(new SpawnGardenBrothersInstance(area), userPlayer);
                    }
                })
                .buildCheckpoint();

        checkpointBuilder.description("Negotiate with the thieves or kill them")
                .targetLocation(Optional.<Location>empty())
                .condition(new BoundArea(Avatar.INSTANCE.getAreaManager().getAreaByReference(AreaReferences.GARDEN).get(), new BoundArea.LeaveAreaAction() {
                    @Override
                    public void doAction(UserPlayer userPlayer, Area area) {
                        if(area.isInstanced(userPlayer)){
                            area.getInstance(userPlayer).get().removeUser(userPlayer);
                        }
                    }
                }))
                .condition(new ClickDialogueChoice(""))
                .buildCheckpoint();

        Quest quest = questBuilder.name("Helping Your Elders").description("Help the old man find his glasses").level(1).setReference(reference).checkpoints()
                .itemType(Material.PAPER).reward(new TestReward()).owner(userPlayer).build();
        questBuilder.reset();
        return quest;
    }

    private static class TestReward implements Reward {
        @Override
        public String getDescription() {
            return "Hammer of Doom";
        }

        @Override
        public void giveReward(Player player) {
            player.getInventory().addItem(new ItemStack(Material.ACACIA_DOOR));
        }
    }

}
