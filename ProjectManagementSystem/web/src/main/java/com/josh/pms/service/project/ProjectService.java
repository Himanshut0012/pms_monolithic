package com.josh.pms.service.project;

import com.josh.pms.advice.NoRecordsFoundException;
import com.josh.pms.advice.UserNotFoundException;
import com.josh.pms.dto.project.ProjectCreationDTO;
import com.josh.pms.dto.project.ProjectDTO;
import com.josh.pms.dto.project.ProjectResponseDTO;
import com.josh.pms.dto.project.ProjectUpdatedDTO;
import com.josh.pms.dto.search.api.PagingHeaders;
import com.josh.pms.model.project.PagingResponseProject;
import com.josh.pms.model.project.Project;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;

import java.util.List;

public interface ProjectService {
    void addProject(ProjectCreationDTO project);

    ProjectDTO getProjectById(int id) throws UserNotFoundException;

    ProjectDTO updateProject(ProjectUpdatedDTO project, int projectId);

    void deleteProject(int id) throws UserNotFoundException;

    List<Project> getProjectsOfManager(int id);

    ProjectResponseDTO getAllProject(int pageNumber, int pageSize, String sortBy, String sortOrder) throws NoRecordsFoundException;

    public PagingResponseProject searchProjects(Specification<Project> spec, HttpHeaders headers, Sort sort);

}
