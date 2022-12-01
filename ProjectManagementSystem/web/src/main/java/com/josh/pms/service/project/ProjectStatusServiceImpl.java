package com.josh.pms.service.project;

import com.josh.pms.dao.project.ProjectStatusRepository;
import com.josh.pms.dto.project.ProjectStatusDTO;
import com.josh.pms.model.project.ProjectStatus;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectStatusServiceImpl implements ProjectStatusService{

    private final ProjectStatusRepository projectStatusRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<ProjectStatus> getProjectStatus() {
        return this.projectStatusRepository.findAll();
    }

    @Override
    public String getProjectStatusById(int id) {
        return this.projectStatusRepository.findByProjectStatusId(id).getStatus();
    }
    @Override
    public List<ProjectStatusDTO> findAll() {
        List<ProjectStatus> projectStatuses = projectStatusRepository.findAll();
        List<ProjectStatusDTO> projectStatusDTOS = new ArrayList<>();

        for(ProjectStatus projectStatus : projectStatuses){
            projectStatusDTOS.add(modelMapper.map(projectStatus, ProjectStatusDTO.class));
        }
        return projectStatusDTOS;
    }
}
