package socialmedia;

import java.util.ArrayList;

public class Post {
    private String handle; //the name of the user, or the title of the post?
    private String message;
    private int id;
    private ArrayList<Comment> comments;
    private boolean isOriginal = false;
    private boolean isEndorsement = false;
    private boolean isComment = false;

    public Post(String handle, String message,int id)
    {
        this.handle = handle;
        this.message = message;
        this.id = id;

        setIsOriginal(true);
    }

    public boolean getIsOriginal() {
        return isOriginal;
    }

    public void setIsOriginal(boolean isOriginal) {
        this.isOriginal = isOriginal;
    }

    public void setIsComment(boolean comment) {
        isComment = comment;
    }

    public boolean getIsComment() {
        return isComment;
    }

    public void setIsEndorsement(boolean endorsement) {
        isEndorsement = endorsement;
    }

    public boolean getIsEndorsement() {
        return isEndorsement;
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
