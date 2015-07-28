package org.sitenv.directvendortools.web.configuration;

import org.sitenv.directvendortools.web.configuration.security.AuthFailer;
import org.sitenv.directvendortools.web.configuration.security.AuthSuccess;
import org.sitenv.directvendortools.web.configuration.security.AuthenticationTokenProcessingFilter;
import org.sitenv.directvendortools.web.configuration.security.EntryPointUnauthorizedHandler;
import org.sitenv.directvendortools.web.services.UserService;
import org.sitenv.directvendortools.web.util.SaltedPasswordHashUtil;
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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	@Autowired
	private AuthFailer authFailer;
	@Autowired
	private AuthSuccess authSuccess;
	@Autowired
	private EntryPointUnauthorizedHandler unauthorizedHandler;
	@Autowired
	private UserService userDetailsService;
	
	@Bean(name="authenticationManager")
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
	       return super.authenticationManagerBean();
	}

	@Autowired
	public void configAuthBuilder(AuthenticationManagerBuilder builder) throws Exception {
		builder.userDetailsService(userDetailsService).passwordEncoder(new SaltedPasswordHashUtil());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.addFilterBefore(new AuthenticationTokenProcessingFilter(userDetailsService), UsernamePasswordAuthenticationFilter.class);
		
		http.authorizeRequests()
         .antMatchers(HttpMethod.GET,"/validatedirecttransporttestingservice/**").hasRole("USER")
		 .antMatchers(HttpMethod.POST,"/directtransporttestingservice").hasRole("USER")
		 .antMatchers(HttpMethod.PUT,"/updatedirecttransporttestingservice").hasRole("USER")
		 .antMatchers(HttpMethod.DELETE,"/deletedirecttransporttestingservice").hasRole("USER")
		 .antMatchers(HttpMethod.POST,"/uploadCert").hasRole("USER")
		 .antMatchers(HttpMethod.GET,"/deleteCert").hasRole("USER")
		 .anyRequest().permitAll();
		
		http.httpBasic().and().exceptionHandling()
			.authenticationEntryPoint(unauthorizedHandler).and().formLogin()
			.successHandler(authSuccess).failureHandler(authFailer);
			/*.and().addFilterAfter(new CsrfHeaderFilter(), CsrfFilter.class)
			.csrf().csrfTokenRepository(csrfTokenRepository());*/
		
		http.csrf().disable();
	}

	/*private CsrfTokenRepository csrfTokenRepository() {
		HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
		repository.setHeaderName("X-XSRF-TOKEN");
		return repository;
	}*/
}
