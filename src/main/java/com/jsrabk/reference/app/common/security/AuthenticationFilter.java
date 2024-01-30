package com.jsrabk.reference.app.common.security;

import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.GenericFilterBean;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
      throws IOException, ServletException {
    	
        try {
        	if (request instanceof HttpServletRequest) {
        		 String url = ((HttpServletRequest)request).getRequestURL().toString();
        		 //String path = ((HttpServletRequest)request).getPathInfo().toString();
        		 String queryString = ((HttpServletRequest)request).getQueryString();
        		 System.out.println(url + "?" + queryString);
        		 //System.out.println("path: " + path);
                 Authentication authentication = AuthenticationService.getAuthentication((HttpServletRequest) request);
                 SecurityContextHolder.getContext().setAuthentication(authentication);
    		} else {
    			System.out.println("request is not an instanceof HttpServletRequest");
    		}
        		

            System.out.println("doFilter try part");
        } catch (Exception exp) {
        	
        	System.out.println("doFilter catch part");
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
            PrintWriter writer = httpResponse.getWriter();
            writer.print(exp.getMessage());
            writer.flush();
            writer.close();
        }

        filterChain.doFilter(request, response);
    }
}