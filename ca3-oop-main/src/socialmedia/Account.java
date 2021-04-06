package socialmedia;

import java.util.ArrayList;

public class Account {

    private String handle; //username
    private String description;
    private String password;
    private ArrayList<Account> friends;
    private int id;

    public Account(String handle, String description)
    {
        this.handle = handle;
        this.description = description;
    }

    public void addFriend(Account account)
    {
        //adds an account to this friend list and adds this account to the other's
        friends.add(account);
        account.addFriend(this);
    }
    public void removeFriend(Account account)
    {
        //removes an account from this friend list and removes this account from the other's
        friends.remove(account);
        account.removeFriend(this);
    }
}
