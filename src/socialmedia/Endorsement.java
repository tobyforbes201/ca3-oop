package socialmedia;

public class Endorsement extends Post {
    private String handle;
    private int id;


    public Endorsement(String handle, String message, int id) {
        super(handle, message, id);
        setIsOriginal(false);
    }
}
