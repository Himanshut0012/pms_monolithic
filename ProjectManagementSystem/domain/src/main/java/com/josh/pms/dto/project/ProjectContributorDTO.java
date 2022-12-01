package com.josh.pms.dto.project;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectContributorDTO {

    private int contributorId;
    private String contributorName;
    @JsonIgnore
    private String recordStatus = "W";
}
