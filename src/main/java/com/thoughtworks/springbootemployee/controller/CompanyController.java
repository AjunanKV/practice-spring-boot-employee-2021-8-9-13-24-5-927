package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.advice.entity.Company;
import com.thoughtworks.springbootemployee.advice.entity.Employee;
import com.thoughtworks.springbootemployee.mapper.CompanyMapper;
import com.thoughtworks.springbootemployee.model.*;
import com.thoughtworks.springbootemployee.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    @Autowired
    private CompanyService companyService;

    @Autowired
    CompanyMapper companyMapper;
    private List<Company> companies = new ArrayList<>();

    @GetMapping
    public List<Company> getAllCompanies() {
        return companyService.getAllCompany();
    }

    @GetMapping(path = "/{companyId}")
    public CompanyResponse findbyID(@PathVariable Integer companyId) {
        return companyMapper.toResponse(companyService.findCompanyBbyId(companyId));
    }

    @GetMapping(path = "/{companyId}/employees")
    public List<Employee> getAllEmployeesByCompanyId(@PathVariable Integer companyId) {
        return companyService.getAllEmployeesByCompanyId(companyId);
    }
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public CompanyResponse addEmployee(@RequestBody CompanyRequest companyRequest) {
        Company company = companyService.addCompany(companyMapper.toEntity(companyRequest));
        return companyMapper.toResponse(companyService.addCompany(company));
    }

    @PutMapping(path = "/{employeeId}")
    public CompanyResponse updateEmployee(@PathVariable Integer companyId, @RequestBody CompanyRequest companyRequest) {
        Company company = companyService.updateCompany(companyId, companyMapper.toEntity(companyRequest));
        return companyMapper.toResponse(companyService.addCompany(company));
    }
}
