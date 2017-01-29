package avatar.game.combatlog.entries;

import java.util.UUID;

public class CombatEntry {

    //Entries are created when something is attacked,
    //These are for non-player entities

    private double damage;
    private String attacker, attack;
    private UUID owner;

    public CombatEntry(double damage, String attack, String attacker, UUID owner){
        this.damage = damage;
        this.attack = attack;
        this.attacker = attacker;
        this.owner = owner;
    }

    public double getDamage() {
        return damage;
    }

    public String getAttack() {
        return attack;
    }

    public String getAttacker() {
        return attacker;
    }

    public UUID getOwner() {
        return owner;
    }
}
