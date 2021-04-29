package socialmedia;

import java.io.*;
import java.util.ArrayList;

/**
 * Functioning implementor of
 * the SocialMediaPlatform interface.
 *
 * @authors Toby Forbes and Harry Collins
 * @version 2.1
 */
public class SocialMedia implements SocialMediaPlatform {

	//global platform variables
	ArrayList<Account> accounts = new ArrayList<>();
	ArrayList<Post> posts = new ArrayList<>();
	int IDCounter = 0;
	int postIDCounter = 0;

	@Override
	public int createAccount(String handle) throws IllegalHandleException, InvalidHandleException {
		//checks to see if handle for account is valid
		if(handle == null || handle.length() > 30 || handle.contains(" ")){
			throw new InvalidHandleException();
		}
		//checks to see if handle is unique
		for(Account account : accounts){
			if(account.getHandle().equals(handle)){
				throw new IllegalHandleException();
			}
		}
		//creates new account
		IDCounter++;
		Account newAccount = new Account(handle, null, IDCounter);
		accounts.add(newAccount);
		return IDCounter;
	}

	@Override
	public int createAccount(String handle, String description) throws IllegalHandleException, InvalidHandleException {
		//checks to see if handle for account is valid
		if(handle == null || handle.length() > 30 || handle.contains(" ")){
			throw new InvalidHandleException();
		}
		//checks to see if handle is unique
		for(Account account : accounts){
			if(account.getHandle().equals(handle)){
				throw new IllegalHandleException();
			}
		}
		//creates new account (with description)
		IDCounter++;
		Account newAccount = new Account(handle, description, IDCounter);
		accounts.add(newAccount);
		return IDCounter;
	}

	@Override
	public void removeAccount(int id) throws AccountIDNotRecognisedException, PostIDNotRecognisedException {
		//finds account and removes it
		for(Account account : accounts){
			if(account.getId() == id){
				accounts.remove(account);

				for(Post post : posts){
					if(post.getHandle().equals(account.getHandle())){
						deletePost(post.getId());
					}
				}
				return;
			}
		}
		//throws error if no matching id
		throw new AccountIDNotRecognisedException();
	}

	@Override
	public void removeAccount(String handle) throws HandleNotRecognisedException, PostIDNotRecognisedException {
		//finds account and removes it
		for(Account account : accounts){
			if(account.getHandle().equals(handle)){
				accounts.remove(account);
				for(Post post : posts){
					if(post.getHandle().equals(handle)){
						deletePost(post.getId());
					}
				}
				return;
			}
		}
		//throws error if no matching handle
		throw new HandleNotRecognisedException();
	}

	@Override
	public void changeAccountHandle(String oldHandle, String newHandle)
			throws HandleNotRecognisedException, IllegalHandleException, InvalidHandleException {
		//checks new handle is valid
		if(newHandle == null || newHandle.length() > 30 || newHandle.contains(" ")){
			throw new InvalidHandleException();
		}
		//checks new handle is unique
		for(Account account : accounts){
			if(account.getHandle().equals(newHandle)){
				throw new IllegalHandleException();
			}
		}
		//finds account from old handle and replaces with new handle
		for(Account account : accounts){
			if(account.getHandle().equals(oldHandle)){
				account.setHandle(newHandle);
				//changes any posts with the old handle to the old handle
				for(Post post : posts){
					if(post.getHandle().equals(oldHandle)){
						post.setHandle(newHandle);
					}
				}
				return;
			}
		}
		//throws error if no account found
		throw new HandleNotRecognisedException();
	}

	@Override
	public void updateAccountDescription(String handle, String description) throws HandleNotRecognisedException {
		//finds account and sets the description
		for(Account account : accounts){
			if(account.getHandle().equals(handle)){
				account.setDescription(description);
				return;
			}
		}
		//throws error if no account found
		throw new HandleNotRecognisedException();
	}

	@Override
	public String showAccount(String handle) throws HandleNotRecognisedException {
		//calculates total posts from an account
		int totalPosts = 0;
		for(Post post : posts){
			if(post.getHandle().equals(handle)){
				totalPosts++;
			}
		}
		//finds account and outputs the necessary data
		for(Account account : accounts){
			if(account.getHandle().equals(handle)){
				return "\nID: " + account.getId() + "\nHandle: " + account.getHandle() + "\nDescription: "
						+ account.getDescription() + "\nPost count: " + totalPosts + "\nEndorse count: " +
						account.getEndorsementNum() + "\n";
			}
		}
		//throws error if account isn't found
		throw new HandleNotRecognisedException();
	}

	@Override
	public int createPost(String handle, String message) throws HandleNotRecognisedException, InvalidPostException {
		//checks there is an account with that handle, throws error if not
		checkAccount(handle);
		//checks message is valid, throws error if not
		if(!isMessageAccepted(message))
		{
			throw new InvalidPostException();
		}
		//creates new account
		postIDCounter++;
		posts.add(new Post(handle,message, postIDCounter));
		return postIDCounter;
	}

