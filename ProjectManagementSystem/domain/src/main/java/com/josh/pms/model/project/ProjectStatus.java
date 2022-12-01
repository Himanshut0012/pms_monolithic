package com.josh.pms.model.project;

import com.josh.pms.Auditable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectStatus extends Auditable<String> {

	@Id
	private Integer projectStatusId;
	private String status;
}
