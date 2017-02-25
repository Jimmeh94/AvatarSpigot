package avatar.events;

import avatar.events.custom.AbilityEvent;
import avatar.events.custom.DialogueEvent;
import avatar.game.user.UserPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class QuestEvents implements Listener {

    @EventHandler
    public void onClick(DialogueEvent.ChoiceClicked event){
        event.getUserPlayer().getQuestManager().checkEvent(event);
    }

    @EventHandler
    public void onFire(AbilityEvent.PostFire event){
        if(event.getAbility().getOwner().isPlayer()){
            ((UserPlayer)event.getAbility().getOwner()).getQuestManager().checkEvent(event);
        }
    }

}
