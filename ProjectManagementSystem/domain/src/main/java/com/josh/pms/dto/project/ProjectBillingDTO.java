package com.josh.pms.dto.project;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectBillingDTO {
    private Integer id;
    private String billingType;
}
