package com.lumata.visume.service;

import java.io.IOException;
import java.net.MalformedURLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.microsoft.aad.msal4j.IAuthenticationResult;

public interface MsalAuthenticationHelperService {
	
	static final String PRINCIPAL_SESSION_NAME = "principal";
    static final String TOKEN_CACHE_SESSION_ATTRIBUTE = "token_cache";

	void processAuthenticationCodeRedirect(HttpServletRequest httpRequest, String currentUri, String fullUrl) throws Throwable;
	
	IAuthenticationResult getAuthResultBySilentFlow(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws Throwable;
	
	void sendAuthRedirect(HttpServletRequest httpRequest, HttpServletResponse httpResponse, String scope, String redirectURL) throws IOException;
	
	String getAuthorizationCodeUrl(String claims, String scope, String registeredRedirectURL, String state, String nonce)
            throws MalformedURLException;
	
	
}
