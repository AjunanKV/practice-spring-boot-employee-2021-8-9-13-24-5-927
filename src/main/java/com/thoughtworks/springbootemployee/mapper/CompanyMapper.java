package com.thoughtworks.springbootemployee.mapper;

import com.thoughtworks.springbootemployee.advice.entity.Company;
import com.thoughtworks.springbootemployee.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class CompanyMapper {
    public Company toEntity(CompanyRequest employeeRequest){
        Company company = new Company();
        BeanUtils.copyProperties(employeeRequest, company);
        return company;
    }
    public CompanyResponse toResponse(Company company){
        CompanyResponse companyResponse = new CompanyResponse();
        BeanUtils.copyProperties(company,companyResponse);
        companyResponse.setEmployeeNumber(company.getEmployees().size());
        return companyResponse;
    }
}
