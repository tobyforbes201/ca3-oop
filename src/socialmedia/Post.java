package socialmedia;

import java.util.ArrayList;

public class Post {
    private String handle; //the name of the user
    private String message;
    private int id;
    private ArrayList<Integer> children = new ArrayList<>();
    private boolean deleted;

    public Post(String handle, String message,int id)
    {
        this.handle = handle;
        this.message = message;
        this.id = id;
        this.deleted = false;

    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public boolean getDeleted() {
        return this.deleted;
    }

    public int getId() {
        return id;
    }

    public String getHandle() {
        return handle;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<Integer> getChildren() {
        return children;
    }

    public void addChild(int child) {
        children.add(child);
    }

    public void removeChild(int child) {
        children.remove(child);
    }
}