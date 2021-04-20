package socialmedia;

public class Comment extends Post {

    private int id; // the id of the post being commented on
    private String handle; //user handle
    private String message;
    private int parentId;


    public Comment(String handle, String message, int id, int parentId) {
        super(handle, message, id);

        this.parentId = parentId;
    }
}