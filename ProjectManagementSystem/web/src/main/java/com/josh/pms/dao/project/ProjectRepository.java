package com.josh.pms.dao.project;

import com.josh.pms.model.project.Project;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProjectRepository extends PagingAndSortingRepository<Project, Integer>, JpaSpecificationExecutor<Project> {
    List<Project> findByManagerId(Integer managerId);
}
