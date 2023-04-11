package com.url.shortner.demo.entity;

import org.springframework.data.annotation.Version;
import org.springframework.data.annotation.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;


@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class AuditableMongo<U> {
	
	@CreatedBy
	@Column(name = "Created_User", insertable = true, updatable = false)
	protected U createdUser;

	@CreatedDate
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "Created_Time", insertable = true, updatable = false)
	protected LocalDateTime createdTime;

	@LastModifiedBy
	@Column(name = "Updated_User", insertable = false, updatable = true)
	protected U updatedUser;

	@LastModifiedDate
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "Updated_Time", insertable = false, updatable = true)
	protected LocalDateTime updatedTime;

	@Version
	@Column(name = "Version",  updatable = true)	
	protected int version;

	/**
	 * @return the createdUser
	 */
	public U getCreatedUser() {
		return createdUser;
	}

	/**
	 * @param createdUser the createdUser to set
	 */
	public void setCreatedUser(U createdUser) {
		this.createdUser = createdUser;
	}

	/**
	 * @return the createdTime
	 */
	public LocalDateTime getCreatedTime() {
		return createdTime;
	}

	/**
	 * @param createdTime the createdTime to set
	 */
	public void setCreatedTime(LocalDateTime createdTime) {
		this.createdTime = createdTime;
	}

	/**
	 * @return the updatedUser
	 */
	public U getUpdatedUser() {
		return updatedUser;
	}

	/**
	 * @param updatedUser the updatedUser to set
	 */
	public void setUpdatedUser(U updatedUser) {
		this.updatedUser = updatedUser;
	}

	/**
	 * @return the updatedTime
	 */
	public LocalDateTime getUpdatedTime(){
		return updatedTime;
	}

	/**
	 * @param updatedTime the updatedTime to set
	 */
	public void setUpdatedTime(LocalDateTime updatedTime) {
		this.updatedTime = updatedTime;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}
		
}


