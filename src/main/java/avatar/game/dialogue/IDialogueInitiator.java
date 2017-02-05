package avatar.game.dialogue;


import org.bukkit.entity.Player;

public interface IDialogueInitiator {

    Dialogue build(Player player);

}
