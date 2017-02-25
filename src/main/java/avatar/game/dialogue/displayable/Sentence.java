package avatar.game.dialogue.displayable;

import avatar.util.text.Messager;
import org.bukkit.ChatColor;
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
            Messager.sendMessage(player, ChatColor.GRAY + text, Optional.<Messager.Prefix>empty());
            Messager.sendBlankLine(player);
        }
    }

    public boolean isAllDisplayed(){
        return sentences.size() == 0;
    }

    public List<String> getSentences() {
        return sentences;
    }
}
