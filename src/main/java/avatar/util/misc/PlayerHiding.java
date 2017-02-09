package avatar.util.misc;

import avatar.Avatar;
import avatar.game.area.Instance;
import avatar.game.user.User;
import avatar.game.user.UserPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PlayerHiding {

    public static void instance(Instance instance){
        //make all players and entities outside of instance hidden to instanced players
        //make all instanced players hidden to non-instanced players

        for(User user: instance.getMembers()){
            if(user.isPlayer()){
                UserPlayer userPlayer = (UserPlayer)user;

                for(Player player: Bukkit.getOnlinePlayers()){
                    if(player == userPlayer.getPlayer())
                        continue;

                    if(instance.hasUser(Avatar.INSTANCE.getUserManager().findUserPlayer(player).get())){
                        player.showPlayer(userPlayer.getPlayer());
                        userPlayer.getPlayer().showPlayer(player);
                    } else {
                        player.hidePlayer(userPlayer.getPlayer());
                        userPlayer.getPlayer().hidePlayer(player);
                    }
                }
            }
        }
    }

}
