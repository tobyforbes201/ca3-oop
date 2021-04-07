package socialmedia;

import java.util.ArrayList;

public class Post {
    private String handle; //the name of the user, or the title of the post?
    private String message;
    private int id;
    private ArrayList<Comment> comments;

    public Post(String handle, String message)
    {
        this.handle = handle;
        this.message = message;
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

    public void addComment(Comment comment) {
        comments.add(comment);
    }

    public void removeComment(Comment comment) {
        comments.remove(comment);
    }
}