	/**
	 * checks if a given handle belongs to any of the accounts, throws error if not
	 * @param handle account's handle
	 * @throws HandleNotRecognisedException if handle cannot be found in accounts
	 */
	public void checkAccount(String handle) throws HandleNotRecognisedException {
		for(Account account : accounts){
			if(account.getHandle().equals(handle)){
				return;
			}
		}
		throw new HandleNotRecognisedException();
	}

	/**
	 * A validity checker for messages
	 * @param message The string being checked
	 * @return true if valid, false if not
	 */
	private boolean isMessageAccepted(String message) {
		if(message.trim().length() > 0 && message.length() <= 100) {
			return true;
		}
		return false;
	}

	@Override
	public int endorsePost(String handle, int id)
			throws HandleNotRecognisedException, PostIDNotRecognisedException, NotActionablePostException {

		String message;
		//checks there is an account with that handle, throws error if not
		checkAccount(handle);

		//find the post being endorsed
		for(Post post:posts)
		{
			if(post.getId() == id)
			{
				if(post instanceof Endorsement)
				{
					//cannot endorse an endorsement
					throw new NotActionablePostException();
				}
				else
				{
					message = post.getMessage();
					//header for endorsement
					String header = "EP@"+post.getHandle()+":";
					if (message.length() + header.length() > 100)
					{
						//make space for header if there isn't enough space
						int removeNum = message.length() + header.length() + 1 - 100;
						message = message.substring(0, message.length() - removeNum);
					}
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
	/**
	 * A validity checker for post ID, throws error if it is an endorsement or if the post isn't found
	 * @param id The int id being checked
	 * @throws NotActionablePostException if the post is an endorsement
	 * @throws PostIDNotRecognisedException if the post cannot be found
	 */
	public void checkId(int id) throws NotActionablePostException, PostIDNotRecognisedException {
		for(Post post : posts){
			if(post.getId() == id){
				if(post instanceof Endorsement)
				{
					throw new NotActionablePostException();
				}
				//only accept if post is not an endorsement
				return;
			}
		}
		throw new PostIDNotRecognisedException();
	}

	@Override
	public int commentPost(String handle, int id, String message) throws HandleNotRecognisedException,
			PostIDNotRecognisedException, NotActionablePostException, InvalidPostException {
		//checks if there is an account with that handle, throws error if not
		checkAccount(handle);
		//checks if that inputted post id is valid, throws error if not
		checkId(id);
		//checks comment message is valid, throws error if not
		if(message == null || message.length() > 100){
			throw new InvalidPostException();
		}
		//creates new comment
		postIDCounter++;
		Comment newComment = new Comment(handle, message, postIDCounter, id);
		posts.add(newComment);

		//adds comment as a child of parent post
		for(Post post : posts){
			if(post.getId() == id){
				post.addChild(postIDCounter);
				break;
			}
		}

		return postIDCounter;
	}

	/**
	 * Turns Post into a String that can be outputted
	 * @param inputPost the post that needs to be turned into a String
	 * @param indents the number of indents to place the output to the right of, used to format the output when
	 *                   creating tree of posts
	 * @return String of visual interpretation of a post
	 */
	public String toString(Post inputPost, int indents) {
		//builds the formatting based on input "indents"
		String part1 = "";
		String part2 = "";
		StringBuilder tabs = new StringBuilder();
		StringBuilder tabsLess = new StringBuilder();
		if(indents>0){
			part1 = "|";
			part2 = "| > ";
		}
		tabs.append("\n");
		for(int i = 0; i<indents; i++){
			tabs.append("\t");
		}
		tabsLess.append("\n");
		for(int i = 1; i<indents; i++){
			tabsLess.append("\t");
		}

		//counts number of comment and endorsements a post has
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

		//builds output
		return tabsLess + part1 + tabsLess + part2 + "ID: " + inputPost.getId() + tabs + "Account: " +
				inputPost.getHandle() + tabs + "No. endorsements: " + numEndorsements + " | No. comments: " +
				numComments + tabs + inputPost.getMessage();
	}

	@Override
	public void deletePost(int id) throws PostIDNotRecognisedException {
		boolean postFound = false;

		//find the post being deleted
		for (Post post: posts)
		{
			if (id == post.getId())
			{
				postFound = true;

				//set the message to blank
				post.setMessage("The original content was removed from the system and is no longer available.");
				post.setDeleted(true); //boolean flag

				//find and delete endorsement posts of this post as well, using the children arraylist in the post
				for (int child : post.getChildren())
				{
					for (Post childPost: posts)
					{
						if (child == childPost.getId() && childPost instanceof Endorsement)
						{
							//delete the endorsement
							childPost.setMessage("");
							childPost.setDeleted(true);

							//subtract 1 from the number of endorsements the account has received
							for (Account account: accounts)
							{
								if (account.getHandle().equals(post.getHandle()))
								{
									account.changeEndorsementNum(-1);
								}
							}
							childPost.setHandle("Deleted");
						}
					}
				}
				post.setHandle("Deleted"); //set the handle to say the deleted status
			}
		}
		if(!postFound) {
			throw new PostIDNotRecognisedException();
		}
	}

	@Override
	public String showIndividualPost(int id) throws PostIDNotRecognisedException {
		//finds post from id, then returns the post as a String
		for (Post post: posts) {
			if (post.getId() == id) {
				return toString(post, 0);
			}
		}
		//throws error if post not found
		throw new PostIDNotRecognisedException();
	}

	/**
	 * returns post from post id
	 * @param id of the post you want returned
	 * @return the post wanted
	 * @throws PostIDNotRecognisedException when the post isn't found
	 */
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
		//throws error if endorsement is inputted as an endorsement can't ever have child posts
		if(getPost(id) instanceof Endorsement){
			throw new NotActionablePostException();
		}
		return builder(getPost(id), new StringBuilder(), 0, new ArrayList<>());
	}

	/**
	 *
	 * @param post the post that needs to be added to the tree structure
	 * @param string the current String Builder with posts added
	 * @param indent the current indentation level of the post
	 * @param visited the nodes that have already been visited so that there are no duplicates
	 * @return the StringBuilder with posts that have been added
	 * @throws PostIDNotRecognisedException when the id is not valid
	 */
	public StringBuilder builder(Post post, StringBuilder string, int indent, ArrayList<Post> visited)
			throws PostIDNotRecognisedException {
		//checks for endorsement as these shouldn't be added
		if(post instanceof Endorsement){
			return string;
		}
		//adds all the children of the current post
		for(int child : post.getChildren()){
			if(!visited.contains(post)){
				string.append(toString(post, indent));
				visited.add(post);
			}
			builder(getPost(child), string, (indent + 1), visited); //recursion is used to traverse the tree
		}
		//checks for leaf of tree
		if(post.getChildren().size() == 0){
			string.append(toString(post, indent));
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
		//add 1 to the counter for every non-deleted post that isn't a comment or endorsement
		for(Post post : posts){
			if(!(post instanceof Endorsement) && !(post instanceof Comment) && !post.getDeleted()){
				counter++;
			}
		}
		return counter;
	}

	@Override
	public int getTotalEndorsmentPosts() {
		int counter = 0;
		//add 1 to the counter for every non-deleted endorsement
		for(Post post : posts){
			if(post instanceof Endorsement && !post.getDeleted()){
				counter++;
			}
		}
		return counter;
	}

	@Override
	public int getTotalCommentPosts() {
		int counter = 0;
		//add 1 to the counter for every non-deleted comment
		for(Post post : posts){
			if(post instanceof Comment && !post.getDeleted()){
				counter++;
			}
		}
		return counter;
	}

	@Override
	public int getMostEndorsedPost() {
		//set default values for most endorsed to be compared with
		int greatestNum = 0;
		Post mostEndorsed = posts.get(0);
		//count the endorsements that every post has
		for(Post post : posts){
			int endorseNum = 0;
			for(int child : post.getChildren()){
				for(Post post2 : posts){
					if(post2.getId() == child){
						if(post2 instanceof Endorsement){
							endorseNum++;
						}
					}
				}
			}
			//if the post has more endorsements than the current top post, replace it
			if(endorseNum > greatestNum){
				greatestNum = endorseNum;
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

		//go through the accounts
		for (Account account: accounts)
		{
			//if the account being checked has more endorsements than the current most endorsed,
			// set this one to most endorsed
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
		//delete all data from the platform
		accounts.clear();
		posts.clear();
		IDCounter = 0;
		postIDCounter = 0;
	}

	@Override
	public void savePlatform(String filename) throws IOException {
		try {
			//save all data on the platform
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
		}
	}

	@Override
	public void loadPlatform(String filename) throws IOException, ClassNotFoundException {
		ArrayList<Object> read = null;
		//load all platform data
		try {
			FileInputStream fileIn = new FileInputStream(filename);
			ObjectInputStream objectIn = new ObjectInputStream(fileIn);
			read = (ArrayList<Object>) objectIn.readObject();
			objectIn.close();
		} catch (FileNotFoundException exception) {
			exception.printStackTrace();
		}

		accounts = (ArrayList<Account>) read.get(0);
		posts = (ArrayList<Post>) read.get(1);
		IDCounter = (int) read.get(2);
		postIDCounter = (int) read.get(3);
	}
}