package com.lumata.visume.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public interface MsalRequestFilterService {

	public void doFilter(ServletRequest request, ServletResponse response) throws IOException, ServletException;
}
