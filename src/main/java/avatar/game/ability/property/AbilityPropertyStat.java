package avatar.game.ability.property;

import avatar.game.ability.AbilityStage;
import avatar.game.ability.type.Ability;
import avatar.game.user.stats.Stats;

public abstract class AbilityPropertyStat extends AbilityProperty {

    protected Stats.StatType type;

    public AbilityPropertyStat(String displayName, Ability ability, Stats.StatType type, AbilityStage stage) {
        super(displayName, ability, stage);

        this.type = type;
    }

    //*** Adjust ***
    public static class Adjust extends AbilityPropertyStat {

        private double adjust;

        public Adjust(String displayName, Ability ability, Stats.StatType type, double amount, AbilityStage stage) {
            super(displayName, ability, type, stage);

            this.adjust = amount;
        }

        @Override
        public boolean validate() {
            if(ability.getOwner().getStats().getStat(type).isPresent())
                ability.getOwner().getStats().getStat(type).get().alter(adjust);
            return true;
        }

        @Override
        public String getFailMessage() {
            return null;
        }
    }

    //*** Restore ***
    public static class Restore extends AbilityPropertyStat {

        public Restore(String displayName, Ability ability, Stats.StatType type, AbilityStage stage) {
            super(displayName, ability, type, stage);
        }

        @Override
        public boolean validate() {
            if(ability.getOwner().getStats().getStat(type).isPresent())
                ability.getOwner().getStats().getStat(type).get().restoreMemory();
            return true;
        }

        @Override
        public String getFailMessage() {
            return null;
        }
    }
}
