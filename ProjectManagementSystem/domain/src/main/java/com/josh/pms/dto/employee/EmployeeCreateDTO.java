package com.josh.pms.dto.employee;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.josh.pms.validator.status.Mobile;
import com.josh.pms.validator.status.Name;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Validated
public class EmployeeCreateDTO {
	

	@NotBlank(message = "employeeName.mandatory")
	@Name
	@Size(min = 2, message = "employeeName.size")
	private String empName ;

	@NotBlank(message = "department.mandatory")
	private String department;

	@Email(message="email.invalid")
	private String emailId ;

	//@NotBlank(message = "mobile.mandatory")
	@Mobile
	@Size(min = 10, max =10, message = "mobile.invalid")
	private String mobileNo;

	@JsonIgnore
	private String recordStatus = "1";
}
