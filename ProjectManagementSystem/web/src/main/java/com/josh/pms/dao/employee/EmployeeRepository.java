package com.josh.pms.dao.employee;

import com.josh.pms.model.employee.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends PagingAndSortingRepository<Employee, Integer>, JpaSpecificationExecutor<Employee> {

    Employee findByEmpId(int employeeId);
}
