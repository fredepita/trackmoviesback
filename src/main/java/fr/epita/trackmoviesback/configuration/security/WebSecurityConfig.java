package fr.epita.trackmoviesback.configuration.security;

import fr.epita.trackmoviesback.configuration.security.jwt.JwtAuthenticationEntryPoint;
import fr.epita.trackmoviesback.configuration.security.jwt.JwtRequestFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, proxyTargetClass = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private static Logger logger = LoggerFactory.getLogger(WebSecurityConfig.class);

    private static final String UNAUTHENTICATED_WHITE_LIST[] = {"/trackmovies/v1/login", "/trackmovies/v1/utilisateur"};

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Override
    public void configure(final AuthenticationManagerBuilder auth) throws Exception {
        logger.info("configure(auth : {}" , auth.toString());
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        logger.debug("passwordEncoder()");
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {

        logger.info("authenticationManagerBean()");

        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(final HttpSecurity httpSecurity) throws Exception {
        logger.debug("configure(httpSecurity : " + httpSecurity.toString());

        //ajout d'un filtre qui valide le token pour toutes les requetes
        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        httpSecurity
                .csrf().disable()//on peut desactivé car on utilise une authentification par token et le token sera pas accessible depuis un autre domaine
                //-->  Autorisation login
                .authorizeRequests()
                .antMatchers("/trackmovies/v1/login").permitAll()
                .antMatchers("/trackmovies/v1/utilisateur").permitAll()
                //-->  Autorisation requêtes HTTP Options
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                //-->  Autorisation swagger-ui
                .antMatchers("/v2/api-docs", "/configuration/**", "/swagger*/**", "/webjars/**").permitAll()
                //--> Autorisations en fonction du rôle
                .antMatchers(HttpMethod.GET, "/trackmovies/v1/genres").hasRole("USER")
                .antMatchers(HttpMethod.GET, "/trackmovies/v1/statuts_visionnage").hasRole("USER")
                .antMatchers(HttpMethod.GET, "/trackmovies/v1/mes_oeuvres").hasRole("USER")
                .antMatchers(HttpMethod.GET, "/trackmovies/v1/mes_oeuvres/{id}").hasRole("USER")
                .antMatchers(HttpMethod.DELETE, "/trackmovies/v1/oeuvre/{id}").hasRole("USER")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .and()
                .logout()
                .logoutSuccessUrl("/trackmovies/v1/")
                .and()
                // make sure we use stateless session; session won't be used to
                // store user's state.
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint) //
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}
