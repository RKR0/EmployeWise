package com.rk.Assignment.model;


import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Document
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

	@Id
	String id;
	
	String firstName;
	
	String lastName; 
	
	String email; 
	
	String phoneNumber;
	
	String reportingId;
	
	String profileImage;
	
	LocalDateTime createdAt;
	
	LocalDateTime updatedAt;

}
