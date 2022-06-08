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
    public static final long JWT_TOKEN_VALIDITY_IN_SECOND = 60 * 60;// =1h

    private Boolean connectionUtilisateur = false;

    @Value("${jwt.secret}")
    private String jwtSecret;

    // retrieve username from jwt token
    public String getUsernameFromToken(final String token) {

        logger.debug("getUsernameFromToken(token : " + token);
        String usernameFromToken = getClaimFromToken(token, Claims::getSubject);
        logger.debug("getUsernameFromToken() result : usernameFromToken : " + usernameFromToken);

        return getClaimFromToken(token, Claims::getSubject);
    }

    // retrieve expiration date from jwt token
    /*public Date getExpirationDateFromToken(final String token) {

        logger.info("getExpirationDateFromToken(token : " + token);
        Date expirationDateFromToken = getClaimFromToken(token, Claims::getExpiration);
        logger.info("expirationDateFromToken : " + expirationDateFromToken);

        return getClaimFromToken(token, Claims::getExpiration);
    }
*/
    public <T> T getClaimFromToken(final String token, final Function<Claims, T> claimsResolver) {
        logger.debug("getClaimFromToken(token : {}, claimsResolver : {}",token ,claimsResolver);
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    // for retrieveing any information from token we will need the secret key
    private Claims getAllClaimsFromToken(final String token) {
        logger.debug("getAllClaimsFromToken(token : {}" , token);
        return Jwts.parser() //
                .setSigningKey(jwtSecret) //
                .parseClaimsJws(token) //
                .getBody();
    }
/*
    // check if the token has expired
    private Boolean isTokenExpired(final String token) {

        logger.info("isTokenExpired(token : " + token);

        final Date expiration = getExpirationDateFromToken(token);

        Boolean tokenExpired = expiration.before(new Date());
        logger.info("tokenExpired : " + tokenExpired);
        return expiration.before(new Date());
    }
*/

    /**
     * genere le token pour le user
     * @param userDetails user pour lequel on genere le token
     * @return token
     */
    public String generateToken(final UserDetails userDetails) {

        logger.debug("generateToken() : "
                + "  username : {}"
                + ", authorities : {}"
                + ", account non expired {}: "
                + ", account not locked {}: "
                + ", credentials not expired {}: "
                + ", enabled : {}",
                userDetails.getUsername(),userDetails.getAuthorities(),
                userDetails.isAccountNonExpired(),userDetails.isAccountNonLocked(),
                userDetails.isCredentialsNonExpired(),userDetails.isEnabled()
        );

        final Map<String, Object> claims = new HashMap<>();

        long now =System.currentTimeMillis();
        return Jwts.builder().setClaims(claims).setSubject(userDetails.getUsername()) //
                .setIssuedAt(new Date(now)) //
                .setExpiration(new Date(now + JWT_TOKEN_VALIDITY_IN_SECOND * 1000))
                .signWith(SignatureAlgorithm.HS512, jwtSecret) //
                .compact();
    }

    // while creating the token -
    // 1. Define claims of the token, like Issuer, Expiration, Subject, and the ID
    // 2. Sign the JWT using the HS512 algorithm and secret key.
    // 3. According to JWS Compact
    // Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
    // compaction of the JWT to a URL-safe string
    /*private String doGenerateToken(final Map<String, Object> claims, final String subject) {

        logger.info("doGenerateToken()");

        return Jwts.builder().setClaims(claims).setSubject(subject) //
                .setIssuedAt(new Date(System.currentTimeMillis())) //
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, jwtSecret) //
                .compact();
    }*/



    /**
     * valide le token : il appartient bien au user et est encore valide
     * @param token in the request
     * @param userDetails user of the DB
     * @return vrai si token valide, sinon faux
     */
    public Boolean validateToken(final String token, final UserDetails userDetails) {
        String beginTokenForLog=token.substring(0,20);
        logger.debug("generateToken() : "
                        + "  token : {}"
                        + "  username : {}"
                        + ", authorities : {}"
                        + ", account non expired {}: "
                        + ", account not locked {}: "
                        + ", credentials not expired {}: "
                        + ", enabled : {}",
                beginTokenForLog,
                userDetails.getUsername(),userDetails.getAuthorities(),
                userDetails.isAccountNonExpired(),userDetails.isAccountNonLocked(),
                userDetails.isCredentialsNonExpired(),userDetails.isEnabled()
        );

        final String username = getUsernameFromToken(token);
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        boolean isTokenExpired = claims.getExpiration().before(new Date());
        return (username.equals(userDetails.getUsername()) && !isTokenExpired);
    }

    public void setStatutUtilisateur (Boolean statutUtilisateur) {
        this.connectionUtilisateur = statutUtilisateur;
    }

    public Boolean getStatutUtilisateur () {
        return this.connectionUtilisateur;
    }
}
