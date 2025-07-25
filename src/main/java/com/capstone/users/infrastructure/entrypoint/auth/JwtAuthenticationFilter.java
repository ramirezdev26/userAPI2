package com.capstone.users.infrastructure.entrypoint.auth;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
  private final JwtService jwtService;
  private final UserDetailsService userDetailsService;

  /**
   * Filters the HTTP request and performs authentication based on the JWT token.
   *
   * @param  request            the HTTP servlet request
   * @param  response           the HTTP servlet response
   * @param  filterChain        the filter chain
   * @throws ServletException   if an exception occurs during the filter chain processing
   * @throws IOException        if an I/O error occurs
   */
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    String requestURI = request.getRequestURI();

    if (requestURI.startsWith("/swagger-ui") ||
            requestURI.startsWith("/v3/api-docs") ||
            requestURI.startsWith("/swagger-ui.html")) {
      filterChain.doFilter(request, response);
      return;
    }

    final String token = getTokenFromRequest(request);
    final String username;
    if (token == null) {
      response.setStatus(401);
      filterChain.doFilter(request, response);
      return;
    }
      try {
          username = jwtService.getUsernameFromToken(token);
      } catch (Exception e) {
        response.setStatus(401);
        filterChain.doFilter(request, response);
        return;
      }
      if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
      UserDetails userDetails = userDetailsService.loadUserByUsername(username);
      if (jwtService.isTokenValid(token, userDetails)) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
            userDetails,
            null,
            userDetails.getAuthorities());
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);
      }
    }
    filterChain.doFilter(request, response);
  }

  /**
   * Retrieves the JWT token from the HTTP request header.
   *
   * @param  request  the HTTP servlet request object
   * @return          the JWT token extracted from the request header, or null if not found
   */
  private String getTokenFromRequest(HttpServletRequest request) {
    final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

    if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
      return authHeader.substring(7);
    }
    return null;
  }
}
