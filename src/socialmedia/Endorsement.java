package socialmedia;

public class Endorsement extends Post {
    private String handle;
    private int id;
    private int parentId;


    public Endorsement(String handle, String message, int id, int parentId) {
        super(handle, message, id);

        this.parentId = parentId;
    }
}