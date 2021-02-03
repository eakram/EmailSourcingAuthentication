package com.lumata.visume.service;


import com.lumata.visume.info.User;

public interface EmailService {
	
	public User sourceEmails(String folderName) throws Exception;
	
}
