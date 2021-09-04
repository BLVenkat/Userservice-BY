package com.bridgelabz.userservice.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	

	private String firstName;
	
	private String lastName;
	
	@Column(nullable = false,unique = true)
	private String emailId;
	
	@Column(nullable = false,unique = true)
	private String phoneNumber;
	
	private Boolean isVerified;
	
	private String profilePicURL;
	@JsonIgnore
	private String password;
	
	@CreationTimestamp
	private LocalDateTime createdTimeStamp;

	@UpdateTimestamp
	private LocalDateTime updatedTimeStamp;

}
