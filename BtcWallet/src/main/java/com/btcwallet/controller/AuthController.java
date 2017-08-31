package com.btcwallet.controller;

import java.util.HashSet;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.btcwallet.enums.Role;
import com.btcwallet.model.Login;
import com.btcwallet.model.User;
import com.btcwallet.rest.BaseResponse;
import com.btcwallet.rest.ResponseMessages;
import com.btcwallet.service.UserService; 

@Controller
public class AuthController extends BaseController {

	private static final Logger logger = Logger.getLogger(AuthController.class);
	
	@Autowired
	private UserService userService;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@RequestMapping("/login")
	public String login(Model model) {
		if(isLoggedIn()) {
			return "redirect:/";
		}

		return "login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public BaseResponse login(@RequestBody Login login) { 
		User user = userService.findByUsername(login.getUsername());

		if(user !=null) {
		
			try {
				if(authenticationService.login(user.getUsername(), login.getPassword())) {
					return BaseResponse.success("Login");

				}
			}catch(Exception e) {
				e.printStackTrace();
				logger.error("error while authentication",e);
			}
			return BaseResponse.error(ResponseMessages.WRONG_PASS);
		}


		return BaseResponse.error("Username Not Found");
	}

	@RequestMapping("/register")
	String homxe(Model model) {
		if(isLoggedIn()) {
			return "redirect:/";
		}

		return "register";
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public BaseResponse register(@RequestBody User user) {
		if(user.getPassword() != null) {
			if(!user.getPassword().equals(user.getPasswordConfirm())) {
				return BaseResponse.error(ResponseMessages.PASSWORDS_NOT_EQUAL);
			}

		}else {
			return BaseResponse.error(ResponseMessages.PASSWORD_CANNOT_BE_NULL);
		}

		user.setPasswordHash(bCryptPasswordEncoder.encode(user.getPassword()));

		user.setRoles(new HashSet<Role>());
		user.getRoles().add(Role.USER);
		try {
			String address = bitcoinService.createAddress(user.getUsername());
			user.setBitcoinAddress(address);
			user.setBitcoinAmount("0.000000000");
			user = userService.save(user);
		}catch(DataIntegrityViolationException e) {
			logger.error("Username already Exist",e);
			e.printStackTrace();
			return BaseResponse.error(ResponseMessages.USERNAME_ALREADY_IN_USE);
		}catch(Exception e) {
			logger.error("error while registration",e);
			e.printStackTrace();
			return BaseResponse.error(ResponseMessages.ERROR);
		}

		return BaseResponse.success("Success");
	}



}
