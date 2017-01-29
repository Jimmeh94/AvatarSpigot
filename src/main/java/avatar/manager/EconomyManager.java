package avatar.manager;

import avatar.game.user.Account;
import avatar.game.user.UserPlayer;

import java.util.Optional;

public class EconomyManager extends Manager<Account> {

    public Optional<Account> findAccount(UserPlayer userPlayer){
        for(Account account: objects){
            if(account.getOwner() == userPlayer){
                return Optional.of(account);
            }
        }
        return Optional.empty();
    }

}
