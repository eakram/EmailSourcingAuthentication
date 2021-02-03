package com.lumata.visume.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.lumata.visume.info.User;
import com.lumata.visume.service.EmailService;

@Service
public class EmailServiceImpl implements EmailService{

	private static final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);
	
	@Override
	public User sourceEmails(String folderName) throws Exception {
		
		return getUser();		
	}
	
	
	

	private static User getUser()
	{
		User user = new User();
		user.setName("Akram");	
		user.setEmail("akram9458@outlook.com");
		List<String> folders = new ArrayList<String>();
		folders.add("Inbox");
		folders.add("Drafts");
		folders.add("Outbox");
		folders.add("Sent");		
		user.setOptionFolders(folders);	
		return user;
	}
	/*
	 * @Override public Properties getProperties() { final Properties
	 * oAuthProperties = new Properties(); try {
	 * oAuthProperties.load(EmailServiceImpl.class.getResourceAsStream(
	 * "config.properties")); } catch (IOException e) { System.out.
	 * println("Unable to read OAuth configuration. Make sure you have a properly formatted oAuth.properties file. See README for details."
	 * ); return null; }
	 * 
	 * final String appId = oAuthProperties.getProperty("app.id"); final String[]
	 * appScopes = oAuthProperties.getProperty("app.scopes").split(",");
	 * logger.info("App ID : {}", appId); logger.info("Scopes : {}", appScopes);
	 * return oAuthProperties; }
	 * 
	 * @Override public List<String> getFolders() { Properties oAuthProperties=
	 * getProperties(); final String appId = oAuthProperties.getProperty("app.id");
	 * final String[] appScopes =
	 * oAuthProperties.getProperty("app.scopes").split(","); isAuthenticated();
	 * return null; }
	 * 
	 */

}
