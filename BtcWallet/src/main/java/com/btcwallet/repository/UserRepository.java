package com.btcwallet.repository;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.btcwallet.model.User;

public interface UserRepository extends CrudRepository<User, Long> {

	@Query("SELECT user FROM User user "
			+ " LEFT JOIN FETCH user.roles "
			+ " WHERE user.username  =:username")
	public User findByUsername(@Param("username") String username);

	public User save(User user);

	@Query("SELECT user FROM User user "
			+ " LEFT JOIN FETCH user.roles "
			+ " WHERE user.bitcoinAddress  =:bitcoinAddress")
	public User findByBitcoinAddress(@Param("bitcoinAddress")String bitcoinAddress);


}
