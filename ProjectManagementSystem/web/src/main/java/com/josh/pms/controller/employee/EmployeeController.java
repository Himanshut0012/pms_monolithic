package com.josh.pms.controller.employee;

import com.josh.pms.advice.NoRecordsFoundException;
import com.josh.pms.constants.AppConstants;
import com.josh.pms.dto.employee.EmployeeCreateDTO;
import com.josh.pms.dto.employee.EmployeeDTO;
import com.josh.pms.dto.employee.EmployeeResponseDTO;
import com.josh.pms.dto.search.api.PagingHeaders;
import com.josh.pms.model.employee.PagingResponseEmployee;
import com.josh.pms.service.employee.EmployeeService;
import com.josh.pms.model.employee.Employee;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/employees")
@RequiredArgsConstructor
@Api(value = "EmployeeController",description = "REST Controller for Employee Resource")
@CrossOrigin(origins = "http://localhost:4200")
public class EmployeeController {
    private final EmployeeService employeeService;

    @ApiOperation(value = "Lists all Employees in the system",response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 401, message = "not authorized!"),
            @ApiResponse(code = 403, message = "forbidden!!!"),
            @ApiResponse(code = 404, message = "not found!!!")
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeResponseDTO> getAllEmployee(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY_EMP_ID) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION) String sortDir
    ) throws NoRecordsFoundException{
        return new ResponseEntity<>(employeeService.getAllEmployee(pageNumber, pageSize, sortBy, sortDir), HttpStatus.OK);
    }

    @ApiOperation(value = "Add a new Employee in the system",response = Employee.class)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addEmployee(@RequestBody @Valid EmployeeCreateDTO employeeCreateDTO) {
        this.employeeService.addEmployee(employeeCreateDTO);
        return new ResponseEntity<>("Employee Created Successfully", HttpStatus.CREATED);
    }

    @ApiOperation(value = "Update the Employee by Id",response = Employee.class)
    @PutMapping(path = "/{employeeId}",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateEmployee(@RequestBody @Valid EmployeeCreateDTO employeeCreateDTO, @PathVariable @NotNull int employeeId) {
        this.employeeService.updateEmployee(employeeCreateDTO, employeeId);
        return new ResponseEntity<>("Employee Updated Successfully", HttpStatus.OK);
    }

    @SuppressWarnings("rawtypes")
	@ApiOperation(value = "Delete the Employee by Id",response = Employee.class)
    @DeleteMapping("/{employeeId}")
    public ResponseEntity deleteEmployee(@PathVariable @NotNull int employeeId) throws Exception {
        this.employeeService.deleteEmployee( employeeId);
        return new ResponseEntity<>("Employee Deleted Successfully", HttpStatus.OK);
    }



    @ApiOperation(value = "Search Employee by name",response = Employee.class)
    @GetMapping(value = "/search",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EmployeeDTO>> searchEmployee(@And({
            @Spec(path = "empName",params = "empName",spec = Like.class),
            @Spec(path = "empId",params = "empId",spec = Equal.class),
            @Spec(path = "emailId",params = "emailId",spec = Like.class),
            @Spec(path = "mobileNo",params = "mobileNo",spec = Equal.class),
            @Spec(path = "department",params = "department",spec = Like.class)
    })Specification<Employee> spec,
                                                            Sort sort,
                                                            @RequestHeader HttpHeaders headers){
        final PagingResponseEmployee response=employeeService.searchEmployee(spec , headers , sort);
        return new ResponseEntity<>(response.getSearchEmployees(),employeeService.returnHttpHeaders(response),HttpStatus.OK);

    }


}
