package com.lumata.visume.config.msal;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.microsoft.aad.msal4j.IAuthenticationResult;
import com.microsoft.aad.msal4j.MsalException;

/**
 * Processes incoming requests based on auth status
 */
@Component
@ComponentScan(basePackages = "com.lumata.visume.*")
public class MsalAadAuthFilter implements Filter {

	private static final Logger logger = LoggerFactory.getLogger(MsalAadAuthFilter.class);
	
    private List<String> excludedUrls = Arrays.asList("/", "/visume/");

    
    @Autowired
    MsalAadAuthHelper msalAadAuthHelper;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        if (request instanceof HttpServletRequest) {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            HttpServletResponse httpResponse = (HttpServletResponse) response;
			/*
			 * if (msalAadAuthHelper == null) { ServletContext context =
			 * httpRequest.getSession().getServletContext();
			 * SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
			 * context); }
			 */
            try {
                String currentUri = httpRequest.getRequestURL().toString();
                String path = httpRequest.getServletPath();
                String queryStr = httpRequest.getQueryString();
                String fullUrl = currentUri + (queryStr != null ? "?" + queryStr : "");

                // exclude home page
                if(excludedUrls.contains(path)){
                    chain.doFilter(request, response);
                    return;
                }

                if(containsAuthenticationCode(httpRequest)){
                    // response should have authentication code, which will be used to acquire access token
                	msalAadAuthHelper.processAuthenticationCodeRedirect(httpRequest, currentUri, fullUrl);

                    // remove query params so that containsAuthenticationCode will not be true on future requests
                    ((HttpServletResponse) response).sendRedirect(currentUri);

                    chain.doFilter(request, response);
                    return;
                }

                // check if user has a AuthData in the session
                if (!isAuthenticated(httpRequest)) {
                        // not authenticated, redirecting to login.microsoft.com so user can authenticate
                	msalAadAuthHelper.sendAuthRedirect(
                                httpRequest,
                                httpResponse,
                                null,
                                msalAadAuthHelper.getRedirectUriSignIn());
                        return;
                }

                if (isAccessTokenExpired(httpRequest)) {
                    updateAuthDataUsingSilentFlow(httpRequest, httpResponse);
                }
            } catch (MsalException authException) {
                // something went wrong (like expiration or revocation of token)
                // we should invalidate AuthData stored in session and redirect to Authorization server
                SessionManagementHelper.removePrincipalFromSession(httpRequest);
                msalAadAuthHelper.sendAuthRedirect(
                        httpRequest,
                        httpResponse,
                        null,
                        msalAadAuthHelper.getRedirectUriSignIn());
                return;
            } catch (Throwable exc) {
                httpResponse.setStatus(500);
                System.out.println(exc.getMessage());
                request.setAttribute("error", exc.getMessage());
                request.getRequestDispatcher("/error").forward(request, response);
                return;
            }
        }
        chain.doFilter(request, response);
    }

    private boolean containsAuthenticationCode(HttpServletRequest httpRequest) {
        Map<String, String[]> httpParameters = httpRequest.getParameterMap();

        boolean isPostRequest = httpRequest.getMethod().equalsIgnoreCase("POST");
        boolean containsErrorData = httpParameters.containsKey("error");
        boolean containIdToken = httpParameters.containsKey("id_token");
        boolean containsCode = httpParameters.containsKey("code");

        return isPostRequest && containsErrorData || containsCode || containIdToken;
    }

    private boolean isAccessTokenExpired(HttpServletRequest httpRequest) {
        IAuthenticationResult result = SessionManagementHelper.getAuthSessionObject(httpRequest);
        return result.expiresOnDate().before(new Date());
    }

    private boolean isAuthenticated(HttpServletRequest request) {
        return request.getSession().getAttribute(MsalAadAuthHelper.PRINCIPAL_SESSION_NAME) != null;
    }

    private void updateAuthDataUsingSilentFlow(HttpServletRequest httpRequest, HttpServletResponse httpResponse)
            throws Throwable {
        IAuthenticationResult authResult = msalAadAuthHelper.getAuthResultBySilentFlow(httpRequest, httpResponse);
        SessionManagementHelper.setSessionPrincipal(httpRequest, authResult);
    }

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
    	//SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);

		logger.info("MsalAadAuthFilter init");		
		/*
		 * authHelper = WebApplicationContextUtils.
		 * getRequiredWebApplicationContext(filterConfig.getServletContext()).
		 * getBean(MsalAadAuthHelper.class);
		 */
	}

	@Override
	public void destroy() {
		logger.info("MsalAadAuthFilter destroy");
	}
}
