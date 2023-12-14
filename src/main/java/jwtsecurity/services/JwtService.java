package jwtsecurity.services;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
	
	private static final String SECRET_KEY = 
			"B65B0A183A9517C4B339D067A38CB96B02397F14DC2D2A2BA15AD516707370CD";
	
	public String generateToken(UserDetails userDetails) {
		return generateToken(new HashMap<>(), userDetails);
	}
	
	public String generateToken(
			Map<String, Object> extraClaims, 
			UserDetails userDetails) {
		return Jwts
				.builder()
				.claims(extraClaims)								// вместо .setClaims()
				.subject(userDetails.getUsername())					// вместо .setSubject()
				.issuedAt(new Date(System.currentTimeMillis()))		// вместо .setIssuedAt()
				.expiration(new Date(System.currentTimeMillis() 	// вместо .setExpiration()
											+ 1000 * 60 * 2))		// !!! плюс 2 мин. к текущему времени
				.signWith(getSignInKey(), Jwts.SIG.HS256)			// вторым параметром вместо SignatureAlgorithm.HS256 передается Jwts.SIG.HS256
				.compact();
	}
	
	public boolean isTokenValid(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
	}
	
	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}
	
	private Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}
	
	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}
	
	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}
	
	private Claims extractAllClaims(String token) {
		return Jwts
				.parser()						// вместо .parserBuilder()
				.verifyWith(getSignInKey())		// вместо .setSigningKey()
				.build()
				.parseSignedClaims(token)		// вместо .parseClaimsJws()
				.getPayload();					// вместо .getBody()
	}

	private SecretKey getSignInKey() {
		byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
		return Keys.hmacShaKeyFor(keyBytes);
	}
}
