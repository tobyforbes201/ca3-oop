package socialmedia;

public class Comment extends Post {

    private int parentID; // the id of the post being commented on

    public Comment(String handle, String message, int id, int parentID) {
        super(handle, message, id);
        this.parentID = parentID;
    }
}
