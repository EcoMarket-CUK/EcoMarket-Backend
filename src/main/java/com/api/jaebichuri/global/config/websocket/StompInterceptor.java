package com.api.jaebichuri.global.config.websocket;

import com.api.jaebichuri.auth.entity.CustomUserDetails;
import com.api.jaebichuri.auth.jwt.JwtUtil;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageDeliveryException;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE + 99)
public class StompInterceptor implements ChannelInterceptor {

    private static final String HEADER_ATTRIBUTE_NAME_AUTHORIZATION = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor headerAccessor = MessageHeaderAccessor
            .getAccessor(message, StompHeaderAccessor.class);

        StompCommand command = headerAccessor.getCommand();
        if (StompCommand.CONNECT.equals(command) || StompCommand.SEND.equals(command)) {
            String authorization = headerAccessor.getFirstNativeHeader(
                HEADER_ATTRIBUTE_NAME_AUTHORIZATION);

            validateToken(authorization, headerAccessor);
        }

        return message;
    }

    private void validateToken(String authorization, StompHeaderAccessor headerAccessor) {
        if (Objects.nonNull(authorization) && authorization.startsWith(TOKEN_PREFIX)) {
            String token = authorization.substring(TOKEN_PREFIX.length());

            log.info("jwt 토큰 : {}", token);

            try {
                jwtUtil.validateToken(token);
                setAuthentication(headerAccessor, token);
            } catch (Exception e) {
                throw new MessageDeliveryException("유효하지 않는 토큰입니다.");
            }
        } else {
            throw new MessageDeliveryException("토큰이 존재하지 않습니다.");
        }
    }

    private void setAuthentication(StompHeaderAccessor headerAccessor, String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(
            jwtUtil.extractClientId(token));

        CustomUserDetails customUserDetails = (CustomUserDetails) userDetails;

        Authentication authentication = new UsernamePasswordAuthenticationToken(
            customUserDetails.getMember(), null, userDetails.getAuthorities());

        headerAccessor.setUser(authentication);
    }
}