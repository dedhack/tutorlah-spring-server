package izhar.tutorlah.server.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import izhar.tutorlah.server.models.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    public String extractUsername(String token) {

        return extractClaim(token, Claims::getSubject);
    }
    // generate token from user details only
    public String generateToken(User user){
        return generateToken(new HashMap<>(), user);
    }

    // generate token with extra claims
    public String generateToken(
            Map<String, Object> extraClaims,// for passing additional details like authorities etc
            User user
    ){
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(user.getUsername()) // this is passing our email, which in Spring the unique item detail is Username
                .claim("authorities", user.getAuthorities())
                .claim("firstname", user.getFirstname())
                .claim("lastname", user.getLastname())
                .claim("userId", user.getId())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24)) // valid for 24 hours before expriing
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact(); // to finally generate and return the token

    }

    // check validity of token
    public boolean isTokenValid(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }


    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SecurityConstants.JWT_SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
