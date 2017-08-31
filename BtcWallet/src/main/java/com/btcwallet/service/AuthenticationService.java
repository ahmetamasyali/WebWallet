package com.btcwallet.service;

import com.btcwallet.model.User;

public interface AuthenticationService {

	public boolean login(String username, String password);

	public String loggedInUsername();

	public User loggedInUser();

}
