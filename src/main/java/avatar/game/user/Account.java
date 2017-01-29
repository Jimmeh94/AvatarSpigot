package avatar.game.user;

import avatar.util.misc.ResultType;
import avatar.util.text.AltCodes;
import org.bukkit.ChatColor;

public class Account {

    private UserPlayer owner;
    private int balance;

    public Account(UserPlayer owner){
        this(owner, 0);
    }

    public Account(UserPlayer owner, int balance){
        this.owner = owner;
        this.balance = balance;
    }

    public boolean canAfford(int balance){
        return this.balance >= balance;
    }

    public ResultType withdraw(int balance){
        if(canAfford(balance)){
            this.balance -= balance;
            return ResultType.SUCCESS;
        } else {
            return ResultType.FAIL;
        }
    }

    public void deposit(int balance){this.balance += balance;}

    public ResultType transfer(int balance, Account to){
        if(withdraw(balance) == ResultType.SUCCESS){
            to.deposit(balance);
            return ResultType.SUCCESS;
        } else return ResultType.FAIL;
    }

    public int getBalance() {
        return balance;
    }

    public UserPlayer getOwner() {
        return owner;
    }

    public String getDisplay(){
        return ChatColor.GOLD + String.valueOf(balance) + " " + AltCodes.FILLED_CIRCLE.getSign();
    }
}
