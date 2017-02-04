package avatar.game.entity.hologram.environment;

import avatar.manager.ServerEInteractableManager;
import org.bukkit.Material;

public class ScrollEI extends EnvironmentInteractable {

    public ScrollEI(ServerEInteractableManager.ServerEIReference reference) {
        super(reference, Material.MELON);
    }
}
