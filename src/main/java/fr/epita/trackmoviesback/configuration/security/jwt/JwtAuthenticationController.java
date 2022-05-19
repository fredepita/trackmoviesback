package fr.epita.trackmoviesback.configuration.security.jwt;

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

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenManager jwtTokenManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @PostMapping(value = "/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody final JwtRequest authenticationRequest)
            throws Exception {
        System.out.println("On est dans le @PostMapping /login !");
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        System.out.println("Apres authenticate !");
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        System.out.println("userDetails.getUsername() : " + userDetails.getUsername() + "userDetails.getPassword()) : " + userDetails.getPassword());
        final String token = jwtTokenManager.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));
    }

    private void authenticate(final String username, final String password) throws Exception {
        try {
            System.out.println("Dans authenticate !");
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            System.out.println("Dans authenticate après!");
        } catch (final DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (final BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
