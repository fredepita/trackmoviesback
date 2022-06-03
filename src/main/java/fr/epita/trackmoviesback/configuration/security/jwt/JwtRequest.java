package fr.epita.trackmoviesback.configuration.security.jwt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

public class JwtRequest implements Serializable {

    private static Logger logger = LoggerFactory.getLogger(JwtRequest.class);

    private static final long serialVersionUID = 1L;

    private String username;

    private String password;

    // need default constructor for JSON Parsing
    public JwtRequest() {
    }

    public JwtRequest(final String username, final String password) {
        setUsername(username);
        setPassword(password);
    }

    public String getUsername() {
        logger.info("getUsername()");
        return this.username;
    }

    public void setUsername(final String username) {

        logger.info("setUsername(username : " + username + ")");
        this.username = username;
    }

    public String getPassword() {
        logger.info("getPassword()");
        return this.password;
    }

    public void setPassword(final String password) {

        logger.info("setPassword(password : " + password + ")");
        this.password = password;
    }

    @Override
    public String toString() {
        return "JwtRequest{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
