package com.josh.pms.dto.project;

import com.josh.pms.validator.billing.BillingValidation;
import com.josh.pms.validator.status.StatusValidation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectUpdatedDTO {

	@NotBlank(message = "projectCode.mandatory")
    private String projectCode ;

	@NotBlank(message = "projectName.mandatory")
	@Size(min=2, message="projectName.size")
    private String projectName ;

	@NotBlank(message = "companyName.mandatory")
	@Size(min=2, message = "companyName.size")
    private String companyName;

    private String repoUrl;
    private String technologies;

    private Date startDate;
    private Date endDate;

    @NotNull(message="manager.mandatory")
    private Integer managerId ;

    @BillingValidation
    private Integer billingId;

    @StatusValidation
    private Integer statusId;

    private List<ProjectContributorDTO> contributors;
}
