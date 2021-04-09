package socialmedia;

import java.io.IOException;
import java.util.ArrayList;

/**
 * BadSocialMedia is a minimally compiling, but non-functioning implementor of
 * the SocialMediaPlatform interface.
 * 
 * @author Diogo Pacheco
 * @version 1.0
 */
public class BadSocialMedia implements SocialMediaPlatform {

	ArrayList<Account> accounts = new ArrayList<>();
	int IDCounter = 0;

	@Override
	public int createAccount(String handle) throws IllegalHandleException, InvalidHandleException {
		if(handle == null || handle.length() > 30 || handle.contains(" ")){
			throw new InvalidHandleException();
		}
		for(Account account : accounts){
			if(account.getHandle().equals(handle)){
				throw new IllegalHandleException();
			}
		}

		Account newAccount = new Account(handle, null, IDCounter++);
		accounts.add(newAccount);
		return IDCounter;
	}

	@Override
	public int createAccount(String handle, String description) throws IllegalHandleException, InvalidHandleException {
		if(handle == null || handle.length() > 30 || handle.contains(" ")){
			throw new InvalidHandleException();
		}
		for(Account account : accounts){
			if(account.getHandle().equals(handle)){
				throw new IllegalHandleException();
			}
		}

		Account newAccount = new Account(handle, null, IDCounter++);
		accounts.add(newAccount);
		return IDCounter;
	}

	@Override
	public void removeAccount(int id) throws AccountIDNotRecognisedException {
		for(Account account : accounts){
			if(account.getId() == id){
				accounts.remove(account);
				return;
			}
		}
		throw new AccountIDNotRecognisedException();
	}

	@Override
	public void removeAccount(String handle) throws HandleNotRecognisedException {
		for(Account account : accounts){
			if(account.getHandle().equals(handle)){
				accounts.remove(account);
				return;
			}
		}
		throw new HandleNotRecognisedException();
	}

	@Override
	public void changeAccountHandle(String oldHandle, String newHandle)
			throws HandleNotRecognisedException, IllegalHandleException, InvalidHandleException {
		if(newHandle == null || newHandle.length() > 30 || newHandle.contains(" ")){
			throw new InvalidHandleException();
		}
		for(Account account : accounts){
			if(account.getHandle().equals(newHandle)){
				throw new IllegalHandleException();
			}
		}
		for(Account account : accounts){
			if(account.getHandle().equals(newHandle)){
				account.setHandle(newHandle);
				return;
			}
		}
		throw new HandleNotRecognisedException();
	}

	@Override
	public void updateAccountDescription(String handle, String description) throws HandleNotRecognisedException {
		for(Account account : accounts){
			if(account.getHandle().equals(handle)){
				account.setDescription(description);
				return;
			}
		}
		throw new HandleNotRecognisedException();
	}

	@Override
	public String showAccount(String handle) throws HandleNotRecognisedException {
		for(Account account : accounts){
			if(account.getHandle().equals(handle)){
				return "<pre>\nID: " + account.getId() + "\nHandle: " + account.getHandle() + "\nDescription: "
						+ account.getDescription() + "\nPost count: [total number of posts, including endorsements and " +
						"replies]" + "\nEndorse count: [sum of endorsements received by each post of this account]" +
						"\n</pre>"; //todo fill in final fields
			}
		}
		throw new HandleNotRecognisedException();
	}

	@Override
	public int createPost(String handle, String message) throws HandleNotRecognisedException, InvalidPostException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int endorsePost(String handle, int id)
			throws HandleNotRecognisedException, PostIDNotRecognisedException, NotActionablePostException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int commentPost(String handle, int id, String message) throws HandleNotRecognisedException,
			PostIDNotRecognisedException, NotActionablePostException, InvalidPostException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void deletePost(int id) throws PostIDNotRecognisedException {
		// TODO Auto-generated method stub

	}

	@Override
	public String showIndividualPost(int id) throws PostIDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StringBuilder showPostChildrenDetails(int id)
			throws PostIDNotRecognisedException, NotActionablePostException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getNumberOfAccounts() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTotalOriginalPosts() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTotalEndorsmentPosts() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTotalCommentPosts() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMostEndorsedPost() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMostEndorsedAccount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void erasePlatform() {
		// TODO Auto-generated method stub

	}

	@Override
	public void savePlatform(String filename) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void loadPlatform(String filename) throws IOException, ClassNotFoundException {
		// TODO Auto-generated method stub

	}

}
