package com.josh.pms.service.project;

import com.josh.pms.dto.project.ProjectBillingDTO;

import java.util.List;

public interface ProjectBillingService {
    String getBillingById(int id);
    List<ProjectBillingDTO> findAll();
}
