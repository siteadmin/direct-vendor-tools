package org.sitenv.directvendortools.web.controllers;

import java.util.List;

import org.sitenv.directvendortools.web.configuration.security.TokenUtils;
import org.sitenv.directvendortools.web.entities.DirectTransportTestingService;
import org.sitenv.directvendortools.web.entities.User;
import org.sitenv.directvendortools.web.services.DirectServicesService;
import org.sitenv.directvendortools.web.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DirectServicesController {
	@Autowired
	DirectServicesService directService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	@Qualifier("authenticationManager")
	private AuthenticationManager authManager;
	
	@RequestMapping(value = "/directtransporttestingservice", method = RequestMethod.GET)
	public List<DirectTransportTestingService> getDirectTransportTestingServices(
			@RequestParam(value = "useremail", required = false) String useremail) {
		if (useremail != null) {
			return directService.findByEmailAddress(useremail.toUpperCase());
		} else {
			return directService.findAllRegisteredServices();
		}
	}

	@RequestMapping(value = "/directtransporttestingservice", method = RequestMethod.POST)
	public DirectTransportTestingService saveDirectTransportTestingService(@RequestBody DirectTransportTestingService service) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			UserDetails details = (UserDetails) principal;
			service.setUserEmailAddress(details.getUsername());
		}
		return directService.save(service);
	}
	
	@RequestMapping(value = "/updatedirecttransporttestingservice", method = RequestMethod.PUT)
	public boolean updateDirectTransportTestingService(@RequestBody DirectTransportTestingService service) {
		return directService.update(service) != null ? true : false;
	}
	
	@RequestMapping(value = "/deletedirecttransporttestingservice", method = RequestMethod.DELETE)
	public boolean deleteDirectTransportTestingService(@RequestBody DirectTransportTestingService service) {
		directService.delete(service);
		return true;
	}
	
	@RequestMapping(value = "/validatedirecttransporttestingservice", method = RequestMethod.GET)
	public boolean validateDirectTransportTestingService(@RequestParam(value = "directEmailAddress", required = false) String directEmailAddress) {
		
		return directService.validateDirecteEmailAddress(directEmailAddress.toUpperCase());
	}

	@RequestMapping(value = "/usersignup", method = RequestMethod.POST)
	public User saveUser(@RequestBody User user) {
		return userService.save(user);
	}
	
	@RequestMapping(value = "/checkUserName", method = RequestMethod.GET)
	public boolean checkUserName(@RequestParam(value = "username", required = false) String username) {
		return userService.checkUserNameAvailability(username);
	}
	
	@RequestMapping(value = "/userlogin", method = RequestMethod.POST)
	public String login(@RequestBody User user) {
		
		UsernamePasswordAuthenticationToken authenticationToken =
				new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
		
		Authentication authentication = authManager.authenticate(authenticationToken);
		SecurityContextHolder.getContext().setAuthentication(authentication);

		/*
		 * Reload user as password of authentication principal will be null after authorization and
		 * password is needed for token generation
		 */
		UserDetails userDetails = this.userService.loadUserByUsername(user.getUsername());

		return TokenUtils.createToken(userDetails);
	}
}
