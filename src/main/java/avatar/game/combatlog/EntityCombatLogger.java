package avatar.game.combatlog;

import avatar.Avatar;
import avatar.game.combatlog.entries.CombatEntry;
import avatar.game.combatlog.entries.EntryContainer;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class EntityCombatLogger {

    private List<EntryContainer> entries = new ArrayList<>();
    private Long lastShot, lastHit;
    private boolean inCombat = false, canReceiveDamage = true;
    private UUID uuid;

    //TODO
    public void displayEntries(){}

    public EntityCombatLogger(UUID uuid){
        this.uuid = uuid;
    }

    //Entries are only for when the entity gets hit
    public void addEntry(CombatEntry entry){
        for(EntryContainer container: entries){
            if(container.getOwner().equals(entry.getOwner())){
                container.addEntry(entry);
            }
        }
        lastHit = System.currentTimeMillis();
        setInCombat(true);
    }

    public void tickInCombat(){
        if(!isInCombat())
            return;

        double shotDifference, hitDifference;
        boolean give = true;

        //If they have shot an attack within this "session" of combat, was it over 5 seconds ago?
        if(lastShot != null) {
            shotDifference = System.currentTimeMillis() - lastShot;
            if(shotDifference > Avatar.INSTANCE.getCombatInterval()){
                give = false;
            }
        }
        //If they have been hit within this "session" of combat, was it over 5 seconds ago?
        if(lastHit != null) {
            hitDifference = System.currentTimeMillis() - lastHit;
            if(hitDifference > Avatar.INSTANCE.getCombatInterval()){
                give = false;
            } else give = true;
        }
        setInCombat(give);
    }

    public void setLastShot() {
        this.lastShot = System.currentTimeMillis();
        setInCombat(true);
    }

    public void setCanReceiveDamage(boolean canReceiveDamage) {
        this.canReceiveDamage = canReceiveDamage;
    }

    private void setInCombat(boolean inCombat) {
        this.inCombat = inCombat;
        if(this.inCombat == false) {
            entries.clear();
            lastHit = lastShot = null;
        }
    }

    public List<EntryContainer> getEntries() {
        return entries;
    }

    public Long getLastShot() {
        return lastShot;
    }

    public Long getLastHit() {
        return lastHit;
    }

    public boolean isInCombat() {
        return inCombat;
    }

    public boolean canReceiveDamage() {
        return canReceiveDamage;
    }

    public UUID getUuid() {
        return uuid;
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
