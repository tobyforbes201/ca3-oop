package socialmedia;

public class Comment {

    private int id; // the id of the post being commented on
    private String handle; //user handle
    private String message;

    public Comment(String handle, String message, int id)
    {
        this.handle = handle;
        this.message = message;
        this.id = id;
    }
}
