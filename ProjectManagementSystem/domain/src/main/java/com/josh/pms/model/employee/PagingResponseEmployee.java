package com.josh.pms.model.employee;

import com.josh.pms.dto.employee.EmployeeDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PagingResponseEmployee {

    private Long count;
    private Long pageNumber;
    private Long pageSize;
    private Long pageOffset;
    private Long pageTotal;
    private List<EmployeeDTO> searchEmployees;


}
