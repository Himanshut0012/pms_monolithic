package com.josh.pms.service.employee;

import java.util.List;
import java.util.Objects;

import com.josh.pms.dto.search.api.PagingHeaders;
import com.josh.pms.model.employee.PagingResponseEmployee;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import com.josh.pms.advice.NoRecordsFoundException;
import com.josh.pms.dao.employee.EmployeeRepository;
import com.josh.pms.dto.employee.EmployeeCreateDTO;
import com.josh.pms.dto.employee.EmployeeDTO;
import com.josh.pms.dto.employee.EmployeeResponseDTO;
import com.josh.pms.model.employee.Employee;
import com.josh.pms.model.project.Project;
import com.josh.pms.service.project.ProjectContributorService;
import com.josh.pms.service.project.ProjectService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService{

	private final ProjectService projectService;
	private final ProjectContributorService projectContributorService;
    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    @Override
    public void addEmployee(EmployeeCreateDTO employeeCreateDTO) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        Employee employee=modelMapper.map(employeeCreateDTO, Employee.class);
        this.employeeRepository.save(employee);
    }

    @Override
    public EmployeeResponseDTO getAllEmployee(int pageNo, int pageSize, String sortBy, String sortDir) throws NoRecordsFoundException {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortBy).ascending(): Sort.by(sortBy).descending();

        Pageable page = PageRequest.of(pageNo, pageSize, sort);

        Page<Employee> employeePage = employeeRepository.findAll(page);

        List<Employee> employees = employeePage.getContent();
        List<EmployeeDTO> employeeDTOs = employees.stream()
                                                    .map(employee -> modelMapper.map(employee, EmployeeDTO.class))
                                                    .toList();

      if (!employeeDTOs.isEmpty()) {
    	  EmployeeResponseDTO employeeResponseDTO = new EmployeeResponseDTO();
          employeeResponseDTO.setEmployees(employeeDTOs);
          employeeResponseDTO.setPageNumber(employeePage.getNumber());
          employeeResponseDTO.setPageSize(employeePage.getSize());
          employeeResponseDTO.setTotalPages(employeePage.getTotalPages());
          employeeResponseDTO.setTotalElements(employeePage.getTotalElements());
          employeeResponseDTO.setLast(employeePage.isLast());

          return  employeeResponseDTO;
	} else {
		throw new NoRecordsFoundException("No Records Found");
	}  
    }

    @Override
    public void deleteEmployee(int employeeId) throws Exception {
        this.projectContributorService.deleteProjectContributor(employeeId);

        List<Project> project = this.projectService.getProjectsOfManager(employeeId);

        if (project.isEmpty()) {
            this.employeeRepository.deleteById(employeeId);
        } else {
            throw new Exception("We can't delete this Employee. Becuase this Employee has been as Manager Role.");
        }
    }

    @Override
    public PagingResponseEmployee searchEmployee(Specification<Employee> spec, HttpHeaders headers, Sort sort) {
        if (isRequestPaged(headers)) {
            return get(spec, buildPageRequest(headers, sort));
        } else {
            List<Employee> entities = get(spec, sort);
            List<EmployeeDTO> employeeDTOs = entities.stream()
                    .map(employee -> modelMapper.map(employee, EmployeeDTO.class))
                    .toList();
            return new PagingResponseEmployee((long) employeeDTOs.size(), 0L, 0L, 0L, 0L, employeeDTOs);
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
    public PagingResponseEmployee get(Specification<Employee> spec, Pageable pageable) {
        Page<Employee> page = employeeRepository.findAll(spec, pageable);
        List<Employee> content = page.getContent();
        List<EmployeeDTO> employeeDTOs = content.stream()
                .map(employee -> modelMapper.map(employee, EmployeeDTO.class))
                .toList();
        return new PagingResponseEmployee(page.getTotalElements(), (long) page.getNumber(), (long) page.getNumberOfElements(), pageable.getOffset(), (long) page.getTotalPages(), employeeDTOs);
    }
    public List<Employee> get(Specification<Employee> spec, Sort sort) {
        return employeeRepository.findAll(spec, sort);
    }
    public HttpHeaders returnHttpHeaders(PagingResponseEmployee response) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(PagingHeaders.COUNT.getName(), String.valueOf(response.getCount()));
        headers.set(PagingHeaders.PAGE_SIZE.getName(), String.valueOf(response.getPageSize()));
        headers.set(PagingHeaders.PAGE_OFFSET.getName(), String.valueOf(response.getPageOffset()));
        headers.set(PagingHeaders.PAGE_NUMBER.getName(), String.valueOf(response.getPageNumber()));
        headers.set(PagingHeaders.PAGE_TOTAL.getName(), String.valueOf(response.getPageTotal()));
        return headers;
    }

    @Override
    public EmployeeDTO updateEmployee(EmployeeCreateDTO employeeCreateDTO, int employeeId) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return employeeRepository.findById(employeeId)
                .map(employee ->{
                    employee.setEmpName(employeeCreateDTO.getEmpName());
                    employee.setEmailId(employeeCreateDTO.getEmailId());
                    employee.setMobileNo(employeeCreateDTO.getMobileNo());
                    employee.setDepartment(employeeCreateDTO.getDepartment());
                    employee.setEmpId(employeeId);
                    employeeRepository.save(employee);
                    return modelMapper.map(employee, EmployeeDTO.class);
                })
                .orElseGet(()->{
                    EmployeeDTO saveEmployee=modelMapper.map(employeeCreateDTO, EmployeeDTO.class);
                    return modelMapper.map(saveEmployee, EmployeeDTO.class);
                });
    }
}
