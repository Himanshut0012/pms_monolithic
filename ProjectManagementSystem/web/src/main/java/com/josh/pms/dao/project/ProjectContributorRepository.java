package com.josh.pms.dao.project;

import com.josh.pms.model.project.ProjectContributors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ProjectContributorRepository extends JpaRepository<ProjectContributors, Integer> {

    @Transactional
    @Modifying
    void deleteByProjectId(int projectId);

    List<ProjectContributors> findByProjectId(Integer projectId);

    ProjectContributors findByContributorId(int contributorId);
}
