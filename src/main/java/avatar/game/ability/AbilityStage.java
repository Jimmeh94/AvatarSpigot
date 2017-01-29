package avatar.game.ability;

public enum AbilityStage {

    REQUIREMENT_CHECK,
    PRE_FIRE,
    FIRING,
    POST_FIRE,
    UPDATE,
    COLLISION_ABILITY,
    COLLISION_USER,
    FINISH,
    MANUAL,
    HIT;

    public static AbilityStage[] firingSequence(){
        return new AbilityStage[]{REQUIREMENT_CHECK, PRE_FIRE, FIRING, POST_FIRE};
    }

}
