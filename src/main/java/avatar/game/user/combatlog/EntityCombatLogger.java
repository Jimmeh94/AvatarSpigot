package avatar.game.user.combatlog;

import avatar.Avatar;
import avatar.game.user.User;
import avatar.game.user.UserPlayer;
import avatar.game.user.combatlog.entries.CombatEntry;
import avatar.game.user.combatlog.entries.EntryContainer;
import avatar.game.user.scoreboard.presets.CombatPreset;
import avatar.game.user.scoreboard.presets.DefaultPreset;
import avatar.util.text.Messager;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class EntityCombatLogger {

    private List<EntryContainer> entries = new ArrayList<>();
    private Long lastShot, lastHit;
    private boolean inCombat = false, passiveMode = true;
    private User user;

    //TODO
    public void displayEntries(){}

    public EntityCombatLogger(User uuid){
        this.user = uuid;
    }

    //Entries are only for when the entity gets hit
    public void addEntry(CombatEntry entry){
        for(EntryContainer container: entries){
            if(container.getOwner().equals(entry.getOwner())){
                container.addEntry(entry);
            }
        }
        setLastHit();
    }

    public void tickInCombat(){
        if(!isInCombat())
            return;

        //If they have shot an attack within this "session" of combat, was it over 5 seconds ago?
        if(lastShot != null) {
            if((System.currentTimeMillis() - lastShot)/1000 > Avatar.INSTANCE.getCombatInterval()){
                leaveCombat();
            }
        }
        //If they have been hit within this "session" of combat, was it over 5 seconds ago?
        if(lastHit != null) {
            if((System.currentTimeMillis() - lastHit)/1000 > Avatar.INSTANCE.getCombatInterval()){
                leaveCombat();
            }
        }
    }

    public void setLastShot() {
        this.lastShot = System.currentTimeMillis();
        if(shouldEnterCombat()){
            enterCombat();
        }
    }

    private void setLastHit() {
        this.lastHit = System.currentTimeMillis();
        if(shouldEnterCombat()){
            enterCombat();
        }
    }

    private boolean shouldEnterCombat(){
        return !inCombat;
    }

    private void enterCombat(){
        inCombat = true;
        if(user.isPlayer()) {
            Messager.sendMessage(((UserPlayer) user).getPlayer(), ChatColor.GRAY + "Entered combat!", Optional.of(Messager.Prefix.INFO));
            ((UserPlayer)user).getScoreboard().setPreset(new CombatPreset(((UserPlayer)user)));
        }
    }

    private void leaveCombat(){
        this.inCombat = false;
        entries.clear();
        lastHit = lastShot = null;
        if(user.isPlayer()) {
            Messager.sendMessage(((UserPlayer) user).getPlayer(), ChatColor.GRAY + "Out of combat!", Optional.of(Messager.Prefix.INFO));
            ((UserPlayer)user).getScoreboard().setPreset(new DefaultPreset(((UserPlayer) user)));
        }
    }

    public boolean isInCombat() {
        return inCombat;
    }

    public void setPassiveMode(boolean passiveMode) {
        this.passiveMode = passiveMode;
    }

    public boolean canReceiveDamage() {
        return passiveMode;
    }

    public User getUser() {
        return user;
    }

    public boolean hasEntryContainer(UUID owner) {
        for(EntryContainer container: entries){
            if(container.getOwner().equals(owner))
                return true;
        }
        return false;
    }

    public void addEntryContainer(UUID owner) {
        entries.add(new EntryContainer(owner));
    }
}
