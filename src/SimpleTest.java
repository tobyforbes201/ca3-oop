import socialmedia.BadSocialMedia;
import socialmedia.IllegalHandleException;
import socialmedia.InvalidHandleException;
import socialmedia.SocialMediaPlatform;

public class SimpleTest {
    public static void main(String[] args) throws IllegalHandleException, InvalidHandleException {

        SocialMediaPlatform platform = new BadSocialMedia();

        System.out.println(platform.createAccount("Toby"));
        System.out.println(platform.createAccount("Toby2"));
    }
}
