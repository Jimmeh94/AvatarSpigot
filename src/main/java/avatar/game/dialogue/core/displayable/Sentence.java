package avatar.game.dialogue.core.displayable;

import avatar.util.text.Messager;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Sentence implements Displayable {
    /*
     * This is text presented by the server/npc to a player
     */

    private List<String> sentences;

    public Sentence(String... texts){
        sentences = Arrays.asList(texts);
    }

    public Sentence(Sentence sentence){
        this.sentences = new ArrayList<>(sentence.getSentences());
    }

    @Override
    public void display(Player player) {
        for(String text: sentences) {
                Messager.sendMessage(player, text, Optional.<Messager.Prefix>empty());
        }
    }

    public boolean isAllDisplayed(){
        return sentences.size() == 0;
    }

    public List<String> getSentences() {
        return sentences;
    }
}
