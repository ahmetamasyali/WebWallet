package com.btcwallet.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.btcwallet.model.User;
import com.btcwallet.service.AuthenticationService;
import com.btcwallet.service.BitcoinService; 


public abstract class BaseController {

	private static final Logger logger = Logger.getLogger(BaseController.class);

	@Autowired
	public AuthenticationService authenticationService;

	@Autowired
	public BitcoinService bitcoinService;

	public boolean isLoggedIn() {
		logger.debug("isLoggedIn called");
		return !StringUtils.isEmpty(authenticationService.loggedInUsername());
	}
	
	public User getUser() {
		logger.debug("getUser called");
		User user =  authenticationService.loggedInUser();
	
		
		return user;
	}


}
