package avatar.game.entity.hologram.menu;

import avatar.Avatar;
import avatar.game.entity.hologram.ClientHologram;
import avatar.game.entity.hologram.Hologram;
import avatar.game.entity.hologram.HologramMenu;
import avatar.game.user.UserPlayer;
import avatar.util.particles.ParticleUtils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ParticleMenu extends HologramMenu {

    public ParticleMenu(UserPlayer owner, double boundingRadius) {
        super(owner, boundingRadius);
    }

    @Override
    protected void spawnMenu() {
        List<String> names = new ArrayList<>();
        List<Location> locations = getMenuLocations(5);

        names.add(ChatColor.GRAY + ChatColor.BOLD.toString() + "LOW");
        names.add(ChatColor.GRAY + ChatColor.ITALIC.toString() + "Shows 25%");
        names.add(ChatColor.GREEN + "Left-click");
        holograms.add(new ClientHologram(locations.get(0), Arrays.asList(owner.getPlayer()), names, new Hologram.HologramInteraction() {
            @Override
            public void onInteract(Player player, Hologram hologram) {
                Avatar.INSTANCE.getUserManager().findUserPlayer(player).get().getSettings().setParticleModifier(ParticleUtils.ParticleModifier.LOW);
            }
        }));

        names.clear();
        names.add(ChatColor.WHITE + ChatColor.BOLD.toString() + "MEDIUM");
        names.add(ChatColor.GRAY + ChatColor.ITALIC.toString() + "Shows 50%");
        names.add(ChatColor.GREEN + "Left-click");
        holograms.add(new ClientHologram(locations.get(1), Arrays.asList(owner.getPlayer()), names, new Hologram.HologramInteraction() {
            @Override
            public void onInteract(Player player, Hologram hologram) {
                Avatar.INSTANCE.getUserManager().findUserPlayer(player).get().getSettings().setParticleModifier(ParticleUtils.ParticleModifier.MEDIUM);
            }
        }));

        names.clear();
        names.add(ChatColor.YELLOW + ChatColor.BOLD.toString() + "NORMAL");
        names.add(ChatColor.GRAY + ChatColor.ITALIC.toString() + "Shows 100%");
        names.add(ChatColor.GREEN + "Left-click");
        holograms.add(new ClientHologram(locations.get(2), Arrays.asList(owner.getPlayer()), names, new Hologram.HologramInteraction() {
            @Override
            public void onInteract(Player player, Hologram hologram) {
                Avatar.INSTANCE.getUserManager().findUserPlayer(player).get().getSettings().setParticleModifier(ParticleUtils.ParticleModifier.NORMAL);
            }
        }));

        names.clear();
        names.add(ChatColor.GOLD + ChatColor.BOLD.toString() + "HIGH");
        names.add(ChatColor.GRAY + ChatColor.ITALIC.toString() + "Shows 125%");
        names.add(ChatColor.GREEN + "Left-click");
        holograms.add(new ClientHologram(locations.get(3), Arrays.asList(owner.getPlayer()), names, new Hologram.HologramInteraction() {
            @Override
            public void onInteract(Player player, Hologram hologram) {
                Avatar.INSTANCE.getUserManager().findUserPlayer(player).get().getSettings().setParticleModifier(ParticleUtils.ParticleModifier.HIGH);
            }
        }));

        names.clear();
        names.add(ChatColor.RED + ChatColor.BOLD.toString() + "EXTREME");
        names.add(ChatColor.GRAY + ChatColor.ITALIC.toString() + "Shows 150%");
        names.add(ChatColor.GREEN + "Left-click");
        holograms.add(new ClientHologram(locations.get(4), Arrays.asList(owner.getPlayer()), names, new Hologram.HologramInteraction() {
            @Override
            public void onInteract(Player player, Hologram hologram) {
                Avatar.INSTANCE.getUserManager().findUserPlayer(player).get().getSettings().setParticleModifier(ParticleUtils.ParticleModifier.EXTREME);
            }
        }));

        for(ClientHologram hologram: holograms){
            hologram.spawn();
        }
    }
}
