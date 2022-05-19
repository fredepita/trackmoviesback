package fr.epita.trackmoviesback.configuration.security.jwt;

import java.io.Serializable;

public class JwtRequest implements Serializable {

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
        System.out.println("getUsername()");
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public String getPassword() {
        System.out.println("getPassword()");
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }
}
