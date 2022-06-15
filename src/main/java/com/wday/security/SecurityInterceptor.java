package com.wday.security;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * Interceptor to apply secure HTTP endpoints.
 */
@Component
public class SecurityInterceptor
        implements HandlerInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityInterceptor.class);

    @Value("${wday.security.username}")
    private String username;

    @Value("${wday.security.password}")
    private String password;

    /**
     * Basic authentication using Authorization header with Base64 encoded username and password.
     * Requires: Authorization=base64(username:password)
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        boolean isAuthorized = false;
        try {
            String base64AuthToken = request.getHeader(HttpHeaders.AUTHORIZATION);
            if (StringUtils.hasText(base64AuthToken)) {
                byte[] authTokenDecoded = Base64.getDecoder().decode(base64AuthToken);
                String authToken = new String(authTokenDecoded, StandardCharsets.UTF_8);
                String[] authValues = authToken.split(":", 2);

                if (authValues.length == 2) {
                    isAuthorized = this.username.equals(authValues[0]) && this.password.equals(authValues[1]);
                }
            }
        }
        catch (Exception e) {
            LOGGER.error("Failed to validate user credential", e);
        }

        if (!isAuthorized) {
            response.getWriter().write("Unauthorized access. Please try again with correct credentials.");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
        }

        return isAuthorized;
    }

}
