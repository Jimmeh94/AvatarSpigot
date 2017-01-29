package avatar.game.dialogue.core;


import org.bukkit.entity.Player;

public interface IDialogueInitiator {

    static String getIDPrefix(DialogueBuilder builder){
        return builder.getStringID() + ".";
    }

    Dialogue build(Player player);

}
