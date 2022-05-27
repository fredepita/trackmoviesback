package fr.epita.trackmoviesback.configuration.security.jwt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/trackmovies/v1")
public class JwtAuthenticationController {

    private static Logger logger = LoggerFactory.getLogger(JwtAuthenticationController.class);
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @PostMapping(value = "/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody final JwtRequest authenticationRequest)
            throws Exception {

        logger.debug("createAuthenticationToken(authenticationRequest : " + authenticationRequest.toString());

        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails);

        logger.debug("token : " + token);

        return ResponseEntity.ok(new JwtResponse(token));
    }

    private void authenticate(final String username, final String password) throws Exception {
        try {
            logger.debug("authenticate(username : " + username + ", password : " + password);

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        }
        catch (final DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        }
        catch (final BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
