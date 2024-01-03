package org.delivery.api.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class LoggerFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        var req = new ContentCachingRequestWrapper((HttpServletRequest) request);
        var res = new ContentCachingResponseWrapper((HttpServletResponse) response);
        log.info("INIT uri : {}", req.getRequestURI());

        chain.doFilter(req, res);

        var headerNames = req.getHeaderNames();
        var headerValues = new StringBuilder();

        headerNames.asIterator().forEachRemaining(headerKey ->{
            var headerValue = req.getHeader(headerKey);
            headerValues.append("[").append(headerKey).append(" : ").append(headerValue).append("], \n"); // authorization-token : ??? , user-agent : ???
        });
        var uri = req.getRequestURI();
        var method = req.getMethod();
        var requestBody = new String(req.getContentAsByteArray());
        log.info(">>>> [uri : {}], [method : {}],  header :\n{}, [requestBody : {}]", uri, method, headerValues, requestBody);
        var responseHeaderValues = new StringBuilder();

        res.getHeaderNames().forEach(headerKey ->{
            var headerValue = res.getHeader(headerKey);
            responseHeaderValues.append("[").append(headerKey).append(" : ").append(headerValue).append("], \n");
        });

        var responseBody = new String(res.getContentAsByteArray());
        log.info("<<<< [uri : {}], [method : {}], header :\n{}, responseBody : Api : {}", uri, method, responseHeaderValues, responseBody);

        res.copyBodyToResponse();
    }
}
