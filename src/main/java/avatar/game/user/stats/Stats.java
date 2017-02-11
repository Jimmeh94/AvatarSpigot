package avatar.game.user.stats;

import avatar.game.user.User;

import java.util.List;
import java.util.Optional;

/**
 * Stats is a container for a list is Stat
 */
public class Stats {

    private List<Stat> stats;

    public Stats(IStatsPreset preset, User owner){
        stats = preset.loadStats(owner);
    }

    public boolean hasStat(StatType type){
        Optional<Stat> temp = stats.stream().filter(stat -> stat.getType().equals(type)).findFirst();
        if(temp.isPresent()){
            return true;
        }
        return false;
    }

    public Optional<Stat> getStat(final StatType type){
        return stats.stream().filter(stat -> stat.getType().equals(type)).findFirst();
    }

    public void removeStat(StatType type){
        Optional<Stat> temp = stats.stream().filter(stat -> stat.getType().equals(type)).findFirst();
        if(temp.isPresent()){
            stats.remove(temp.get());
        }
    }

    public void addStat(Stat stat){
        if(hasStat(stat.getType())){
            removeStat(stat.getType());
        }
        stats.add(stat);
    }

    public List<Stat> getStats() {
        return stats;
    }

    public void regen() {
        if(getStat(StatType.REGENERATION_HEALTH).isPresent()){
            getStat(StatType.HEALTH).get().add(getStat(StatType.REGENERATION_HEALTH).get().getCurrent());
        }

        if(getStat(StatType.REGENERATION_CHI).isPresent()){
            getStat(StatType.CHI).get().add(getStat(StatType.REGENERATION_CHI).get().getCurrent());
        }

        if(getStat(StatType.REGENERATION_STAMINA).isPresent()){
            getStat(StatType.STAMINA).get().add(getStat(StatType.REGENERATION_STAMINA).get().getCurrent());
        }
    }

    //------------------------------------------------------

    public enum StatType{
        HEALTH,
        CHI,
        STAMINA,
        ARMOR,
        SPEED,
        RESISTANCE_PHYSICAL,
        RESISTANCE_FIRE,
        RESISTANCE_EARTH,
        RESISTANCE_WATER,
        RESISTANCE_AIR,
        REGENERATION_HEALTH,
        REGENERATION_CHI,
        REGENERATION_STAMINA,
        CRITICAL_PERCENTAGE_PHYSICAL,
        CRITICAL_PERCENTAGE_FIRE,
        CRITICAL_PERCENTAGE_EARTH,
        CRITICAL_PERCENTAGE_WATER,
        CRITICAL_PERCENTAGE_AIR;

        /**
         * Returns a nice string for displaying
         * @return
         */
        @Override
        public String toString(){
            String[] parts = super.toString().split("_");
            String give = "";

            for(int i = 0; i < parts.length; i++){
                String temp = parts[i];
                temp = temp.toLowerCase();
                temp = temp.substring(0, 1).toUpperCase() + temp.substring(1);
                give += temp + " ";
            }

            return give;
        }
    }

    //-----------------------------------------------------

    /**
     * The actual stat that is tracked. Holds a current value, max value
     */
    public static class Stat {

        private StatType type;
        private double current, max;
        private StatMemory memory;
        private User owner;

        public Stat(StatType type, User owner){
            this(type, owner, 100);
        }

        public Stat(StatType type, User owner, double max){
            this(type, owner, max, max);
        }

        public Stat(StatType type, User owner, double max, double current){
            this.type = type;
            this.owner = owner;
            this.current = current;
            this.max = max;
        }

        public void alter(double newMax){
            alter(newMax, current);
        }

        public void alter(double newMax, double newCurrent){
            alter(newMax, newCurrent, null);
        }

        /**
         * Used to apply buffs, debuffs to this stat
         * @param newMax
         * @param newCurrent
         * @param completionAction
         */
        public void alter(double newMax, double newCurrent, IStatMemoryCompletion completionAction){
            memory = new StatMemory(max, completionAction);
            this.max = newMax;
            this.current = newCurrent;

            valueCheck();
        }

        private void valueCheck(){
            if(this.current > this.max){
                this.current = this.max;
            }

            if(this.current < 0){
                this.current = 0;
            }
        }

        public boolean isAltered(){
            return memory != null;
        }

        /**
         * Restores this stat back to its pre-altered state
         */
        public void restoreMemory(){
            if(memory == null)
                return;

            this.max = memory.restore();
            memory = null;

            valueCheck();
        }

        public StatType getType() {
            return type;
        }

        /**
         * Gets the currently available max value, whether it's altered or the base
         * @return max
         */
        public double getAvailableMax(){
            return max;
        }

        /**
         * Gets the base max value of this stat, unaltered
         * @return max
         */
        public double getMax() {
            if(memory != null){
                double memMax = memory.getReturnTo();
                if(memMax != max)
                    return memMax;
            }
            return max;
        }

        public double getCurrent() {
            return current;
        }

        public StatMemory getMemory() {
            return memory;
        }

        public User getOwner() {
            return owner;
        }

        public double getCurrentAvailablePercent(){
            return (current/getAvailableMax()) * 100;
        }

        public boolean canAfford(int cost) {
            return current >= cost;
        }

        public void subtract(double cost) {
            current -= cost;

            valueCheck();
        }

        public void add(double amount){
            current += amount;

            valueCheck();
        }

        public void subtract(int cost) {
            current -= cost;

            valueCheck();
        }

        public void add(int amount){
            current += amount;

            valueCheck();
        }
    }

    //--------------------------------------------------------------

    /**
     * Used to store the initial max value. This memory allows for the stat to be altered then restored
     */
    public static class StatMemory{

        private double returnTo;
        private IStatMemoryCompletion completionAction;
        private Stat owningStat;

        public StatMemory(double returnTo){this(returnTo, null);}

        public StatMemory(double returnTo, IStatMemoryCompletion completionAction){
            this.returnTo = returnTo;
            this.completionAction = completionAction;
        }

        public double getReturnTo() {
            return returnTo;
        }

        public double restore(){
            completionAction();
            return getReturnTo();
        }

        private void completionAction(){
            if(completionAction != null){
                completionAction.doAction(this);
            }
        }

        protected Stat getOwningStat(){return owningStat;}
    }

    //----------------------------------------------------------------

    /**
     * Allows for an action to happen once the stat's memory is restored
     */
    public interface IStatMemoryCompletion{
        void doAction(StatMemory memory);
    }

}
