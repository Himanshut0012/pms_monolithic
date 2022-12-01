package com.josh.pms.model.project;

import com.josh.pms.Auditable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Project extends Auditable<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer projectId ;
    private String projectCode ;
    private String projectName ;
    private String companyName;
    private String repoUrl;
    private Integer statusId;
    private String technologies;
    private Integer billingId ;
    private Integer managerId ;
    private Date startDate;
    private Date endDate;
}
