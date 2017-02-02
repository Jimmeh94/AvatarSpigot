package avatar.commands;

import avatar.Avatar;
import avatar.util.particles.ParticleUtils;
import avatar.util.particles.effectData.DisplayProfile;
import avatar.util.particles.effectData.PlayerBasedEffectData;
import avatar.util.particles.effects.*;
import org.bukkit.Particle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ParticleCommands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        if(label.equalsIgnoreCase("particle")){
            if(args.length == 1){
                String effect = args[0];
                AbstractEffect abstractEffect = null;
                PlayerBasedEffectData effectData = new PlayerBasedEffectData(player.getEyeLocation(), Avatar.INSTANCE.getUserManager().findUserPlayer(player).get(),
                        DisplayProfile.builder().amount(50).particle(Particle.FLAME).velocity(0).build());

                switch (effect){
                    case "atom": abstractEffect = new AtomEffect(effectData, ParticleUtils.Loaded.ATOM_3RR_1CR_0Y, 0L, 5L, 50);
                        break;
                    case "helix": abstractEffect = new HelixEffect(effectData, ParticleUtils.Loaded.HELIX_3T_25HS_75R_15L, 0L, 5L, 50);
                        break;
                    case "line": abstractEffect = new LineEffect(effectData, player.getLocation().clone().add(5, 1, 5));
                        break;
                    case "sphere": abstractEffect = new SphereEffect(effectData, ParticleUtils.Loaded.SPHERE_2R, 0L, 5L, 50);
                        break;
                    case "tornado": abstractEffect = new TornadoEffect(effectData, ParticleUtils.Loaded.TORNADO_7H_3R, 0L, 5L, 50);
                }
                abstractEffect.start();
            }
        }
        return true;
    }
}
