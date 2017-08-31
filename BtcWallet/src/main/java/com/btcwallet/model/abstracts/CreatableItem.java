package com.btcwallet.model.abstracts;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class CreatableItem extends Item {

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@JsonIgnore
	@CreatedBy
	private String createdBy;

	
	@Column(name = "created_date", nullable = false)
	@JsonIgnore
	private Instant createdDate = Instant.now();
	

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }
}