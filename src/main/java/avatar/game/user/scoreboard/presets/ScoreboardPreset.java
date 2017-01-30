package avatar.game.user.scoreboard.presets;

import avatar.game.user.UserPlayer;

import java.util.ArrayList;
import java.util.List;

public class ScoreboardPreset {

    private List<String> scores = new ArrayList<>();
    private UserPlayer owner;
    private List<String> oldSnapshot = new ArrayList<>();
    /*
     * First string should be the title
     * Second string begins the information to display
     * Max of 15 entries, starting at the 2nd string
     */

    public ScoreboardPreset(UserPlayer owner){
        this.owner = owner;
    }

    public void updateScores(){} //if scores need to be updated on a timer, event, etc

    public List<String> getScores(){
        return scores;
    }

    public String getScore(int i){
        return scores.get(i);
    }

    public void setScores(List<String> strings){ //should only use when instantiating or needing to manually manipulate scores
        scores = strings;
    }

    public UserPlayer getOwner() {
        return owner;
    }

    public void takeSnapshot() {
        if(scores.isEmpty()){
            updateScores();
        }
        oldSnapshot = new ArrayList<>(scores);
    }

    public List<String> getOldSnapshot() {
        return oldSnapshot;
    }
}
