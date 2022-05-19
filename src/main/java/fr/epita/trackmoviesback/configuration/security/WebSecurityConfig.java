package fr.epita.trackmoviesback.configuration.security;

import fr.epita.trackmoviesback.configuration.security.jwt.JwtAuthenticationEntryPoint;
import fr.epita.trackmoviesback.configuration.security.jwt.JwtRequestFilter;
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

import javax.sql.DataSource;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, proxyTargetClass = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String UNAUTHENTICATED_WHITE_LIST[] = { "/trackmovies/v1/login", "/trackmovies/v1/utilisateur"};

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Override
    public void configure(final AuthenticationManagerBuilder auth) throws Exception {
        System.out.println("WebSecurityConfig - configure auth");
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        System.out.println("WebSecurityConfig - passwordEncoder()");
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        System.out.println("WebSecurityConfig - authenticationManagerBean()");
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(final HttpSecurity httpSecurity) throws Exception {
        // Add a filter to validate the tokens with every request
        System.out.println("WebSecurityConfig - configure http");
        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        httpSecurity
                .csrf().disable()
                .authorizeRequests()
                    .antMatchers("/trackmovies/v1/login").permitAll()
                    .antMatchers(HttpMethod.POST, "/trackmovies/v1/utilisateur").permitAll()
                // dont authenticate this authentication request
                //.authorizeRequests().antMatchers(UNAUTHENTICATED_WHITE_LIST).permitAll()
                // and authorize everybody create a customer
                //.antMatchers(HttpMethod.POST, "/customers").permitAll()
                // and authorize swagger-ui
                    .antMatchers("/v2/api-docs", "/configuration/**", "/swagger*/**", "/webjars/**").permitAll()
                // all other requests need to be authenticated
                    .anyRequest().authenticated()
                    .and()
                    .formLogin()
                    .and()
                // make sure we use stateless session; session won't be used to
                // store user's state.
                    .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint) //
                    .and()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}
