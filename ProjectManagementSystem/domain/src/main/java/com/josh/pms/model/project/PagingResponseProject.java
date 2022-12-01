package com.josh.pms.model.project;

import com.josh.pms.dto.project.ProjectDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PagingResponseProject {

    private Long count;
    private Long pageNumber;
    private Long pageSize;
    private Long pageOffset;
    private Long pageTotal;
    private List<ProjectDTO> searchProject;}
