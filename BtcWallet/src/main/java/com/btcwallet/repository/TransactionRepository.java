package com.btcwallet.repository;


import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.btcwallet.model.Transaction;

public interface TransactionRepository extends CrudRepository<Transaction, Long> {

	@Query("SELECT transaction FROM Transaction transaction "
			+ " LEFT JOIN FETCH transaction.toUser toUser"
			+ " LEFT JOIN FETCH transaction.fromUser "
			+ " WHERE toUser.username  =:username")
	public List<Transaction> findByUsername(@Param("username") String username);

	public Transaction save(Transaction transaction);

	


}
