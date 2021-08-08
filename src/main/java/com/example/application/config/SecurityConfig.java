package com.example.application.config;

import com.example.application.service.MyUserDetailService;
import com.example.application.util.CustomRequestCache;
import com.example.application.util.SecurityUtils;
import com.example.application.views.login.LoginView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private MyUserDetailService myUserDetailService;

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setPasswordEncoder(encoder);
        auth.setUserDetailsService(myUserDetailService);
        return auth;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    // private static final String LOGIN_PROCESSING_URL = "/login";
    // private static final String LOGIN_FAILURE_URL = "/login?error"; // 
    // private static final String LOGIN_URL = "/login";
    private static final String LOGOUT_SUCCESS_URL = "/login";

    @Override
    public void configure(WebSecurity web) throws Exception {
        // TODO Auto-generated method stub
        web.ignoring().antMatchers(
                // Vaadin Flow static resources // 
                "/VAADIN/**",

                // the standard favicon URI
                "/favicon.ico",

                // the robots exclusion standard
                "/robots.txt",

                // web application manifest // 
                "/manifest.webmanifest", "/sw.js", "/offline-page.html",

                // (development mode) static resources // 
                "/frontend/**",

                // (development mode) webjars // 
                "/webjars/**",

                // (production mode) static resources // 
                "/frontend-es5/**", "/frontend-es6/**", "/h2-console/**");
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    // @Bean
    // @Override
    // public UserDetailsService userDetailsService() {
    //     // typical logged in user with some privileges
    //     UserDetails normalUser = User.withUsername("user").password("{noop}user").roles("USER").build();

    //     // admin user with all privileges
    //     UserDetails adminUser = User.withUsername("admin").password("{noop}admin").roles("ADMIN").build();

    //     return new InMemoryUserDetailsManager(normalUser, adminUser);
    // }

    @Bean
    public CustomRequestCache requestCache() {
        return new CustomRequestCache();
    }

    /**
     * Require login to access internal pages and configure login form.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Not using Spring CSRF here to be able to use plain HTML for the login page
        http.csrf().disable()

                // Register our CustomRequestCache that saves unauthorized access attempts, so
                // the user is redirected after login.
                .requestCache().requestCache(new CustomRequestCache())

                // Restrict access to our application.
                .and().authorizeRequests()

                //allow h2-console

                .antMatchers("/h2-console/**").permitAll()

                // Allow all flow internal requests.
                .requestMatchers(SecurityUtils::isFrameworkInternalRequest).permitAll()

                // Allow all requests by logged in users.
                .anyRequest().authenticated()

                // Configure the login page.
                .and().formLogin().loginPage("/" + LoginView.ROUTE).permitAll()

                // Configure logout
                .and().logout().logoutSuccessUrl(LOGOUT_SUCCESS_URL);
    }

}
