package socialmedia;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * BadSocialMedia is a minimally compiling, but non-functioning implementor of
 * the SocialMediaPlatform interface.
 * 
 * @author Diogo Pacheco
 * @version 1.0
 */
public class SocialMedia implements SocialMediaPlatform {

	ArrayList<Account> accounts = new ArrayList<>();
	ArrayList<Post> posts = new ArrayList<>();
	int IDCounter = 0;
	int postIDCounter = 0;

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
		IDCounter++;
		Account newAccount = new Account(handle, null, IDCounter);
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
		IDCounter++;
		Account newAccount = new Account(handle, null, IDCounter);
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

		//if handle is not found throw exception
		if(!isRealHandle(handle))
		{
			throw new HandleNotRecognisedException();
		}

		if(!isMessageAccepted(message))
		{
			throw new InvalidPostException();
		}
		postIDCounter++;
		posts.add(new Post(handle,message, postIDCounter));
		return postIDCounter;
	}

	/**
	 * checks if a given handle belongs to any of the accounts
	 * @param handle account's handle
	 * @return true or false
	 */
	private boolean isRealHandle(String handle)
	{
		for(Account account : accounts){
			if(account.getHandle().equals(handle)){
				return true;
			}
		}
		return false;
	}

	/**
	 * A validity checker for messages
	 * @param message The string being checked
	 * @return true if valid, false if not
	 */
	private boolean isMessageAccepted(String message)
	{
		if(message != " " && message.length() <= 100)
		{
			return true;
		}
		return false;
	}


	@Override
	public int endorsePost(String handle, int id)
			throws HandleNotRecognisedException, PostIDNotRecognisedException, NotActionablePostException {

		String message;

		if(!isRealHandle(handle))
		{
			throw new HandleNotRecognisedException();
		}

		//find the post being endorsed
		for(Post post:posts)
		{
			if(post.getId() == id)
			{
				if(post instanceof Endorsement)
				{
					throw new NotActionablePostException();
				}
				else
				{
					message = post.getMessage();
					postIDCounter++;
					posts.add(new Endorsement(handle,message, postIDCounter,id));
					post.addChild(postIDCounter);

					boolean accountFound = false;
					for (Account account: accounts)
					{
						if (account.getHandle().equals(post.getHandle()))
						{
							accountFound = true;
							account.changeEndorsementNum(1);
						}
					}
					if (!accountFound)
					{
						throw new HandleNotRecognisedException();
					}

					return postIDCounter;

				}
			}
		}
		//if no post could be found then throw an exception
		throw new PostIDNotRecognisedException();

	}

	public void checkAccount(String handle) throws HandleNotRecognisedException {
		for(Account account : accounts){
			if(account.getHandle().equals(handle)){
				return;
			}
		}
		throw new HandleNotRecognisedException();
	}

	public void checkId(int id) throws NotActionablePostException, PostIDNotRecognisedException {
		for(Post post : posts){
			if(post.getId() == id){
				if(post instanceof Endorsement)
				{
					throw new NotActionablePostException();
				}
				return;
			}
		}
		throw new PostIDNotRecognisedException();
	}

	@Override
	public int commentPost(String handle, int id, String message) throws HandleNotRecognisedException,
			PostIDNotRecognisedException, NotActionablePostException, InvalidPostException {
		checkAccount(handle);
		checkId(id);
		if(message == null || message.length() > 100){
			throw new InvalidPostException();
		}
		postIDCounter++;
		Comment newComment = new Comment(handle, message, postIDCounter, id);
		posts.add(newComment);

		for(Post post : posts){
			if(post.getId() == id){
				posts.add(newComment);
				post.addChild(postIDCounter);
				break;
			}
		}

		return postIDCounter;
	}

	public String toString(Post inputPost) {
		int numEndorsements = 0;
		int numComments = 0;
		for(int child : inputPost.getChildren()){
			for(Post post : posts){
				if(post.getId() == child){
					if(post instanceof Comment){
						numComments++;
					}
					else if(post instanceof Endorsement){
						numEndorsements++;
					}
				}
			}
		}
		return "ID: " + inputPost.getId() + "\nAccount: " + inputPost.getHandle() + "\nNo. endorsements: " +
				numEndorsements + " | No. comments: " + numComments + "\n" + inputPost.getMessage();
	}

	@Override
	public void deletePost(int id) throws PostIDNotRecognisedException {
		boolean postFound = false;
		boolean childPostFound;
		for (Post post: posts)
		{
			if (id == post.getId())
			{
				postFound = true;
				post.setMessage(null); //set the message to blank
				for (int child : post.getChildren())
				{
					childPostFound = false;
					for (Post childPost: posts)
					{
						childPostFound = true;
						if (child == childPost.getId() && childPost instanceof Endorsement)
						{
							childPost.setMessage(null);
							for (Account account: accounts)
							{
								if (account.getHandle().equals(post.getHandle()))
								{
									account.changeEndorsementNum(-1);
								}
							}
						}
					}
					if (!childPostFound)
					{
						throw new PostIDNotRecognisedException();
					}
				}
			}
		}
		if(!postFound)
		{
			throw new PostIDNotRecognisedException();
		}
	}

	@Override
	public String showIndividualPost(int id) throws PostIDNotRecognisedException {
		for (Post post: posts)
		{
			if (post.getId() == id)
			{
				return toString(post);
			}
		}
		throw new PostIDNotRecognisedException();
	}

	public Post getPost(int id) throws PostIDNotRecognisedException {
		for(Post post : posts) {
			if (post.getId() == id) {
				return post;
			}
		}
		throw new PostIDNotRecognisedException();
	}

	@Override
	public StringBuilder showPostChildrenDetails(int id)
			throws PostIDNotRecognisedException, NotActionablePostException {
		if(getPost(id) instanceof Endorsement){
			throw new NotActionablePostException();
		}
		builder(getPost(id), new StringBuilder());
		return null;
	}

	public StringBuilder builder(Post post, StringBuilder string) throws PostIDNotRecognisedException {
		if(post instanceof Endorsement){
			return string;
		}
		for(int child : post.getChildren()){
			builder(getPost(child), string.append(post.toString()));
		}
		return string;
	}

	@Override
	public int getNumberOfAccounts() {
		return accounts.size();
	}

	@Override
	public int getTotalOriginalPosts() {
		int counter = 0;
		for(Post post : posts){
			if(!(post instanceof Endorsement) && !(post instanceof Comment)){
				counter++;
			}
		}
		return counter;
	}

	@Override
	public int getTotalEndorsmentPosts() {
		int counter = 0;
		for(Post post : posts){
			if(post instanceof Endorsement){
				counter++;
			}
		}
		return counter;
	}

	@Override
	public int getTotalCommentPosts() {
		int counter = 0;
		for(Post post : posts){
			if(post instanceof Comment){
				counter++;
			}
		}
		return counter;
	}

	@Override
	public int getMostEndorsedPost() {
		int greatestNum = 0;
		Post mostEndorsed = posts.get(0);
		for(Post post : posts){
			if(post.getChildren().size() > greatestNum){
				greatestNum = post.getChildren().size();
				mostEndorsed = post;
			}
		}
		return mostEndorsed.getId();
	}

	@Override
	public int getMostEndorsedAccount() {
		//default values
		int mostEndorsedId = 0;
		int mostEndorsements = -1;
		for (Account account: accounts)
		{
			if(account.getEndorsementNum() > mostEndorsements)
			{
				mostEndorsedId = account.getId();
				mostEndorsements = account.getEndorsementNum();
			}
		}
		return mostEndorsedId;
	}

	@Override
	public void erasePlatform() {
		accounts.clear();
		posts.clear();
		IDCounter = 0;
		postIDCounter = 0;
	}

	@Override
	public void savePlatform(String filename) throws IOException {
		try {
			FileOutputStream fileOut = new FileOutputStream(filename);
			ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
			ArrayList<Object> output = new ArrayList<>();
			output.add(accounts);
			output.add(posts);
			output.add(IDCounter);
			output.add(postIDCounter);
			objectOut.writeObject(output);
			objectOut.close();
		} catch (FileNotFoundException exception) {
			exception.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void loadPlatform(String filename) throws IOException, ClassNotFoundException {
		ArrayList<Object> read = null;
		try {
			FileInputStream fileIn = new FileInputStream(filename);
			ObjectInputStream objectIn = new ObjectInputStream(fileIn);
			read = (ArrayList<Object>) objectIn.readObject();
			objectIn.close();
		} catch (FileNotFoundException exception) {
			exception.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		accounts = (ArrayList<Account>) read.get(0);
		posts = (ArrayList<Post>) read.get(1);
		IDCounter = (int) read.get(2);
		postIDCounter = (int) read.get(3);
	}

}