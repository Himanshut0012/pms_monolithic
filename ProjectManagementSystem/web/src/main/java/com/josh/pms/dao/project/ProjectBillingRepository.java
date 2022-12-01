package com.josh.pms.dao.project;

import com.josh.pms.model.project.ProjectBilling;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectBillingRepository extends JpaRepository<ProjectBilling, Integer> {

    ProjectBilling findByBillingId(Integer billingId);
}
