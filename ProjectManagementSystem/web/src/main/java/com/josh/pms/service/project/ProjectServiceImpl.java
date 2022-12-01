package com.josh.pms.service.project;

import com.josh.pms.advice.NoRecordsFoundException;
import com.josh.pms.advice.UserNotFoundException;
import com.josh.pms.dao.employee.EmployeeRepository;
import com.josh.pms.dao.project.ProjectBillingRepository;
import com.josh.pms.dao.project.ProjectContributorRepository;
import com.josh.pms.dao.project.ProjectRepository;
import com.josh.pms.dao.project.ProjectStatusRepository;
import com.josh.pms.dto.project.*;
import com.josh.pms.model.project.PagingResponseProject;
import com.josh.pms.model.project.Project;
import com.josh.pms.model.project.ProjectContributors;
import com.josh.pms.dto.search.api.PagingHeaders;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService{

    private final ProjectRepository projectRepository;
    private final ProjectBillingRepository billingRepository;
    private final ProjectStatusRepository projectStatusRepository;
    private final EmployeeRepository employeeRepository;
    private final ProjectContributorRepository contributorsRepository;
    private final ModelMapper modelMapper;

    @Override
    public void addProject(ProjectCreationDTO projectCreationDTO) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        Project project = modelMapper.map(projectCreationDTO, Project.class);
        this.projectRepository.save(project);
    }

    @Override
    public ProjectDTO getProjectById(int id) throws UserNotFoundException {
        Optional<Project> project = projectRepository.findById(id);
        if (project!=null) {
        	return project.map(this::createProjectDTO).orElse(null);
		} else {
			throw new UserNotFoundException("No Data found with "+id);
		}
        
    }

    @Override
    public ProjectDTO updateProject(ProjectUpdatedDTO updatedProjectDTO, int projectId) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        List<ProjectContributorDTO> updatedContributorsDTOs = updatedProjectDTO.getContributors();
        List<ProjectContributors> previousContributors = contributorsRepository.findByProjectId(projectId);
        System.out.println(previousContributors);

        if(updatedContributorsDTOs != null && previousContributors != null){
            List<ProjectContributors> updatedContributors = updatedContributorsDTOs.stream().map(dto ->modelMapper.map(dto, ProjectContributors.class)).toList();
            List<ProjectContributors> contributorsToBeDeleted = previousContributors.stream().filter(id -> !updatedContributors.contains(id)).toList();
            List<ProjectContributors> contributorsToBeAdded = updatedContributors.stream().filter(id -> !previousContributors.contains(id)).toList();
            contributorsToBeAdded.forEach(projectContributors -> projectContributors.setProjectId(projectId));
            contributorsRepository.saveAll(contributorsToBeAdded);
            contributorsRepository.deleteAll(contributorsToBeDeleted);
        }else if(updatedContributorsDTOs != null) {
            List<ProjectContributors> updatedContributors = updatedContributorsDTOs.stream().map(dto ->modelMapper.map(dto, ProjectContributors.class)).toList();
            updatedContributors.forEach(contributor -> contributor.setProjectId(projectId));
            contributorsRepository.saveAll(updatedContributors);
        }else if(previousContributors != null){
            contributorsRepository.deleteAll(previousContributors);
        }

        return projectRepository.findById(projectId)
                .map(project1 -> {
                    project1.setProjectName(updatedProjectDTO.getProjectName());
                    project1.setProjectCode(updatedProjectDTO.getProjectCode());
                    project1.setCompanyName(updatedProjectDTO.getCompanyName());
                    project1.setManagerId(updatedProjectDTO.getManagerId());
                    project1.setBillingId(updatedProjectDTO.getBillingId());
                    project1.setStatusId(updatedProjectDTO.getStatusId());
                    project1.setStartDate(updatedProjectDTO.getStartDate());
                    project1.setEndDate(updatedProjectDTO.getEndDate());
                    project1.setRepoUrl(updatedProjectDTO.getRepoUrl());
                    project1.setTechnologies(updatedProjectDTO.getTechnologies());
                    projectRepository.save(project1);
                    return modelMapper.map(project1, ProjectDTO.class);
                })
                .orElseGet(()->{
                    Project savedProject = modelMapper.map(updatedProjectDTO, Project.class);
                    return modelMapper.map(savedProject, ProjectDTO.class);
                });
    }

    @Override
    public void deleteProject(int projectId) throws UserNotFoundException {
        Optional<Project> project = this.projectRepository.findById(projectId);
        if (project!=null) {
        	 contributorsRepository.deleteByProjectId(projectId);
             projectRepository.delete(project.get());
		} else {
			throw new UserNotFoundException("No Data found with "+projectId);
		}
       
    }

    @Override
    public ProjectResponseDTO getAllProject(int pageNumber, int pageSize, String sortBy, String sortDir) throws NoRecordsFoundException {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable page = PageRequest.of(pageNumber,pageSize, sort);
        Page<Project> projectsPage = projectRepository.findAll(page);

        List<Project> projects = projectsPage.getContent();
        List<ProjectDTO> projectDTOS = projects.stream().map(this::createProjectDTO).toList();
        if (!projectDTOS.isEmpty()) {
        	 ProjectResponseDTO projectResponseDTO = new ProjectResponseDTO();
             projectResponseDTO.setProjects(projectDTOS);
             projectResponseDTO.setPageNumber(projectsPage.getNumber());
             projectResponseDTO.setPageSize(projectsPage.getSize());
             projectResponseDTO.setTotalPages(projectsPage.getTotalPages());
             projectResponseDTO.setTotalElements(projectsPage.getTotalElements());
             projectResponseDTO.setLast(projectsPage.isLast());

             return projectResponseDTO;
		} else {
			throw new NoRecordsFoundException("No Records Found");
		}

       
    }



        private ProjectDTO createProjectDTO(Project project) {
        ProjectDTO projectDTO = modelMapper.map(project, ProjectDTO.class);
        projectDTO.setBillingType(billingRepository.findByBillingId(project.getBillingId()).getBillingType());
        projectDTO.setManagerName(employeeRepository.findByEmpId(project.getManagerId()).getEmpName());
        projectDTO.setProjectStatus(projectStatusRepository.findByProjectStatusId(project.getStatusId()).getStatus());
        List<ProjectContributors> contributors = contributorsRepository.findByProjectId(project.getProjectId());

        List<ProjectContributorDTO> contributorsDTO = contributors.stream()
                                                                  .map(contributor -> modelMapper.map(contributor, ProjectContributorDTO.class))
                                                                  .toList();
        contributorsDTO.forEach(contributor -> contributor.setContributorName(employeeRepository.findByEmpId(contributor.getContributorId()).getEmpName()));

        projectDTO.setContributors(contributorsDTO);
        return projectDTO;
    }

	@Override
	public List<Project> getProjectsOfManager(int employeeId) {
		
		return this.projectRepository.findByManagerId(employeeId);
	}


    @Override
    public PagingResponseProject searchProjects(Specification<Project> spec, HttpHeaders headers, Sort sort) {
        if (isRequestPaged(headers)) {
            return get(spec, buildPageRequest(headers, sort));
        } else {
            System.out.println("Else flow");
            List<Project> entities = get(spec, sort);
            List<ProjectDTO> projectDTOS=entities.stream()
                    .map(this::createProjectDTO).toList();

            return new PagingResponseProject((long) entities.size(), 0L, 0L, 0L, 0L, projectDTOS);
        }
    }
    private boolean isRequestPaged(HttpHeaders headers) {
        return headers.containsKey(PagingHeaders.PAGE_NUMBER.getName()) && headers.containsKey(PagingHeaders.PAGE_SIZE.getName());
    }
    private Pageable buildPageRequest(HttpHeaders headers, Sort sort) {
        int page = Integer.parseInt(Objects.requireNonNull(headers.get(PagingHeaders.PAGE_NUMBER.getName())).get(0));
        int size = Integer.parseInt(Objects.requireNonNull(headers.get(PagingHeaders.PAGE_SIZE.getName())).get(0));
        return PageRequest.of(page, size, sort);
    }
    public PagingResponseProject get(Specification<Project> spec, Pageable pageable) {
        Page<Project> page = projectRepository.findAll(spec, pageable);
        List<Project> content = page.getContent();
        List<ProjectDTO> projectDTOS=content.stream()
                .map(this::createProjectDTO).toList();
        return new PagingResponseProject(page.getTotalElements(), (long) page.getNumber(), (long) page.getNumberOfElements(), pageable.getOffset(), (long) page.getTotalPages(), projectDTOS);
    }
    public List<Project> get(Specification<Project> spec, Sort sort) {
        return projectRepository.findAll(spec, sort);
    }
}
