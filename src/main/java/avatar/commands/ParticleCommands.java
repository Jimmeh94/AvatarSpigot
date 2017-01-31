package avatar.commands;

import avatar.Avatar;
import avatar.util.particles.ParticleUtils;
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
                EffectData effectData = EffectData.builder()
                        .user(Avatar.INSTANCE.getUserManager().findUserPlayer(player).get())
                        .center(player.getLocation().clone().add(0, 1, 0))
                        .taskInfo(0L, 8L, 200)
                        .amount(5)
                        .offsets(0, 0, 0)
                        .particle(Particle.FLAME)
                        .playParticles((data, target) -> ParticleUtils.PlayerBased.displayParticles(data))
                        .randomizeOffsets(false)
                        .build();

                switch (effect){
                    case "atom": abstractEffect = new AtomEffect(effectData, 1, 0.5, 0.0);
                        break;
                    case "helix": abstractEffect = new HelixEffect(effectData, 7.5, .25, 0.75, 15);
                        break;
                    case "line": abstractEffect = new LineEffect(effectData, player.getLocation().clone().add(5, 1, 5));
                        break;
                    case "sphere": abstractEffect = new SphereEffect(effectData, 5.0);
                        break;
                    case "tornado": abstractEffect = new TornadoEffect(effectData, 10, 0.5, 6.5, 50);
                        break;
                    case "test": abstractEffect = new TestEffect(effectData);
                }
                abstractEffect.start();
            }
        }
        return true;
    }
}
