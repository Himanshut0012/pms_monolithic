package com.josh.pms.dto.project;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDTO {

    private Integer projectId;
    private String projectCode ;
    private String projectName ;
    private String companyName;
    private String managerName;
    private String projectStatus;
    private String billingType;
    private String repoUrl;
    private String technologies;
    private Date startDate;
    private Date endDate;
    private Integer billingId;
    private Integer statusId;
    private Integer managerId;
    private List<ProjectContributorDTO> contributors;
}
