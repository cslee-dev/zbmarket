package com.example.zbmarket.security.filter;


import com.example.zbmarket.rest.member.model.MemberMatchRequestDto;
import com.example.zbmarket.rest.member.model.MemberMatchResponseDto;
import com.example.zbmarket.security.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.log.LogMessage;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.context.NullSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class CustomUsernamePasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private static final AntPathRequestMatcher DEFAULT_ANT_PATH_REQUEST_MATCHER = new AntPathRequestMatcher("/api/v1/sign-in", "POST");
    private boolean postOnly = true;
    private final ObjectMapper objectMapper;

    private SecurityContextRepository securityContextRepository = new NullSecurityContextRepository();
    private final AuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();


    public CustomUsernamePasswordAuthenticationFilter(
            AuthenticationManager authenticationManager,
            ObjectMapper objectMapper
    ) {
        super(DEFAULT_ANT_PATH_REQUEST_MATCHER, authenticationManager);
        this.objectMapper = objectMapper;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        if (this.postOnly && !StringUtils.equals(request.getMethod(), HttpMethod.POST.name())) {
            throw new AuthenticationServiceException(
                    "Authentication method not supported:" + request.getMethod()
            );
        }
        if (!MediaType.APPLICATION_JSON_VALUE.equals(request.getContentType())) {
            throw new AuthenticationServiceException(
                    "content-type error :" + request.getContentType()
            );
        }
        MemberMatchRequestDto memberMatchRequestDto = getMemberMatchRequestDto(request);
        UsernamePasswordAuthenticationToken authRequest = getAuthRequest(memberMatchRequestDto);
        setDetails(request, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    private static UsernamePasswordAuthenticationToken getAuthRequest(MemberMatchRequestDto memberMatchRequestDto) {
        String username = memberMatchRequestDto.getEmail();
        username = StringUtils.isNotEmpty(username) ? username.trim() : "";
        String password = memberMatchRequestDto.getPassword();
        password = StringUtils.isNotEmpty(password) ? password : "";
        return UsernamePasswordAuthenticationToken.unauthenticated(
                username, password
        );
    }

    private MemberMatchRequestDto getMemberMatchRequestDto(HttpServletRequest request) throws IOException {
        BufferedReader reader = request.getReader();
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        // JSON 문자열을 Java 객체로 변환
        MemberMatchRequestDto memberMatchRequestDto = objectMapper.readValue(sb.toString(), MemberMatchRequestDto.class);
        return memberMatchRequestDto;
    }


    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authResult);
        SecurityContextHolder.setContext(context);
        this.securityContextRepository.saveContext(context, request, response);
        if (this.logger.isDebugEnabled()) {
            this.logger.debug(LogMessage.format("Set SecurityContextHolder to %s", authResult));
        }
        String accessToken = JwtUtil.generateAccessToken(authResult.getName(), "USER");
        String refreshToken = JwtUtil.generateRefreshToken(authResult.getName(), "USER");
        MemberMatchResponseDto matched = new MemberMatchResponseDto(
                accessToken, refreshToken
        );
        ObjectMapper objectMapper = new ObjectMapper();
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter writer = response.getWriter();
        writer.write(objectMapper.writeValueAsString(matched));
        writer.flush();
        this.successHandler.onAuthenticationSuccess(request, response, authResult);
    }

    protected void setDetails(HttpServletRequest request, UsernamePasswordAuthenticationToken authRequest) {
        authRequest.setDetails(request.getRemoteAddr());
    }

    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }


    public void setSecurityContextRepository(SecurityContextRepository securityContextRepository) {
        this.securityContextRepository = securityContextRepository;
    }
}
