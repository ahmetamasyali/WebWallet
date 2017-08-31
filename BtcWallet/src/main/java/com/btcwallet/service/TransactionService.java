package com.btcwallet.service;

import java.util.List;

import com.btcwallet.exceptions.BitcoinNotEnoughException;
import com.btcwallet.model.Transaction;
import com.btcwallet.model.User;

public interface TransactionService{

	public List<Transaction> findByUsername(String username);
	
	public Transaction save(Transaction transaction);
	
	public Transaction sendCoins(User from,User to, String amount) throws BitcoinNotEnoughException;
}
