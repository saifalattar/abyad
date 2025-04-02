package com.example.abyad.Security;

import com.example.abyad.AbyadExceptions.AbyadExceptions;
import com.example.abyad.Properties;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

@Component
public class SecureRequest extends OncePerRequestFilter {
    final private HandlerExceptionResolver handlerExceptionResolver;
    final private Properties properties;
    @Autowired
    private AbyadExceptions abyadExceptions;
    private Logger log;

    SecureRequest(HandlerExceptionResolver handlerExceptionResolver, @Qualifier("Exceptions") AbyadExceptions abyadExceptions, Properties properties){
        this.abyadExceptions = abyadExceptions;
        this.handlerExceptionResolver = handlerExceptionResolver;
        this.properties = properties;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);
        log = LoggerFactory.getLogger(wrappedRequest.getServletPath());

        ArrayList<String> disallowedHeaders = new ArrayList<>();
        LinkedHashMap allowedHeaders = (LinkedHashMap)properties.getProperty("allowedHeaders");

        wrappedRequest.getHeaderNames().asIterator().forEachRemaining(x->{
            if(!allowedHeaders.containsValue(x)){
                disallowedHeaders.add(x);
            }
        });

        if(!disallowedHeaders.isEmpty()){
            System.out.println(disallowedHeaders);
            try {
                log.error("Disallowed headers :  {}" ,disallowedHeaders);
                throw abyadExceptions.setErrorKey("473857");
            } catch (AbyadExceptions e) {
                handlerExceptionResolver.resolveException(wrappedRequest, responseWrapper, null, e);
            }
        }else{
            filterChain.doFilter(wrappedRequest, responseWrapper);
        }

        log.info("REQUEST :  " +  new String(wrappedRequest.getContentAsByteArray()));

        log.info("FINAL RESPONSE :  " + new String(responseWrapper.getContentAsByteArray()));
        responseWrapper.copyBodyToResponse();

    }
}
