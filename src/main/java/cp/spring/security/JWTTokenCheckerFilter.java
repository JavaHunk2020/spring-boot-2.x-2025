package cp.spring.security;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

public class JWTTokenCheckerFilter extends OncePerRequestFilter {

	private static final Logger logger = LoggerFactory.getLogger(JWTTokenCheckerFilter.class);

	@Value("${jwt.secret.key:ABUE87%&$&##@)@(&@*^@^@@@}")
	private String jwtSecret;

	public boolean validateJwtToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
			return true;
		} catch (SignatureException e) {
			logger.error("Invalid JWT signature: {}", e.getMessage());
		} catch (MalformedJwtException e) {
			logger.error("Invalid JWT token: {}", e.getMessage());
		} catch (ExpiredJwtException e) {
			logger.error("JWT token is expired: {}", e.getMessage());
		} catch (UnsupportedJwtException e) {
			logger.error("JWT token is unsupported: {}", e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.error("JWT claims string is empty: {}", e.getMessage());
		}
		return false;
	}

	public String getUserNameFromJwtToken(String token) {
		return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
	}

	public List<String> getRoleFromJwtToken(String token) {
		return (List<String>) Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().get("scopes");
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
	    	String headerAuth = request.getHeader("Authorization");
			// VALIDATE THE TOKEN
			if (headerAuth!=null && StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
				headerAuth = headerAuth.substring(7, headerAuth.length());
				if (validateJwtToken(headerAuth)) {
					String username = this.getUserNameFromJwtToken(headerAuth);
					List<String> roles = this.getRoleFromJwtToken(headerAuth);
					List<GrantedAuthority> authorities = roles.stream().map(r -> new SimpleGrantedAuthority(r))
							.collect(Collectors.toList());

					// Spring security autheticated you
					UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(username, null,
							authorities);

					// SPRING SECURITY VALIDATE AND SAYS GO AHEAD
					// authentication.setDetails(new
					// WebAuthenticationDetailsSource().buildDetails(request));
					// SecutiryContextHolder
					//THIS LINE IS MAGIC
					SecurityContextHolder.getContext().setAuthentication(auth);
				}
			}
			
			// GO AHEAD - IF RESOURCE IS PUBLIC
			filterChain.doFilter(request, response);
			System.out.println("headerAuth = " + headerAuth);
	}
}
