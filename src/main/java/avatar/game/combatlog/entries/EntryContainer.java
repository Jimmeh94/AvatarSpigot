package avatar.game.combatlog.entries;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class EntryContainer {

    /*
     * Contains all combat entries for this entity. This is the attacker, not victim
     */

    private UUID owner;
    private List<CombatEntry> entries = new ArrayList<>();

    public EntryContainer(UUID owner){this.owner = owner;}

    public void addEntry(CombatEntry entry){
        entries.add(entry);
    }

    public List<CombatEntry> getEntries() {
        return entries;
    }

    public UUID getOwner() {
        return owner;
    }
}
