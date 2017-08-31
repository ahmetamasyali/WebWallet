package com.btcwallet.service;

import com.btcwallet.model.User;

public interface UserService{

	public User findByUsername(String username);
	
	public User findByBitcoinAddress(String bitcoinAddress);

	public User save(User user);
}
