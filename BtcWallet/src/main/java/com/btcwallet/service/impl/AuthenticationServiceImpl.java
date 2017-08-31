package com.btcwallet.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.btcwallet.service.AuthenticationService;
import com.btcwallet.service.UserService;

@Service
public class AuthenticationServiceImpl implements AuthenticationService{
	
	private static final Logger logger = Logger.getLogger(AuthenticationServiceImpl.class);
	
	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private UserService userService;
	 
    @Autowired
    private AuthenticationManager authenticationManager;

   


    @Override
    public String loggedInUsername() {
    	logger.debug("loggedInUsername Called");
    	Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user instanceof User) {
            return ((User)user).getUsername();
        }

        return null;
    }
    
    @Override
    public com.btcwallet.model.User loggedInUser() {
    	logger.debug("loggedInUsername Called");
    	Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user instanceof User) {
        	return userService.findByUsername(((User) user).getUsername());
         
        }

        return null;
    }

    @Override
    public boolean login(String username, String password) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());

    	logger.debug("login Called");
        authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        if (usernamePasswordAuthenticationToken.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            logger.info(String.format("Login Success ", username));
            return true;
        }
        logger.info(String.format("Login failed ", username));
        return false;
    }
}