package fr.epita.trackmoviesback.exposition;

import fr.epita.trackmoviesback.dto.JwtRequestDto;
import fr.epita.trackmoviesback.configuration.security.jwt.JwtResponse;
import fr.epita.trackmoviesback.configuration.security.jwt.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
    public ResponseEntity<?> createAuthenticationToken(@RequestBody final JwtRequestDto authenticationRequest)
            throws Exception {
        try {
            logger.info("createAuthenticationToken(authenticationRequest : " + authenticationRequest.toString());
            // Authentification avec les données en entrée
            authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
            logger.debug("après authenticate");

            final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

            final String token = jwtTokenUtil.generateToken(userDetails);
            jwtTokenUtil.setStatutUtilisateur(true);
            logger.info("token : {}", token);

            return ResponseEntity.ok(new JwtResponse(token));
        } catch (Exception e) {
            logger.error("Echec du login de {} : {}",authenticationRequest.getUsername(), e.getMessage());
            return new ResponseEntity("Echec du login", HttpStatus.UNAUTHORIZED);
        }
    }


    @GetMapping(value = "/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        logger.info("request : {} - {} - {}",request.getAuthType(),request.getContextPath(),request.getQueryString());
        logger.info("response : {} - {} ",response.getHeaderNames(),response.getStatus());

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null) {
            //logger.info("auth avant : " + auth.getName() + auth.getAuthorities() + auth.getCredentials() + auth.getDetails() + auth.getPrincipal() + auth.isAuthenticated());
            new SecurityContextLogoutHandler().logout(request, response, auth);
            jwtTokenUtil.setStatutUtilisateur(false);
            //logger.info("auth après : " + auth.getName() + auth.getAuthorities() + auth.getCredentials() + auth.getDetails() + auth.getPrincipal() + auth.isAuthenticated());
        }
        return ResponseEntity.ok("logout OK");
    }


    private void authenticate(final String username, final String password) throws Exception {
        try {
            logger.info("authenticate() start - username : {}", username);
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
