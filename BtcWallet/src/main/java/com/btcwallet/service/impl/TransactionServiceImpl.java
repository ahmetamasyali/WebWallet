package com.btcwallet.service.impl;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.btcwallet.enums.Status;
import com.btcwallet.enums.TransactionType;
import com.btcwallet.exceptions.BitcoinNotEnoughException;
import com.btcwallet.model.Transaction;
import com.btcwallet.model.User;
import com.btcwallet.repository.TransactionRepository;
import com.btcwallet.service.TransactionService;
import com.btcwallet.service.UserService;

@Service
public class TransactionServiceImpl  implements TransactionService {

	private static final Logger logger = Logger.getLogger(TransactionServiceImpl.class);

	@Autowired
	private UserService userService;

	private TransactionRepository transactionRepository;


	public TransactionServiceImpl(TransactionRepository transactionRepository) {
		this.transactionRepository = transactionRepository;

	}

	public Transaction save(Transaction transaction) {
		logger.debug("saving Transaction");


		return transactionRepository.save(transaction);
	}



	@Override
	public List<Transaction> findByUsername(String username) {
		logger.debug("findByUsername called");
		return transactionRepository.findByUsername(username);
	}

	@Override
	@Transactional
	public Transaction sendCoins(User from, User to, String amount) throws BitcoinNotEnoughException {


		Double btcAmountFromUsers = Double.valueOf(from.getBitcoinAmount());
		
		Double btcAmountToUsers = Double.valueOf(to.getBitcoinAmount());
		
		Double btcAmount =  Double.valueOf(amount);
		
		if(btcAmount>btcAmountFromUsers) {
			throw new BitcoinNotEnoughException("BitcoinNotEnough");
		}
		from.setBitcoinAmount(String.valueOf(btcAmountFromUsers-btcAmount));
		to.setBitcoinAmount(String.valueOf(btcAmountToUsers+btcAmount));
		
		userService.save(from);
		userService.save(to);
		
		Transaction transaction = new Transaction();
		transaction.setBitcoinAmount(amount);
		transaction.setToUser(to);
		transaction.setFromUser(from);
		transaction.setTransactionDate(new Date());
		transaction.setTransactionType(TransactionType.EXTERNAL);
		transaction.setStatus(Status.CONFIRMED);
		transaction.setConfirmationDate(new Date());

		return save(transaction);
	}





}
