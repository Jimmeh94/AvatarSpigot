package avatar.game.quest.quests.test;

import avatar.Avatar;
import avatar.game.area.Area;
import avatar.game.area.AreaReferences;
import avatar.game.entity.npc.NPC;
import avatar.game.entity.npc.spawn.CZBilly;
import avatar.game.entity.npc.spawn.CZBobby;
import avatar.game.quest.*;
import avatar.game.quest.builder.CheckpointBuilder;
import avatar.game.quest.condition.*;
import avatar.game.user.UserPlayer;
import avatar.util.misc.Items;
import avatar.util.text.Messager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class DemoQuest extends Quest{

    public static QuestReference reference = QuestReference.DEMO;

    public DemoQuest(UserPlayer userPlayer) {
        super("Helping Your Elders", "Help the old man find his glasses", 1, reference, Material.PAPER, new TestReward(), userPlayer);
    }

    @Override
    public void loadCheckpoints(){
        CheckpointBuilder checkpointBuilder = Quest.getCheckpointBuilder(owner);
        Area test2 = Avatar.INSTANCE.getAreaManager().getAreaByReference(AreaReferences.SOUTHERN_BRIDGES).get();

        checkpointBuilder.description("Investigate the southern bridges")
                .targetLocation(Optional.<Location>empty())
                .condition(new ReachArea(owner.getPlayer(), test2))
                .buildCheckpoint();

        checkpointBuilder.description("Look for a clue")
                .targetLocation(Optional.<Location>empty())
                .condition(new ItemInteract(owner.getPlayer(), Items.SCROLL, new Location(Bukkit.getWorlds().get(0), -814, 6, 367), new Items.ItemCallback() {
                    @Override
                    public void handle(UserPlayer userPlayer) {
                        Messager.sendMessage(userPlayer.getPlayer(), Items.getScrollPrefixMessage(), Optional.<Messager.Prefix>empty());
                        Messager.sendMessage(userPlayer.getPlayer(), ChatColor.GRAY.toString() + ChatColor.ITALIC +
                                "Brother, we've stolen the old man's glasses! This is the last time he calls us ugly! Meet me at the garden!", Optional.<Messager.Prefix>empty());
                    }
                }))
                .condition(new BoundArea(owner.getPlayer(), test2))
                .buildCheckpoint();

        checkpointBuilder.description("Find the thieves in the garden")
                .targetLocation(Optional.<Location>empty())
                .condition(new ReachArea(owner.getPlayer(), Avatar.INSTANCE.getAreaManager().getAreaByReference(AreaReferences.GARDEN).get()))
                .completeAction(new ICheckpointCompleteAction() {
                    @Override
                    public void doAction(UserPlayer userPlayer) {
                        CZBobby bobby = new CZBobby();
                        Avatar.INSTANCE.getUserManager().find(bobby.getEntity().getUniqueId()).get()
                                .enterArea(Avatar.INSTANCE.getAreaManager().getAreaByContainedLocation(bobby.getEntity().getLocation()).get(), false);

                        CZBilly billy = new CZBilly();
                        Avatar.INSTANCE.getUserManager().find(billy.getEntity().getUniqueId()).get()
                                .enterArea(Avatar.INSTANCE.getAreaManager().getAreaByContainedLocation(billy.getEntity().getLocation()).get(), false);
                    }
                })
                .buildCheckpoint();

        checkpointBuilder.description("Negotiate with the thieves or kill them")
                .targetLocation(Optional.<Location>empty())
                .condition(new BoundArea(owner.getPlayer(), Avatar.INSTANCE.getAreaManager().getAreaByReference(AreaReferences.GARDEN).get(), new BoundArea.LeaveAreaAction() {
                    @Override
                    public void doAction(UserPlayer userPlayer, Area area) {
                        for (NPC npc : Avatar.INSTANCE.getEntityManager().findEntitiesWithin(AreaReferences.GARDEN, CZBilly.class, CZBobby.class)) {
                            npc.remove();
                        }
                    }
                }, new BoundArea.EnterAreaAction() {
                    @Override
                    public void doAction(UserPlayer userPlayer, Area area) {
                        CZBobby bobby = new CZBobby();
                        Avatar.INSTANCE.getUserManager().find(bobby.getEntity().getUniqueId()).get()
                                .enterArea(Avatar.INSTANCE.getAreaManager().getAreaByContainedLocation(bobby.getEntity().getLocation()).get(), false);

                        CZBilly billy = new CZBilly();
                        Avatar.INSTANCE.getUserManager().find(billy.getEntity().getUniqueId()).get()
                                .enterArea(Avatar.INSTANCE.getAreaManager().getAreaByContainedLocation(billy.getEntity().getLocation()).get(), false);
                    }
                }))
                .condition(new Fork(owner.getPlayer(), getFork()))
                .buildCheckpoint();

        checkpoints = checkpointBuilder.finish();
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

    private Map<Condition, List<Checkpoint>> getFork(){
        Map<Condition, List<Checkpoint>> temp = new HashMap<>();
        CheckpointBuilder checkpointBuilder = Quest.getCheckpointBuilder(owner);
        List<Checkpoint> checkpoints = new ArrayList<>();

        checkpoints.add(checkpointBuilder.description("Bring the glasses back to the old man").targetLocation(Optional.of(new Location(Bukkit.getWorlds().get(0), -858.5, 5, 350.5)))
                .condition(new ClickDialogueChoice(owner.getPlayer(), "returnGlasses.1")).buildCheckpoint().finish().get(0));
        temp.put(new ClickDialogueChoice(owner.getPlayer(), "demoBrothersInquire.2"),new ArrayList<>(checkpoints));

        return temp;
    }

}
