package com.backbone.phalanx.authentication.configuration;

import com.backbone.phalanx.user.model.User;
import com.backbone.phalanx.user.service.UserService;
import com.backbone.phalanx.authentication.service.implementation.JwtService;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
@Log4j2
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    public static final String BEARER_PREFIX = "Bearer ";
    private final JwtService jwtService;
    private final UserService userService;
    private static final AntPathMatcher pathMatcher = new AntPathMatcher();
    private static final List<String> EXCLUDED_PATHS = List.of(
            "/api/public/",
            "/api/public/**",
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/api/user/forgot-password/send-code",
            "/api/user/forgot-password/verify-code",
            "/api/user/forgot-password/reset"
    );

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        boolean skip =  EXCLUDED_PATHS.stream().anyMatch(pattern -> pathMatcher.match(pattern, path));
        log.info("Should not filter: {}", skip);
        return skip;
    }

    @Override
    protected void doFilterInternal(
            @NotNull HttpServletRequest request,
            @NotNull HttpServletResponse response,
            @NotNull FilterChain filterChain
    ) throws ServletException, IOException {

        if (shouldNotFilter(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        log.info("Should filter: {}", request.getServletPath());

        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.isEmpty(authHeader) || !authHeader.startsWith("Bearer ")) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing Authorization header");
            return;
        }

        log.info("Authorization header: {}", authHeader);

        String jwt = authHeader.substring(BEARER_PREFIX.length());
        String email = jwtService.extractEmail(jwt);

        if (StringUtils.isNotEmpty(email) && SecurityContextHolder.getContext().getAuthentication() == null) {
            User appUser = userService.loadUserByUsername(email);

            if (jwtService.isTokenValid(jwt, appUser)) {
                SecurityContext context = SecurityContextHolder.createEmptyContext();

                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        appUser, null, appUser.getAuthorities()
                );

                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                context.setAuthentication(authenticationToken);
                SecurityContextHolder.setContext(context);
            }
        }

        filterChain.doFilter(request, response);
    }
}


