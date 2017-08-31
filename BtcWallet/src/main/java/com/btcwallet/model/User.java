package com.btcwallet.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.btcwallet.enums.Role;
import com.btcwallet.model.abstracts.CreatableItem;


@Entity
@Table(name = "cf_user")
public class User extends CreatableItem {



	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Size(max = 50)
	@Column(name = "name", length = 50, nullable = false)
	private String name;

	@NotNull
	@Size(min = 1, max = 50)
	@Column(length = 50, unique = true, nullable = false)
	private String username;

	@JsonIgnore
	@NotNull
	@Size(min = 60, max = 60)
	@Column(name = "password_hash",length = 60, nullable = false)
	private String passwordHash;
	


	@JsonIgnore
	@ElementCollection(targetClass  = Role.class)
	@JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "userId"))
	@Column(name = "role", nullable = false)
	@Enumerated(EnumType.STRING)
    private Set<Role> roles;
	
	@NotNull
	@Column(name = "bitcoin_address", nullable = false)
	private String bitcoinAddress;
	
	@NotNull
	@Column(name = "bitcoin_amount", nullable = false)
	private String bitcoinAmount;
	 
	@Transient
	private String password;

	
	@Transient
	private String passwordConfirm;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public String getPasswordConfirm() {
		return passwordConfirm;
	}

	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}

	public String getBitcoinAddress() {
		return bitcoinAddress;
	}

	public void setBitcoinAddress(String bitcoinAddress) {
		this.bitcoinAddress = bitcoinAddress;
	}

	public String getBitcoinAmount() {
		return bitcoinAmount;
	}

	public void setBitcoinAmount(String bitcoinAmount) {
		this.bitcoinAmount = bitcoinAmount;
	}


}