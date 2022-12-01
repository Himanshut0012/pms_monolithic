package com.josh.pms.controller.project;

import com.josh.pms.dto.project.ProjectBillingDTO;
import com.josh.pms.model.project.Project;
import com.josh.pms.service.project.ProjectBillingService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping(value = "api/v1/billing")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class ProjectBillingController {

    private final ProjectBillingService projectBillingService;

    @ApiOperation(value = "Lists all the Billing Types available",response = ProjectBillingDTO.class)
    @GetMapping
    public ResponseEntity<List<ProjectBillingDTO>> getProjectBillings(){

        return new ResponseEntity<>(projectBillingService.findAll(), HttpStatus.OK);
    }
}
