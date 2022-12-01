package com.josh.pms.dto.employee;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {
	
	private Integer empId ;
	private String empName ;
	private String department;
	private String emailId ;
	private String mobileNo;
}
