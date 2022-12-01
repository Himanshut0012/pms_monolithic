package com.josh.pms.service.project;

import com.josh.pms.model.project.ProjectContributors;

import java.util.List;

public interface ProjectContributorService {
    public void addProjectContributors(ProjectContributors projectContributors);

    public List<ProjectContributors> getProjectEmployeeByProjectId(int id);

    public List<ProjectContributors> getAllProjectEmployee();
    
    public void deleteProjectContributor(int id);
}
