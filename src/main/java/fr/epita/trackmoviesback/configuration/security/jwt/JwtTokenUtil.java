package fr.epita.trackmoviesback.configuration.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenUtil {

    private static Logger logger = LoggerFactory.getLogger(JwtTokenUtil.class);
    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

    @Value("${jwt.secret}")
    private String secret;

    // retrieve username from jwt token
    public String getUsernameFromToken(final String token) {

        logger.debug("getUsernameFromToken(token : " + token);

        return getClaimFromToken(token, Claims::getSubject);
    }

    // retrieve expiration date from jwt token
    public Date getExpirationDateFromToken(final String token) {

        logger.debug("getExpirationDateFromToken(token : " + token);

        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(final String token, final Function<Claims, T> claimsResolver) {

        logger.debug("getClaimFromToken(token : " + token + ", claimsResolver : " + claimsResolver.toString());

        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    // for retrieveing any information from token we will need the secret key
    private Claims getAllClaimsFromToken(final String token) {

        logger.debug("getAllClaimsFromToken(token : " + token);

        return Jwts.parser() //
                .setSigningKey(secret) //
                .parseClaimsJws(token) //
                .getBody();
    }

    // check if the token has expired
    private Boolean isTokenExpired(final String token) {

        logger.debug("isTokenExpired(token : " + token);

        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    // generate token for user
    public String generateToken(final UserDetails userDetails) {

        logger.debug("generateToken : " + userDetails.toString());

        final Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername());
    }

    // while creating the token -
    // 1. Define claims of the token, like Issuer, Expiration, Subject, and the ID
    // 2. Sign the JWT using the HS512 algorithm and secret key.
    // 3. According to JWS Compact
    // Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
    // compaction of the JWT to a URL-safe string
    private String doGenerateToken(final Map<String, Object> claims, final String subject) {

        logger.debug("doGenerateToken : claims : " + claims.toString() + ", subject : " + subject.toString());

        return Jwts.builder().setClaims(claims).setSubject(subject) //
                .setIssuedAt(new Date(System.currentTimeMillis())) //
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, secret) //
                .compact();
    }

    // validate token
    public Boolean validateToken(final String token, final UserDetails userDetails) {

        logger.debug("validateToken(token : " + token + ", userDetails : " + userDetails.toString());

        final String username = getUsernameFromToken(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }
}
