package com.josh.pms.service.project;

import com.josh.pms.dao.project.ProjectBillingRepository;
import com.josh.pms.dto.project.ProjectBillingDTO;
import com.josh.pms.model.project.ProjectBilling;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectBillingServiceImpl implements ProjectBillingService {

    private final ProjectBillingRepository billingRepository;
    private final ModelMapper modelMapper;

    @Override
    public String getBillingById(int id) {
        return this.billingRepository.findByBillingId(id).getBillingType();
    }

    @Override
    public List<ProjectBillingDTO> findAll() {
        List<ProjectBilling> projectBillings = billingRepository.findAll();
        List<ProjectBillingDTO> billingDTOS = new ArrayList<>();
        for(ProjectBilling billing : projectBillings){
            billingDTOS.add(modelMapper.map(billing, ProjectBillingDTO.class));
        }
        return billingDTOS;
    }
}
