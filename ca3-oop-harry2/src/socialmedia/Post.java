package socialmedia;

import java.util.ArrayList;

public class Post {
    private String handle; //the name of the user
    private String message;
    private int id;
    private ArrayList<Integer> children;

    public Post(String handle, String message,int id)
    {
        this.handle = handle;
        this.message = message;
        this.id = id;

    }


    public int getId() {
        return id;
    }

    public String getHandle() {
        return handle;
    }

    public String getMessage() {
        return message;
    }

    public void addChild(int child) {
        children.add(child);
    }

    public void removeChild(int child) {
        children.remove(child);
    }
}
