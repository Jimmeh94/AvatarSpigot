package avatar.manager;

import avatar.game.user.User;
import avatar.game.user.UserPlayer;
import org.bukkit.entity.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserManager extends Manager<User> {

    private long lastRun = System.currentTimeMillis();

    public Optional<User> find(UUID uuid){
        Optional<User> give = Optional.empty();

        for(User user: this.objects){
            if(user.getUUID() == uuid){
                give = Optional.of(user);
            }
        }

        return give;
    }

    public void tick() {
        for(User user: objects) {
            user.tick();
        }
    }

    public List<UserPlayer> getPlayers() {
        List<UserPlayer> players = new ArrayList<>();
        for(User user: objects){
            if(user instanceof UserPlayer)
                players.add(((UserPlayer)user));
        }
        return players;
    }

    public Optional<User> findUser(Entity entity){
        return find(entity.getUniqueId());
    }

    public Optional<UserPlayer> findUserPlayer(Entity entity) {
        Optional<UserPlayer> give = Optional.empty();

        for(User user: objects){
            if(user instanceof UserPlayer && user.getUUID().equals(entity.getUniqueId())){
                give = Optional.of((UserPlayer)user);
                return give;
            }
        }

        return give;
    }

    private void tickHologramMenus() {
        for(User user: objects){
            if(user.isPlayer()){
                ((UserPlayer)user).tickHologramMenu();
            }
        }
    }

    public void slowTick() {
        tickHologramMenus();
        for(User user: objects){
            user.getStats().regen();
            user.getCombatLogger().tickInCombat();
        }
    }
}
