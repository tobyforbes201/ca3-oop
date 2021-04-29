import socialmedia.*;

import java.util.ArrayList;

public class SimpleTest {
    public static void main(String[] args) throws IllegalHandleException, InvalidHandleException, NotActionablePostException, InvalidPostException, HandleNotRecognisedException, PostIDNotRecognisedException {

        SocialMediaPlatform platform = new SocialMedia();

        System.out.println(platform.createAccount("Toby"));
        System.out.println(platform.createAccount("Toby2"));
        int postid = platform.createPost("Toby", "OG Post");
        System.out.println(postid);
        int commentid = platform.commentPost("Toby2", postid, "wtf");
        System.out.println(commentid);
        platform.deletePost(commentid);
        System.out.println(platform.commentPost("Toby2", commentid, "part2"));
        System.out.println(platform.commentPost("Toby2", postid, "wtf2"));
        System.out.println(platform.showPostChildrenDetails(postid));
    }
}
