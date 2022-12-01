package com.josh.pms.model.project;

import com.josh.pms.Auditable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "billing")
public class ProjectBilling extends Auditable<String> {

	@Id
	private Integer billingId;
	private String billingType;
}
