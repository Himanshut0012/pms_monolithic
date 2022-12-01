package com.josh.pms.service.project;

import com.josh.pms.dao.project.ProjectContributorRepository;
import com.josh.pms.model.project.ProjectContributors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectContributorServiceImpl implements ProjectContributorService {

    private final ProjectContributorRepository projectContributorsRepository;

    @Override
    public List<ProjectContributors> getProjectEmployeeByProjectId(int id) {
        return this.projectContributorsRepository.findByProjectId(id);
    }

    @Override
    public List<ProjectContributors> getAllProjectEmployee() {
        return this.projectContributorsRepository.findAll();
    }

    @Override
    public void addProjectContributors(ProjectContributors projectContributors) {
        this.projectContributorsRepository.save(projectContributors);
    }

	@Override
	public void deleteProjectContributor(int id) {
        ProjectContributors projectContributor = projectContributorsRepository.findByContributorId(id);
		projectContributorsRepository.delete(projectContributor);
	}
}
