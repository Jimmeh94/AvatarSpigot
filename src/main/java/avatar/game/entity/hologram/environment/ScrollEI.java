package avatar.game.entity.hologram.environment;

import avatar.game.user.UserPlayer;
import avatar.manager.ServerEInteractableManager;
import org.bukkit.Material;

public class ScrollEI extends EnvironmentInteractable {

    public ScrollEI(ServerEInteractableManager.ServerEIReference reference) {
        super(reference, Material.MELON);
    }

    @Override
    public void handle(UserPlayer userPlayer) {
        System.out.println("inside");
    }
}
