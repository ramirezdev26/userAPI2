package com.capstone.users.infrastructure.entrypoint.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
  private static final String SECRET_KEY = "VGhpcyBpcyBhIHZhbGlkIHNlY3JldCBrZXkgZm9yIEpXVCBzaWduaW5nIHdpdGggSFMyNTYhISEh";

  public String getToken(UserDetails user) {
    return getToken(new HashMap<>(), user);
  }

  /**
   * Generates a JWT token with the given extra claims and user details.
   *
   * @param extraClaims   a map of additional claims to include in the token
   * @param user           the user details for the token
   * @return                the generated JWT token
   */
  private String getToken(Map<String, Object> extraClaims, UserDetails user) {
    if(!(user instanceof UserAuth userAuth)) {
      throw new IllegalArgumentException("Invalid users details");
    }
    extraClaims.put("userId", userAuth.getId());

    return Jwts
        .builder()
        .setClaims(extraClaims)
        .setSubject(user.getUsername())
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
        .signWith(getKey(), SignatureAlgorithm.HS256)
        .compact();
  }

  /**
   * Returns a Key object that can be used for HMAC-SHA encryption.
   *
   * @return a Key object generated from the base64-decoded SECRET_KEY
   */
  private Key getKey() {
    byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
    return Keys.hmacShaKeyFor(keyBytes);
  }

  /**
   * Retrieves the username from a JWT token.
   *
   * @param  token  the JWT token to extract the username from
   * @return        the username extracted from the token
   */
  public String getUsernameFromToken(String token) {
    return getClaim(token, Claims::getSubject);
  }

  /**
   * Checks if a JWT token is valid for a given user.
   *
   * @param  token       the JWT token to validate
   * @param  userDetails the user details to compare against the token
   * @return             true if the token is valid and belongs to the user, false otherwise
   */
  public boolean isTokenValid(String token, UserDetails userDetails) {
    final String username = getUsernameFromToken(token);
    return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
  }

  /**
   * Parses the given JWT token and returns all its claims.
   *
   * @param  token  the JWT token to parse
   * @return        the claims extracted from the token
   */
  private Claims getAllClaims(String token) {
    return Jwts
        .parserBuilder()
        .setSigningKey(getKey())
        .build()
        .parseClaimsJws(token)
        .getBody();
  }

  /**
   * Retrieves a specific claim from a JWT token using a provided function.
   *
   * @param  token         the JWT token to extract the claim from
   * @param  claimsResolver a function that takes the Claims object and returns the desired claim
   * @return                the value of the specified claim
   */
  public <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = getAllClaims(token);
    return claimsResolver.apply(claims);
  }

  /**
   * Retrieves the expiration date from a JWT token.
   *
   * @param  token  the JWT token to extract the expiration date from
   * @return        the expiration date extracted from the token
   */
  private Date getExpiration(String token) {
    return getClaim(token, Claims::getExpiration);
  }

  /**
   * Checks if a JWT token has expired by comparing its expiration date with the current date.
   *
   * @param  token  the JWT token to check for expiration
   * @return        true if the token has expired, false otherwise
   */
  private boolean isTokenExpired(String token) {
    return getExpiration(token).before(new Date());
  }

}
