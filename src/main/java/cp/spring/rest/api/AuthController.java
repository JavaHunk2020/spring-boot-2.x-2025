package cp.spring.rest.api;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
@RequestMapping("/v1")
public class AuthController {
	
	
	@Value("${jwt.secret.key:ABUE87%&$&##@)@(&@*^@^@@@}")
	private String jwtSecret;

	private int jwtExpirationMs = 1800000; //ms


	@PostMapping("/slogin")
	public ResponseEntity<Map<String, Object>> validateUser(@RequestBody Map<String, String> input) {
		if (input.get("username").equals("nagen") && input.get("password").equals("admin")) {
			GrantedAuthority authority1 = new SimpleGrantedAuthority("ADMIN");
		       GrantedAuthority authority2 = new SimpleGrantedAuthority("SUPER ADMIN");
		       List<GrantedAuthority> roleList=List.of(authority1,authority2);
				
		    	Map<String, Object> claims = new HashMap<>();
				claims.put("scopes", roleList.stream().map(ga->ga.getAuthority()).collect(Collectors.toList()));
				claims.put("company", "AbcTech");

				String jwtToken= 
						Jwts.builder()
						.setSubject((input.get("username")))
						.addClaims(claims)
						.setIssuedAt(new Date())
						.setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
						.signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
				
			return new ResponseEntity<>(Map.of("message", "Cool! username and password are correct!","Authorization",jwtToken), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(Map.of("message", "Sorry! username and password are not correct!"),
					HttpStatus.UNAUTHORIZED);
		}
	}

}
