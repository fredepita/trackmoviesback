package fr.epita.trackmoviesback.configuration.security.jwt;

import java.io.Serializable;

public class JwtResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String jwtToken;

    public JwtResponse(final String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public String getToken() {
        return jwtToken;
    }
}
