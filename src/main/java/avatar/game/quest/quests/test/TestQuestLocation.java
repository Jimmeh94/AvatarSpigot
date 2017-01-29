package avatar.game.quest.quests.test;

import avatar.Avatar;
import avatar.game.area.Area;
import avatar.game.area.AreaReferences;
import avatar.game.quest.IQuestInitiator;
import avatar.game.quest.Quest;
import avatar.game.quest.Reward;
import avatar.game.quest.builder.CheckpointBuilder;
import avatar.game.quest.builder.QuestBuilder;
import avatar.game.quest.condition.*;
import avatar.game.user.UserPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class TestQuestLocation implements IQuestInitiator{

    @Override
    public Quest createLocationTest(UserPlayer userPlayer){
        Location use = new Location(Bukkit.getWorlds().get(0), 55, 55, 55);
        Location use2 = new Location(Bukkit.getWorlds().get(0), 51, 55, 51);
        QuestBuilder questBuilder = Avatar.INSTANCE.getQuestBuilder();
        CheckpointBuilder checkpointBuilder = questBuilder.getCheckpointBuilder();

        checkpointBuilder.description("Go to a location")
                .targetLocation(use)
                .condition(new ReachLocation(use, 1.5))
                .condition(new TimeLimit(60))
                .buildCheckpoint();
        checkpointBuilder.description("Stay within quest region and reach target location")
                .targetLocation(use2)
                .condition(new BoundRadius(10.0, use))
                .condition(new ReachLocation(use2, 1.5))
                .buildCheckpoint();
        checkpointBuilder.description("Talk to Old Man")
                .targetLocation(use2)
                .condition(new ClickDialogueChoice("test.with"))
                .buildCheckpoint();

        Area test2 = Avatar.INSTANCE.getAreaManager().getAreaByReference(AreaReferences.TEST2).get();
        checkpointBuilder.description("Reach Test2")
                .targetLocation(test2.getCenter())
                .condition(new ReachArea(test2))
                .buildCheckpoint();


        Quest quest = questBuilder.name("Test").description("This is a test quest").level(1).setID("test").checkpoints()
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
        public void giveAward(Player player) {
            player.getInventory().addItem(new ItemStack(Material.ACACIA_DOOR));
        }
    }

}
