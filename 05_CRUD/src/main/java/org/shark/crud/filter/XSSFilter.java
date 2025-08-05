package org.shark.crud.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

@Component
public class XSSFilter implements Filter {

  @Override
  public void destroy() { }

  //----- 요청 (request)이 XSSRequestWrapper에 의해서 처리됩니다.
  @Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    System.out.println("===== XSSFilter 동작 =====");
    chain.doFilter(new XSSRequestWrapper((HttpServletRequest)request), response);
	}

  @Override
	public void init(FilterConfig fConfig) throws ServletException { }

}
