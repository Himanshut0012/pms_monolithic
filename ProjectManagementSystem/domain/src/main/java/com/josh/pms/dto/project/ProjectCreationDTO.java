package com.josh.pms.dto.project;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.josh.pms.validator.billing.BillingValidation;
import com.josh.pms.validator.status.StatusValidation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Validated
public class ProjectCreationDTO {

	@NotBlank(message = "projectName.mandatory")
	@Size(min=2, message = "projectName.size")
    private String projectName ;

	@NotBlank(message = "projectCode.mandatory")
    private String projectCode ;

	@NotBlank(message = "companyName.mandatory")
	@Size(min=2, message = "companyName.size")
    private String companyName;

	@NotNull(message="manager.mandatory")
    private Integer managerId ;

	@BillingValidation
    private Integer billingId;

	@StatusValidation
    private Integer statusId;

	@JsonIgnore
    private String recordStatus = "1";
}
 