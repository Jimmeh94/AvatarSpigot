package avatar.game.quest.quests.test;

import avatar.Avatar;
import avatar.game.area.Area;
import avatar.game.area.AreaReferences;
import avatar.game.quest.IQuestInitiator;
import avatar.game.quest.Quest;
import avatar.game.quest.QuestReference;
import avatar.game.quest.Reward;
import avatar.game.quest.builder.CheckpointBuilder;
import avatar.game.quest.builder.QuestBuilder;
import avatar.game.quest.condition.BoundArea;
import avatar.game.quest.condition.InteractEI;
import avatar.game.quest.condition.ReachArea;
import avatar.game.user.UserPlayer;
import avatar.manager.ServerEInteractableManager;
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
                .condition(new InteractEI(ServerEInteractableManager.ServerEIReference.DEMO))
                .condition(new BoundArea(test2))
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
