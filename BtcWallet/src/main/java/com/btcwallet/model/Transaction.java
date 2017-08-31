package com.btcwallet.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.btcwallet.enums.Status;
import com.btcwallet.enums.TransactionType;
import com.btcwallet.model.abstracts.CreatableItem;


@Entity
@Table(name = "transaction")
public class Transaction extends CreatableItem {



	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@OneToOne
    @JoinColumn(name = "to_user_id",nullable = false)
	private User toUser;
	
	@OneToOne
    @JoinColumn(name = "from_user_id")
	private User fromUser;

	
	@NotNull
	@Column(name = "transaction_date", nullable = false)
	private Date transactionDate;
	

	@Column(name = "confirmation_date")
	private Date confirmationDate;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private Status status;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private TransactionType transactionType;
	
	@Column(name = "from_address")
	private String fromAddress;
	
	@Column(name = "bitcoin_amount", nullable = false)
	private String bitcoinAmount;

	public User getToUser() {
		return toUser;
	}

	public void setToUser(User toUser) {
		this.toUser = toUser;
	}

	public User getFromUser() {
		return fromUser;
	}

	public void setFromUser(User fromUser) {
		this.fromUser = fromUser;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public Date getConfirmationDate() {
		return confirmationDate;
	}

	public void setConfirmationDate(Date confirmationDate) {
		this.confirmationDate = confirmationDate;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public TransactionType getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(TransactionType transactionType) {
		this.transactionType = transactionType;
	}

	public String getFromAddress() {
		return fromAddress;
	}

	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}

	public String getBitcoinAmount() {
		return bitcoinAmount;
	}

	public void setBitcoinAmount(String bitcoinAmount) {
		this.bitcoinAmount = bitcoinAmount;
	}
	 

	

}