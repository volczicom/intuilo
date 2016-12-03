package com.doctusoft.example.forum;

/**
 * Contains the client IDs and scopes for allowed clients consuming the APIs.
 */
public class Constants {

	// TODO: This client id should be replaced with the one you got during the google project setup!
	// Without this your deployed application won't work because you have to specify the authorized urls for every client id.
	// This id works only if you serve your application from http://localhost:8080 or https://forum-training-example.appspot.com
	/**
	 * Google uses this client id to identify which application asks for a permission.<br/> 
	 * You can ask for different kind of user permissions like know their identity or edit their calendar. These are called scopes.<br/> 
	 * The users have to approve this request before the application gets access to the selected scopes.
	 */
	public static final String WEB_CLIENT_ID = "1097207723412-2u798llcj8pn0fifd31pnqsqtmvom1tc.apps.googleusercontent.com";

	/**
	 * This scope lets the application see the email address of the user.
	 */
	public static final String EMAIL_SCOPE = "https://www.googleapis.com/auth/userinfo.email";
}
