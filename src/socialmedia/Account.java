package socialmedia;

import java.util.ArrayList;

public class Account {

    private String handle; //username
    private String description;
    private String password;
    private ArrayList<Account> friends;
    private int id;
    private int endorsementNum;

    public Account(String handle, String description, int ID)
    {
        this.handle = handle;
        this.description = description;
        this.id = ID;
        this.endorsementNum = 0;
    }

    public int getEndorsementNum() {
        return endorsementNum;
    }

    public void setEndorsementNum(int endorsementNum) {
        this.endorsementNum = endorsementNum;
    }

    public void changeEndorsementNum(int change) {
        this.endorsementNum = this.endorsementNum + change;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<Account> getFriends() {
        return friends;
    }

    public int getId() {
        return id;
    }

    public String getHandle() {
        return handle;
    }

    public String getPassword() {
        return password;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    public void setDescription(String description) {
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