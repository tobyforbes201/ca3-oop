import socialmedia.*;

/**
 * A short program to illustrate an app testing some minimal functionality of a
 * concrete implementation of the SocialMediaPlatform interface -- note you will
 * want to increase these checks, and run it on your SocialMedia class (not the
 * BadSocialMedia class).
 *
 * 
 * @authors Toby Forbes and Harry Collins
 * @version 2.1
 */
public class SocialMediaPlatformTestApp {

	/**
	 * Test method.
	 * 
	 * @param args not used
	 */
	public static void main(String[] args) {
		System.out.println("The system compiled and started the execution...");

		SocialMediaPlatform platform = new SocialMedia();

		assert (platform.getNumberOfAccounts() == 0) : "Innitial SocialMediaPlatform not empty as required.";
		assert (platform.getTotalOriginalPosts() == 0) : "Innitial SocialMediaPlatform not empty as required.";
		assert (platform.getTotalCommentPosts() == 0) : "Innitial SocialMediaPlatform not empty as required.";
		assert (platform.getTotalEndorsmentPosts() == 0) : "Innitial SocialMediaPlatform not empty as required.";

		Integer id;
		try {
			id = platform.createAccount("my_handle");
			assert (platform.getNumberOfAccounts() == 1) : "number of accounts registered in the system does not match";
			platform.changeAccountHandle("my_handle", "my_newHandle");
			platform.createAccount("my_handle");
			assert (platform.getNumberOfAccounts() == 2) : "number of accounts registered in the system does not match";
			assert (platform.createPost("my_handle", "Post number 1") == 1) : "id sequencing not as " +
					"expected (system may still work)";
			assert (platform.getTotalOriginalPosts() == 1) : "number of posts in the system does not match";
			platform.removeAccount(id);
			assert (platform.getNumberOfAccounts() == 0) : "number of accounts registered in the system does not match";

		} catch (IllegalHandleException e) {
			assert (false) : "IllegalHandleException thrown incorrectly";
			e.printStackTrace();
		} catch (InvalidHandleException e) {
			assert (false) : "InvalidHandleException thrown incorrectly";
			e.printStackTrace();
		} catch (AccountIDNotRecognisedException e) {
			assert (false) : "AccountIDNotRecognizedException thrown incorrectly";
			e.printStackTrace();
		} catch (HandleNotRecognisedException e) {
			assert (false) : "HandleNotRecognisedException thrown incorrectly";
			e.printStackTrace();
		} catch (PostIDNotRecognisedException e) {
			assert (false) : "PostIDNotRecognisedException thrown incorrectly";
			e.printStackTrace();
		} catch (InvalidPostException e) {
			assert (false) : "InvalidPostException thrown incorrectly";
			e.printStackTrace();
		}
	}

}
