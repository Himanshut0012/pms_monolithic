package com.josh.pms.service.employee;

import com.josh.pms.advice.NoRecordsFoundException;
import com.josh.pms.dto.employee.EmployeeCreateDTO;
import com.josh.pms.dto.employee.EmployeeDTO;
import com.josh.pms.dto.employee.EmployeeResponseDTO;
import com.josh.pms.dto.search.api.PagingHeaders;
import com.josh.pms.model.employee.Employee;
import com.josh.pms.model.employee.PagingResponseEmployee;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;

public interface EmployeeService {
     EmployeeResponseDTO getAllEmployee(int pageNumber, int pageSize, String sortBy, String sortOrder) throws NoRecordsFoundException;

    void addEmployee(EmployeeCreateDTO employeeCreateDTO);

    EmployeeDTO updateEmployee(EmployeeCreateDTO employeeCreateDTO, int employeeId);
    void deleteEmployee(int employeeId) throws Exception;

    public PagingResponseEmployee searchEmployee(Specification<Employee> spec, HttpHeaders headers, Sort sort);

    public HttpHeaders returnHttpHeaders(PagingResponseEmployee response);

}
