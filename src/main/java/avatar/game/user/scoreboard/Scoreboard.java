package avatar.game.user.scoreboard;


import avatar.game.user.UserPlayer;
import avatar.game.user.scoreboard.presets.DefaultPreset;
import avatar.game.user.scoreboard.presets.ScoreboardPreset;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;

public class Scoreboard {

    private UserPlayer owner;
    private ScoreboardPreset preset;

    public Scoreboard(UserPlayer player){
        owner = player;

        org.bukkit.scoreboard.Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        scoreboard.registerNewObjective("side", "dummy").setDisplaySlot(DisplaySlot.SIDEBAR);

        //scoreboard.registerNewObjective("prefix", "dummy").setDisplaySlots(DisplaySlots.PLAYER_LIST);
        //May have to loop through every online player and add this team to their scoreboard

        //scoreboard.registerNewTeam("Red").setDisplayName(ChatColor.RED + "Red");
        //scoreboard.registerNewTeam("Blue").setDisplayName(ChatColor.DARK_AQUA + "Blue");
        owner.getPlayer().setScoreboard(scoreboard);
        //setPrefix();

        preset = new DefaultPreset(owner);
        //updateScoreboard();
    }

   /* public void setPrefix(){
        updateTeams();
    }*/

    private void updatePreset(){ //use this before updating scoreboard
        preset.takeSnapshot();
        preset.updateScores();
    }

    public void setPreset(ScoreboardPreset preset){
        if(preset.getClass() == this.preset.getClass())
            return;
        this.preset.takeSnapshot();

        Objective objective = owner.getPlayer().getScoreboard().getObjective(DisplaySlot.SIDEBAR);
        objective.setDisplayName(preset.getScore(0));

        for(int i = 0; i < this.preset.getOldSnapshot().size(); i++) {
            objective.getScoreboard().resetScores(this.preset.getOldSnapshot().get(i));
        }

        this.preset = preset;
        updateScoreboard();
    }

    public void unregisterScoreboard(){
        Player owner = this.owner.getPlayer();
        owner.setScoreboard(null);
    }

    public void updateScoreboard(){//sidebar scoreboard
        updatePreset();
        Objective objective = owner.getPlayer().getScoreboard().getObjective(DisplaySlot.SIDEBAR);
        objective.setDisplayName(preset.getScore(0));

        //we are to assume that the lines of the snapshot match the lines of the current scores
        //starting at 1 because 0 is the title
        for(int i = 1; i < preset.getOldSnapshot().size(); i++){
            String old = preset.getOldSnapshot().get(i);
            String current = preset.getScore(i);
            //For when setting up the scoreboard, if the line is blank or doesn't exist, add it
            if(objective.getScore(old) == null && objective.getScore(current) == null){
                objective.getScore(preset.getScore(i)).setScore(16 - i);
            }
            if(!preset.getOldSnapshot().get(i).equals(preset.getScore(i))){
                objective.getScoreboard().resetScores(this.preset.getOldSnapshot().get(i));
                objective.getScore(preset.getScore(i)).setScore(16 - i);
            }
        }
    }

    /*private void updateTeams() {
        GameArena arena = Core.getInstance().getGameArenaManager().getArena(owner.getPlayer().getUniqueId());
        for(PlayerInfo playerInfo: arena.getMembers()){
            org.bukkit.scoreboard.Scoreboard scoreboard = playerInfo.getPlayer().getScoreboard();
            for(AdvancedEntity playerInfo1: arena.getRedTeam().getMembers()){
                if(playerInfo1 instanceof PlayerInfo)
                    scoreboard.getTeam("Red").addEntry(playerInfo1.getPlayer().getName());
            }
            for(AdvancedEntity playerInfo1: arena.getBlueTeam().getMembers()){
                if(playerInfo1 instanceof PlayerInfo)
                    scoreboard.getTeam("Blue").addEntry(playerInfo1.getPlayer().getName());
            }
            playerInfo.getPlayer().setScoreboard(scoreboard);
        }
    }*/

    public ScoreboardPreset getPreset() {
        return preset;
    }
}
