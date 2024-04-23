package com.sarop.saropbackend.config;


import com.sarop.saropbackend.common.TokenClaims;
import com.sarop.saropbackend.common.Util;
import com.sarop.saropbackend.user.model.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.security.Key;
import java.util.*;
import java.util.function.Function;


@Service
public class JWTService {

    @Value("${application.security.jwt.expiration}")
    private long TOKEN_VALIDITY;

    @Value("${application.security.jwt.refresh-token.expiration}")
    private long REFRESH_TOKEN_EXPIRATION;
    @Value("${application.security.jwt.secret-key}")
    private String SIGNING_KEY;
    public String extractUsername(String token) {
        return extractClaim(token,Claims::getSubject);
    }
    public Claims extractAllClaims(String token){
        return Jwts.parserBuilder().setSigningKey(getSigninKey()).build().parseClaimsJws(token).getBody();
    }

    public <T> T extractClaim(String token, Function<Claims,T> claimResolver){
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    public String generateToken(User user){
        HashMap<String,Object> claim  = new HashMap<>();



        claim.put(TokenClaims.USER_ID.getValue(),user.getId());
        claim.put(TokenClaims.NAME.getValue(), user.getName());
        claim.put(TokenClaims.MAIL.getValue(), user.getEmail());
        claim.put(TokenClaims.ROLE_NAME.getValue(),user.getRole().name());


        return generateToken(claim, user);
    }


    public String generateToken(Map<String,Object> extraClaims, User user) {
        return buildToken(extraClaims, user, TOKEN_VALIDITY);

    }

    public boolean isTokenValid(String token,String email){
        final String username = extractUsername(token);
        return username.equals(email) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Key getSigninKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SIGNING_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateRefreshToken(
            User user
    ) {
        HashMap<String,Object> claim  = new HashMap<>();
        claim.put(TokenClaims.ROLE_NAME.getValue(), user.getRole().name());

        return buildToken(claim, user, REFRESH_TOKEN_EXPIRATION );
    }

    private String buildToken(
            Map<String, Object> extraClaims,
            User user,
            long expiration
    ) {

        return Jwts
                .builder()
                .setHeaderParam(TokenClaims.JWT_ID.getValue(), Util.generateUUID())
                .setHeaderParam(TokenClaims.TYPE.getValue(), TokenClaims.TYPE_VAL.getValue())
                .setClaims(extraClaims)
                .setSubject(user.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .setIssuer(TokenClaims.ISSUER.getValue())
                .signWith(getSigninKey(), SignatureAlgorithm.HS256)
                .compact();

    }


    public void verifyAndValidate(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigninKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (MalformedJwtException | ExpiredJwtException exception) {
            throw new RuntimeException(token, exception);
        }
    }

}
