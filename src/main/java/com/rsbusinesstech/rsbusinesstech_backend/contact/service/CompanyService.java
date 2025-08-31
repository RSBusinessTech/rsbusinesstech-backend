//package com.rsbusinesstech.rsbusinesstech_backend.contact.service;
//
//import com.rsbusinesstech.rsbusinesstech_backend.contact.model.Company;
//import com.rsbusinesstech.rsbusinesstech_backend.contact.repository.CompanyRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class CompanyService {
//
//    @Autowired
//    CompanyRepository companyRepository;
//
//    public String saveCompany(Company company){
//        companyRepository.save(company);
//        return "Company details saved successfully...";
//    }
//
//    public String getCompanyEmail(){
//        Optional<Company> optionalCompany = companyRepository.findById(1);
//        if(optionalCompany.isPresent()){
//            return optionalCompany.get().getEmail();
//        }
//        return "Failed to fetch email";
//    }
//}
