package com.josh.pms.controller.project;

import com.josh.pms.advice.NoRecordsFoundException;
import com.josh.pms.advice.UserNotFoundException;
import com.josh.pms.constants.AppConstants;
import com.josh.pms.dto.project.ProjectCreationDTO;
import com.josh.pms.dto.project.ProjectDTO;
import com.josh.pms.dto.project.ProjectResponseDTO;
import com.josh.pms.dto.project.ProjectUpdatedDTO;
import com.josh.pms.model.project.PagingResponseProject;
import com.josh.pms.model.project.Project;
import com.josh.pms.dto.search.api.PagingHeaders;
import com.josh.pms.service.project.ProjectService;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/projects")
@Api(value = "ProjectController",description = "Rest Controller for Project Resource!")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class ProjectController {
    private final ProjectService projectService;


    @ApiOperation(value = "Lists all Projects in the system", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 401, message = "not authorized!"),
            @ApiResponse(code = 403, message = "forbidden!!!"),
            @ApiResponse(code = 404, message = "not found!!!")
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProjectResponseDTO> getProjects(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY_PROJECT_ID) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION) String sortDir
    ) throws NoRecordsFoundException{
        return new ResponseEntity<>(projectService.getAllProject(pageNumber, pageSize, sortBy, sortDir), HttpStatus.OK);
    }

    @ApiOperation(value = "Get a Project by project Id",response = Project.class)
    @GetMapping(path = "/{projectId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProjectDTO> getProject(@PathVariable @NotNull int projectId) throws UserNotFoundException {
        return new ResponseEntity<>(projectService.getProjectById(projectId), HttpStatus.OK);
    }

    @ApiOperation(value ="Add a new Project in the system",response = Project.class)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addProject(@RequestBody @Valid ProjectCreationDTO projectCreationDTO) {
        projectService.addProject(projectCreationDTO);
        return new ResponseEntity<>("Project Created Successfully", HttpStatus.CREATED);
    }

    @SuppressWarnings("rawtypes")
    @ApiOperation(value = "Update the Project by project Id",response = Project.class )
    @PutMapping(path = "/{projectId}",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateProject(@RequestBody @Valid ProjectUpdatedDTO projectUpdateDTO, @PathVariable @NotNull int projectId) {
        projectService.updateProject(projectUpdateDTO, projectId);
        return new ResponseEntity<>("Project Updated Successfully", HttpStatus.OK);
    }

    @ApiOperation(value = "Delete the project by project Id",response = Project.class)
    @DeleteMapping("/{projectId}")
    public ResponseEntity deleteProject(@PathVariable @NotNull int projectId) throws UserNotFoundException {
        projectService.deleteProject(projectId);
        return new ResponseEntity<>("Project Deleted Successfully", HttpStatus.OK);
    }


    @ApiOperation(value = "Search Project by name",response = Project.class)
    @GetMapping(value = "/search",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PagingResponseProject> searchProjects(@And({
            @Spec(path = "projectName",params = "projectName",spec = Like.class),
            @Spec(path = "managerId",params = "managerId",spec = Equal.class),
            @Spec(path = "technologies",params = "technologies",spec = Like.class),
            @Spec(path = "projectId",params = "projectId",spec = Equal.class)
    }) Specification<Project> spec,
                                                        Sort sort,
                                                        @RequestHeader HttpHeaders headers){
        final PagingResponseProject response=projectService.searchProjects(spec , headers , sort);
        return new ResponseEntity<>(response,HttpStatus.OK);

    }


}
