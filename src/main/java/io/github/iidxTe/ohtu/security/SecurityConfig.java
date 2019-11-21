package io.github.iidxTe.ohtu.security;


import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Spring security configuration.
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	/**
	 * Our passwords are encoded with this. The encoding used is stored as
	 * prefix of the password. At time of writing, Spring Security used Bcrypt
	 * for encoding.
	 */
	private final PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

	@Autowired
	public void initialize(AuthenticationManagerBuilder authManager, DataSource db) throws Exception {
		// Provide a single test account; we'll probably move to database authentication later
		// We'll need a persistent database (e.g. H2 on file) and a way to create accounts for that
		UserDetails testUser = User.withUsername("test").password(passwordEncoder.encode("test")).roles("USER").build();
		authManager.inMemoryAuthentication().withUser(testUser);
	}
}
