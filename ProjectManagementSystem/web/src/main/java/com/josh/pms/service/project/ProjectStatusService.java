package com.josh.pms.service.project;

import com.josh.pms.dto.project.ProjectStatusDTO;
import com.josh.pms.model.project.ProjectStatus;

import java.util.List;

public interface ProjectStatusService {
    List<ProjectStatus> getProjectStatus();
    String getProjectStatusById(int id);
    List<ProjectStatusDTO> findAll();
}
