package com.lumata.visume.config;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Configuration {
	
	private static final Logger logger = LoggerFactory.getLogger(Configuration.class);
	
	private static Configuration instance;
	private String applicationId;
	private Set<String> scopes;
	private String apiKey;
	private String apiSecret;
	
	
	private Configuration(String appId, Set<String> AllowedScopes)
	{
		this.applicationId = appId;
		this.scopes = AllowedScopes;
	}
	
	public static Configuration getInstance()
	{
		if(instance == null)
		{
			Properties oAuthProperties = getProperties();
			String appId = oAuthProperties.getProperty("app.id");
			String[] appScopes = oAuthProperties.getProperty("app.scopes").split(",");
			Set<String> scopeSet = new HashSet<String>(Arrays.asList(appScopes));
			instance = new Configuration(appId, scopeSet);
		}
		return instance;
	}
	
	
	public static Properties getProperties()
	{
		final Properties oAuthProperties = new Properties();
		try 
		{
		    oAuthProperties.load(Configuration.class.getResourceAsStream("config.properties"));
		} 
		catch (IOException e) 
		{
			logger.error("Unable to read OAuth configuration. Make sure you have a properly formatted config.properties file.");
		    return null;
		}

		String appId = oAuthProperties.getProperty("app.id");
		String[] appScopes = oAuthProperties.getProperty("app.scopes").split(",");
		logger.info("App ID : {}", appId);
		logger.info("Scopes : {}", appScopes);	
		return oAuthProperties;
	}

	public String getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

	public Set<String> getScopes() {
		return scopes;
	}

	public void setScopes(Set<String> scopes) {
		this.scopes = scopes;
	}

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public String getApiSecret() {
		return apiSecret;
	}

	public void setApiSecret(String apiSecret) {
		this.apiSecret = apiSecret;
	}
	
	
}
