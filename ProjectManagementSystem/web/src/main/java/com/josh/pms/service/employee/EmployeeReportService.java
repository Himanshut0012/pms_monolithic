package com.josh.pms.service.employee;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.josh.pms.dao.employee.EmployeeRepository;
import com.josh.pms.model.employee.Employee;

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
public class EmployeeReportService {

	private final EmployeeRepository employeeRepository;
	
	@Scheduled(cron = "${scheduling.job.cron}")
	public String employeeReport() throws FileNotFoundException, JRException {
		List<Employee> employees = (List<Employee>) employeeRepository.findAll();
		
		File file = ResourceUtils.getFile("classpath:employees.jrxml");
		JasperReport jasperReport = JasperCompileManager.compileReport(file.getPath());
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(employees);

		Map<String, Object> params = new HashMap<>();
		
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, dataSource);
		String path = String.valueOf(Paths.get(System.getProperty("user.home"), "Downloads"));
		JasperExportManager.exportReportToPdfFile(jasperPrint, path + "/employees.pdf");
		
		return "Downloaded";
	}
}
