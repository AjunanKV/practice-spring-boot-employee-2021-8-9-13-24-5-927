package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.Exceptions.NoEmployeeWithIDException;
import com.thoughtworks.springbootemployee.advice.entity.Company;
import com.thoughtworks.springbootemployee.advice.entity.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
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


    public Company addCompany(Company companyinfo) {
        return companyRepositoryRepository.save(companyinfo);
    }

    public Company updateCompany(int companyId, Company updateCompanyDetails) {
        Company updateCompany = companyRepositoryRepository.findById(companyId)
                .map(company -> updateCompanyInformation(company, updateCompanyDetails))
                .get(); //TODO: findbyID then update
        return companyRepositoryRepository.save(updateCompany);
    }

    private Company updateCompanyInformation(Company company, Company companyInfo) {
        if (companyInfo.getName() != null) {
            company.setName(companyInfo.getName());
        }
        if (!companyInfo.getEmployees().isEmpty() && companyInfo.getEmployees() != null) {
            company.setEmployees(companyInfo.getEmployees());
        }
        return company;
    }

    public List<Company> getAllCompany() {
        return companyRepositoryRepository.findAll();
    }
    public Company findCompanyBbyId(int companyId) {//TODO: By
        return companyRepositoryRepository.findById(companyId).orElseThrow(()->new NoEmployeeWithIDException("No company found with this ID"));
    }
}
