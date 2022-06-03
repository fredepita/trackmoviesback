package fr.epita.trackmoviesback.configuration.security.jwt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

public class JwtResponse implements Serializable {

    private static Logger logger = LoggerFactory.getLogger(JwtResponse.class);

    private static final long serialVersionUID = 1L;

    private final String jwtToken;

    public JwtResponse(final String jwtToken) {

        logger.info("JwtResponse(jwtToken : " + jwtToken);

        this.jwtToken = jwtToken;
    }

    public String getToken() {

        logger.info("getToken()");

        return jwtToken;
    }
}
