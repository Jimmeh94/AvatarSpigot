package avatar.game.quest;

import avatar.game.user.UserPlayer;

public interface IQuestInitiator {

    Quest getQuest(UserPlayer userPlayer);

}
