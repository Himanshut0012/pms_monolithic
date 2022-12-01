package com.josh.pms.controller.project;

import com.josh.pms.dto.project.ProjectStatusDTO;
import com.josh.pms.model.project.Project;
import com.josh.pms.model.project.ProjectStatus;
import com.josh.pms.service.project.ProjectStatusService;
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
@RequestMapping(value = "api/v1/status")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class ProjectStatusController {

    private final ProjectStatusService statusService;

    @ApiOperation(value = "Lists all the Status Types for a Project",response = ProjectStatusDTO.class)
    @GetMapping
    public ResponseEntity<List<ProjectStatusDTO>> getProjectStatuses(){
        return new ResponseEntity<>(statusService.findAll(), HttpStatus.OK);
    }
}
