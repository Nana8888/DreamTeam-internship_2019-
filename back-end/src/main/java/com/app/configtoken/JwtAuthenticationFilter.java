package com.app.configtoken;

import com.app.entities.User;
import com.app.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

import static com.app.configtoken.Constants.HEADER_STRING;
import static com.app.configtoken.Constants.TOKEN_PREFIX;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

   private JwtTokenProvider tokenProvider;
   private CustomUserDetailsService customUserDetailsService;

   @Autowired
   public JwtAuthenticationFilter(JwtTokenProvider tokenProvider, CustomUserDetailsService customUserDetailsService) {
      this.tokenProvider = tokenProvider;
      this.customUserDetailsService = customUserDetailsService;
   }

   @Override
   protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                   FilterChain filterChain) throws ServletException, IOException {
      try {
         String jwt = getJWTFromRequest(httpServletRequest);

         if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
            Long userId = tokenProvider.getUserIdFromJWT(jwt);
            User userDetails = customUserDetailsService.loadUserById(userId);

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userDetails, null, Collections.emptyList());

            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
            SecurityContextHolder.getContext().setAuthentication(authentication);
         }
      }
      catch (Exception ex) {
         logger.error("Could not set user authentication in security context", ex);
      }
      filterChain.doFilter(httpServletRequest, httpServletResponse);
   }

   private String getJWTFromRequest(HttpServletRequest request) {
      String bearerToken = request.getHeader(HEADER_STRING);
      if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(TOKEN_PREFIX)) {
         return bearerToken.substring(7, bearerToken.length());
      }
      return null;
   }
}
