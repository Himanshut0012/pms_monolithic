package com.josh.pms.service.project;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.josh.pms.dao.employee.EmployeeRepository;
import com.josh.pms.dao.project.ProjectBillingRepository;
import com.josh.pms.dao.project.ProjectContributorRepository;
import com.josh.pms.dao.project.ProjectRepository;
import com.josh.pms.dao.project.ProjectStatusRepository;
import com.josh.pms.dto.project.ProjectContributorDTO;
import com.josh.pms.dto.project.ProjectDTO;
import com.josh.pms.model.project.Project;
import com.josh.pms.model.project.ProjectContributors;

import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
@RequiredArgsConstructor
public class ProjectReportService {
	
	private final ModelMapper modelMapper;
	private final ProjectBillingRepository billingRepository;
	private final ProjectStatusRepository projectStatusRepository;
	private final EmployeeRepository employeeRepository;
	private final ProjectContributorRepository contributorsRepository;
	private final ProjectRepository projectRepository;
	
	@Scheduled(cron = "${scheduling.job.cron}")
	public String employeeReport() throws FileNotFoundException, JRException {
		List<Project> projects = (List<Project>) this.projectRepository.findAll();
		List<ProjectDTO> projectDTOS = new ArrayList<>();
		for(Project project : projects){
			projectDTOS.add(createProjectDTO(project));
		}
		
		File file = ResourceUtils.getFile("classpath:project.jrxml");
		JasperReport jasperReport = JasperCompileManager.compileReport(file.getPath());
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(projectDTOS);
		
		Map<String, Object> params = new HashMap<>();
		
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, dataSource);
		String path = String.valueOf(Paths.get(System.getProperty("user.home"), "Downloads"));
		JasperExportManager.exportReportToPdfFile(jasperPrint, path + "/project.pdf");
		
		return "Downloaded";
	}
	
	private ProjectDTO createProjectDTO(Project project) {
		ProjectDTO projectDTO = modelMapper.map(project, ProjectDTO.class);
		projectDTO.setBillingType(billingRepository.findByBillingId(project.getBillingId()).getBillingType());
		projectDTO.setManagerName(employeeRepository.findByEmpId(project.getManagerId()).getEmpName());
		projectDTO.setProjectStatus(projectStatusRepository.findByProjectStatusId(project.getStatusId()).getStatus());
		List<ProjectContributors> contributors = contributorsRepository.findByProjectId(project.getProjectId());
		List<ProjectContributorDTO> contributorsDTO = contributors.stream().map(contributor ->
				modelMapper.map(contributor, ProjectContributorDTO.class)).toList();

		projectDTO.setContributors(contributorsDTO);
		return projectDTO;
	}
}
