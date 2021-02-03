// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.lumata.visume.config.msal;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Object containing configuration data for the application. Spring will automatically wire the
 * values by grabbing them from application.properties file
 */
@Component
class BasicConfiguration {

	private static final Logger logger = LoggerFactory.getLogger(BasicConfiguration.class);
	
	
    private String clientId;
    private String authority;
    private String redirectUriSignin;
    private String redirectUriGraph;
    private String secretKey;
    private String msGraphEndpointHost;

    @PostConstruct
    private void init()
	{
    	try {
    		com.lumata.visume.config.ConfigReader config = com.lumata.visume.config.ConfigReader.getInstance();
        	clientId = config.getProperty("aad.clientId");
        	authority = config.getProperty("aad.authority");
        	redirectUriSignin = config.getProperty("aad.redirectUriSignin");
        	redirectUriGraph = config.getProperty("aad.redirectUriGraph");
        	secretKey = config.getProperty("aad.secretKey");
        	msGraphEndpointHost = config.getProperty("aad.msGraphEndpointHost");
		} catch (Exception e) {
			logger.error("Error in loading MSAL AAD properties, {}", e);
			throw e;
		}
    	
    }

    
    public String getAuthority(){
        if (!authority.endsWith("/")) {
            authority += "/";
        }
        return authority;
    }

    public String getClientId() {
        return clientId;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getRedirectUriSignin() {
        return redirectUriSignin;
    }

    public void setRedirectUriSignin(String redirectUriSignin) {
        this.redirectUriSignin = redirectUriSignin;
    }

    public String getRedirectUriGraph() {
        return redirectUriGraph;
    }

    public void setRedirectUriGraph(String redirectUriGraph) {
        this.redirectUriGraph = redirectUriGraph;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public void setMsGraphEndpointHost(String msGraphEndpointHost) {
        this.msGraphEndpointHost = msGraphEndpointHost;
    }

    public String getMsGraphEndpointHost(){
        return msGraphEndpointHost;
    }
}