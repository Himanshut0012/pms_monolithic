package com.josh.pms.dao.project;

import com.josh.pms.model.project.ProjectStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectStatusRepository extends JpaRepository<ProjectStatus, Integer> {

    ProjectStatus findByProjectStatusId(Integer projectStatusId);
}
