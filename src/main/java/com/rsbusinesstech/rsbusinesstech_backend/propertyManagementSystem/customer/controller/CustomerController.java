package com.rsbusinesstech.rsbusinesstech_backend.propertyManagementSystem.customer.controller;

import com.rsbusinesstech.rsbusinesstech_backend.propertyManagementSystem.customer.dto.CustomerDTO;
import com.rsbusinesstech.rsbusinesstech_backend.propertyManagementSystem.customer.service.CustomerService;
import com.rsbusinesstech.rsbusinesstech_backend.commonUtils.CloudinaryService;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = {"http://localhost:4200", "https://rsbusinesstech.com"})
@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    CustomerService customerService;

    @Autowired
    CloudinaryService cloudinaryService;

    String mediaFolder = "Vyen_Property_Advisor";
    String backupFolder = "Vyen_Property_Advisor/Backup";

    @GetMapping("/getAllCustomers/{agentId}")
    public ResponseEntity<List<CustomerDTO>> getAllCustomers(@PathVariable String agentId){
        List<CustomerDTO> customers = new ArrayList<>();
        try{
            customers = customerService.getAllCustomers(agentId);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(customers);
        }
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/getAllCustomers/{agentId}/{propertyType}")
    public ResponseEntity<List<CustomerDTO>> getAllCustomersByPropertyType(@PathVariable String agentId, @PathVariable String propertyType){
        List<CustomerDTO> customers = new ArrayList<>();
        try{
            customers = customerService.getAllCustomersByPropertyType(agentId, propertyType);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(customers);
        }
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/getCustomerById/{id}/{agentId}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable String id, @PathVariable String agentId){
        List<CustomerDTO> customers = new ArrayList<>();
        CustomerDTO customer = null;
        try{
            customers = customerService.getAllCustomers(agentId);
            Optional<CustomerDTO> customerDTOOptional = customers.stream().filter(customerDTO -> String.valueOf(customerDTO.getId()).equalsIgnoreCase(id)).findFirst();
            if(customerDTOOptional.isPresent()){
                customer =  customerDTOOptional.get();
            }
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(customer);
        }
        return ResponseEntity.ok(customer);
    }

    @PostMapping("/addCustomer")
    public ResponseEntity<CustomerDTO> addCustomer(@RequestPart("customer") CustomerDTO customer,
                                                   @RequestPart (value = "images", required = false) List<MultipartFile> images){
        CustomerDTO newCustomer = null;
        try{
            // 1. Upload images to cloudinary.
             List<String> urls = new ArrayList<>();
             List<String> publicIDs = new ArrayList<>();
             if (images != null && images.size() > 0) {
                 Map<String,List<String>> responseMap  = cloudinaryService.uploadFiles(images, mediaFolder); // your cloud upload logic.
                 urls = responseMap.get("urls");
                 publicIDs = responseMap.get("publicIDs");
                 // 2. Set URLs to property
                 customer.setImageUrl(urls.get(0));
                 customer.setImagePublicId(publicIDs.get(0));
             }
            newCustomer = customerService.addCustomer(customer);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(newCustomer);
        }
        return ResponseEntity.ok(newCustomer);
    }

    @PutMapping("/updateCustomer")
    public ResponseEntity<CustomerDTO> updateCustomer(@RequestPart("customer") CustomerDTO customer, @RequestParam String id,
                                                            @RequestPart (value = "images", required = false) List<MultipartFile> images ){
        CustomerDTO updatedCustomer = null;
        try{
            if(customer != null && !StringUtils.isEmpty(id)){
                // 1. Upload images to cloudinary.
                List<String> urls = new ArrayList<>();
                List<String> publicIDs = new ArrayList<>();
                if (images != null && images.size() > 0) {
                    Map<String,List<String>> responseMap = cloudinaryService.uploadFiles(images, mediaFolder); // your cloud upload logic.
                    urls = responseMap.get("urls");
                    publicIDs = responseMap.get("publicIDs");

                    // 2. Set URLs to property
                    customer.setImageUrl(urls.get(0));
                    customer.setImagePublicId(publicIDs.get(0));
                }

                updatedCustomer = customerService.updateCustomer(customer, Long.parseLong(id));
            }
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(updatedCustomer);
        }
        return ResponseEntity.ok(updatedCustomer);
    }

    @DeleteMapping("/deleteCustomer")
    public ResponseEntity<String> deleteCustomer(@RequestParam String id){
        try{
            if(!StringUtils.isEmpty(id)){
                customerService.deleteCustomer( Long.parseLong(id));
            }
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to Delete Customer");
        }
        return ResponseEntity.ok("Customer Deleted Successfully");
    }

}
