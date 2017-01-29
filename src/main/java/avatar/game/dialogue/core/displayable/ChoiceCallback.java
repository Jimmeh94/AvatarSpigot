package avatar.game.dialogue.core.displayable;

public abstract class ChoiceCallback{

    private int groupID;
    private String choiceID;

    public ChoiceCallback(int id, String choiceID){
        this.groupID = id;
        this.choiceID = choiceID;
    }

    /**
     * Return true if all conditions were validated and the actions ran
     * @return
     */
    public abstract boolean handle();

    public int getGroupID(){return groupID;}

    public String getChoiceID() {
        return choiceID;
    }
}
