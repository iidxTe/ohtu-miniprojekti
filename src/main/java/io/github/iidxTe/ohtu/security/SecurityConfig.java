package io.github.iidxTe.ohtu.security;


import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Spring security configuration.
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    public void initialize(AuthenticationManagerBuilder authManager, DataSource db) throws Exception {
        // Use JDBC database for users; see DatabaseDao for more details
        authManager.jdbcAuthentication()
                .dataSource(db)
                .usersByUsernameQuery("SELECT name, password, 'true' FROM users WHERE name=?")
                .authoritiesByUsernameQuery("SELECT name, 'ROLE_USER' FROM users WHERE name=?");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .mvcMatchers("/register").anonymous() // Creating users
                .anyRequest().authenticated() // Everything else is only for users
                .and()
                .formLogin().loginPage("/login").permitAll() // Unauthorized requests redirect here
                .and()
                .logout().permitAll(); // Enable default logout mechanism
                

    }
}
