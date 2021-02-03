package com.lumata.visume.config;

import java.io.InputStream;
import java.util.Properties;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ConfigReader
{


	private static final Logger logger = LoggerFactory.getLogger(ConfigReader.class);
	
	private final Properties configProp = new Properties();

	private ConfigReader()
	{
		try
		{
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			InputStream inputStream = classLoader.getResourceAsStream("config.properties");
			logger.info("Read all properties from file");
			configProp.load(inputStream);			
		}
		catch (Exception e)
		{
			logger.error("Error is Loading the config.properties:::" + e.getMessage());
		}
	}

	private static class LazyHolder
	{
		private static final ConfigReader INSTANCE = new ConfigReader();
	}

	public static ConfigReader getInstance()
	{
		return LazyHolder.INSTANCE;
	}

	public String getProperty(String key)
	{
		return configProp.getProperty(key).trim();
	}

	public Set<String> getAllPropertyNames()
	{
		return configProp.stringPropertyNames();
	}

	public boolean containsKey(String key)
	{
		return configProp.containsKey(key);
	}

}

