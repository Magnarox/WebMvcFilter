package com.example.demo;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class MyFilter implements Filter {
    private Pattern jsonVersionHeaderPattern = Pattern.compile("^application/json;\\s?version=(.*?)$");

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {
            System.out.println("*********FILTER***************");
      MutableHttpServletRequest mutableRequest = new MutableHttpServletRequest((HttpServletRequest)request);
      chain.doFilter(mutableRequest, response);
    }

    class MutableHttpServletRequest extends HttpServletRequestWrapper {

        private String innerContentType;
        public MutableHttpServletRequest(HttpServletRequest request){
            super(request);
            innerContentType = request.getContentType();
            String reqCT = request.getContentType();
            if(reqCT != null && !reqCT.isEmpty()){
                Matcher matcher = jsonVersionHeaderPattern.matcher(reqCT);
                if(matcher.matches()) {
                    String version = matcher.group(1);
                    if(version != null && !version.isEmpty()) {
                        innerContentType = "application/v" + version + "+json";
                    }
                }
            }
        }

        @Override
        public String getContentType(){
            return innerContentType;
        }
    }
}
