package socialmedia;

public class Comment extends Post {

    private int id; // the id of the post being commented on
    private String handle; //user handle
    private String message;


    public Comment(String handle, String message, int id) {
        super(handle, message, id);

        setIsComment(true);
    }
}
