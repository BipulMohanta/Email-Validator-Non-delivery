package com.mav.email.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.mav.email.service.RequestResponseLogService;

@Component
@Order(1)
public class RequestResponseLoggingFilter implements Filter {

	@Autowired
	private RequestResponseLogService requestResponseLogService;
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse  = (HttpServletResponse) response;
		System.out.println("RequestResponseLoggingFilter before --- "+httpServletRequest.getRequestURL());
		requestResponseLogService.persistReqResInDB(httpServletRequest);
		chain.doFilter(request, response);
		System.out.println("RequestResponseLoggingFilter after--- "+httpServletRequest.getRequestURL());
	}

}
