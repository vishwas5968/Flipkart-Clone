package com.shopping.flipkart.security;

import com.shopping.flipkart.entity.AccessToken;
import com.shopping.flipkart.repo.AccessTokenRepo;
import com.shopping.flipkart.repo.RefreshTokenRepo;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Slf4j
@AllArgsConstructor
@Component
public class JwtFilter extends OncePerRequestFilter {

    private AccessTokenRepo accessTokenRepo;
    private RefreshTokenRepo refreshTokenRepo;
    private JwtService jwtService;
    private CustomUserDetailsService userDetailsService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String rt = null;
        String at = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("rt")) {
                    rt = cookie.getValue();
                }
                if (cookie.getName().equals("at")) {
                    at = cookie.getValue();
                }
            }
        }
        if ( accessTokenRepo.existsByTokenAndIsBlocked(at,true) || refreshTokenRepo.existsByTokenAndIsBlocked(rt,true)) {
            throw new RuntimeException("Unauthorized ");
        }

        if (at != null && rt != null) {
            String username = null;
            AccessToken accessToken = accessTokenRepo.findByTokenAndIsBlocked(at, false).get();
            if (accessToken == null)
                throw new RuntimeException("Failed to Authenticate");
            else {
                username = jwtService.extractUsername(at);
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                //Sets authenticationToken inside SecurityContext(which is present in SecurityContextHolder)
                log.info("Authentication Completed");
                filterChain.doFilter(request, response);
                //Passes the req and res to all the next filters
            }
        }
    }

}
