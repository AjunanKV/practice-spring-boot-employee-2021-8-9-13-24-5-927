package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import com.thoughtworks.springbootemployee.repository.NewEmployeeRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CompanyService {
    @Resource
    private CompanyRepository companyRepositoryRepository;

    public CompanyService(CompanyRepository companyRepositoryRepository) {
        this.companyRepositoryRepository = companyRepositoryRepository;
    }

    public List<Employee> getAllEmployeesByCompanyId(Integer companyId) {
        Company company = companyRepositoryRepository.findById(companyId).orElse(null);
        return company.getEmployees();
    }
}
