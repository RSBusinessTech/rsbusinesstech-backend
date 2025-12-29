package com.rsbusinesstech.rsbusinesstech_backend.propertyManagementSystem.customer.service;

import com.rsbusinesstech.rsbusinesstech_backend.commonUtils.CloudinaryService;
import com.rsbusinesstech.rsbusinesstech_backend.propertyManagementSystem.customer.dto.CustomerDTO;
import com.rsbusinesstech.rsbusinesstech_backend.propertyManagementSystem.property.dto.PropertyDTO;
import com.rsbusinesstech.rsbusinesstech_backend.utils.JsonFileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CustomerService
{
    @Autowired
    JsonFileUtil jsonFileUtil;

    @Autowired
    CloudinaryService cloudinaryService;

    //This method will give all the customers for a particular agent.
    public List<CustomerDTO> getAllCustomers(String agentId){
        if(agentId == null || agentId.length() == 0){
            throw new IllegalArgumentException("AgentId is required");
        }
        List<CustomerDTO> allCustomers = Optional.ofNullable(jsonFileUtil.readCustomers()).orElse(Collections.emptyList());
        List<CustomerDTO> agentCustomers = allCustomers.stream().filter(customerDTO -> agentId.equalsIgnoreCase(customerDTO.getAgentId())).collect(Collectors.toList());
        return agentCustomers;
    }


    //This method will add a Property by it's Type.
    public CustomerDTO addCustomer(CustomerDTO customer){

        if(customer == null){
           throw new IllegalArgumentException("Customer cann't be null");
        }
        if(customer.getAgentId() == null || customer.getAgentId().length() == 0){
            throw new IllegalArgumentException("AgentId is required");
        }

        List<CustomerDTO> customers = jsonFileUtil.readCustomers();
        long nextId = customers.stream().mapToLong(CustomerDTO::getId).max().orElse(0)+1;
        customer.setId(nextId);
        customer.setCreatedAt(ZonedDateTime.now(ZoneId.of("Asia/Kuala_Lumpur")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss a")));  //a → AM/PM marker.
        customer.setCreatedBy(customer.getAgentId());
        setRentalStartDueDates(customer);

        customers.add(customer);

        jsonFileUtil.writeCustomers(customers);
        return customer;
    }

    //This method will update a Property by it's Type.
    public CustomerDTO updateCustomer(CustomerDTO updatedCustomer, Long id){
        List<CustomerDTO> customers = jsonFileUtil.readCustomers();

        for(int i = 0; i < customers.size() ; i++){
            if(customers.get(i).getId().equals(id)){
                updatedCustomer.setId(id);
                updatedCustomer.setUpdatedAt(ZonedDateTime.now(ZoneId.of("Asia/Kuala_Lumpur")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss a"))); //a → AM/PM marker.
                updatedCustomer.setUpdatedBy(updatedCustomer.getAgentId());

                // Only delete and replace images if new images are provided.
                if(updatedCustomer.getImageUrl() != null && !updatedCustomer.getImageUrl().isEmpty()) {
                    if(customers.get(i).getImagePublicId() != null && !customers.get(i).getImagePublicId().isEmpty()) {
                        cloudinaryService.deleteFile(customers.get(i).getImagePublicId());
                    }
                } else {
                    // Keep existing images if no new images are uploaded.
                    updatedCustomer.setImageUrl(customers.get(i).getImageUrl());
                    updatedCustomer.setImagePublicId(customers.get(i).getImagePublicId());
                }
                setRentalStartDueDates(updatedCustomer);
                customers.set(i,updatedCustomer);
                jsonFileUtil.writeCustomers(customers);
              return updatedCustomer;
            }
        }
        throw new RuntimeException("Property not found");
    }

    // This method will delete a Property by its Type & Id, including Cloudinary images.
    public boolean deleteCustomer(Long id) {
        List<CustomerDTO> customers = jsonFileUtil.readCustomers();

        // Find the customer to delete.
        CustomerDTO customerToDelete = customers.stream()
                .filter(customer -> customer.getId().equals(id))
                .findFirst()
                .orElse(null);

        if (customerToDelete != null) {
            // 1. Delete existing images from Cloudinary.
            if (customerToDelete.getImagePublicId() != null && !customerToDelete.getImagePublicId().isEmpty()) {
                cloudinaryService.deleteFile(customerToDelete.getImagePublicId());
            }

            // 2. Remove property from list.
            customers.remove(customerToDelete);

            // 3. Write updated list back to JSON.
            jsonFileUtil.writeCustomers(customers);
            return true;
        }
        return false; // Property not found.
    }

    public void setRentalStartDueDates(CustomerDTO customer){
        if(customer != null && StringUtils.hasText(customer.getContractStartDate()) && StringUtils.hasText(customer.getContractEndDate())){
            LocalDate contractStartDate =  LocalDate.parse(customer.getContractStartDate(), DateTimeFormatter.ISO_LOCAL_DATE); // DateTimeFormatter.ISO_LOCAL_DATE:it formats the date without time component.
            LocalDate contractEndDate =  LocalDate.parse(customer.getContractEndDate(), DateTimeFormatter.ISO_LOCAL_DATE);

            YearMonth currentYearMonth = YearMonth.now();
            int rentalDay = contractStartDate.getDayOfMonth();

            int minRentalDay = Math.min(rentalDay,currentYearMonth.lengthOfMonth()); //Handling short months (Feb, etc.)
            LocalDate rentalStartDate = currentYearMonth.atDay(minRentalDay);

            if(!rentalStartDate.isBefore(contractStartDate) && !rentalStartDate.isAfter(contractEndDate)){
                LocalDate rentalDueDate = rentalStartDate.plusDays(customer.getGracePeriodInDays());

                // Only reset isRentalPaid if null or empty or rentalStartDate changed (new rental-cycle).
                if(customer.getIsRentalPaid() == null || customer.getIsRentalPaid().isEmpty() || rentalStartDateHasChanged(customer, rentalStartDate)) {
                    customer.setIsRentalPaid("No");
                }
                customer.setRentalStartDate(rentalStartDate.toString());
                customer.setRentalDueDate(rentalDueDate.toString());
            }
        }
    }

    public Map<String,Long> getCustomersInfo(String agentId){
        Map<String,Long> customersInfoMap = new HashMap<>();
        List<CustomerDTO> customers = jsonFileUtil.readCustomers();

        long totalTenants = customers.stream()
                .filter(customerDTO -> "Rental".equalsIgnoreCase(customerDTO.getPropertyType()) && agentId.equalsIgnoreCase(customerDTO.getAgentId()))
                .count();

        long totalBuyers = customers.stream()
                .filter(customerDTO -> "Buy".equalsIgnoreCase(customerDTO.getPropertyType()) && agentId.equalsIgnoreCase(customerDTO.getAgentId()))
                .count();

        customersInfoMap.put("totalTenants",totalTenants);
        customersInfoMap.put("totalBuyers",totalBuyers);

        return customersInfoMap;
    }

    // Helper method to detect if rentalStartDate changed from previous (new rental-cycle).
    private boolean rentalStartDateHasChanged(CustomerDTO customer, LocalDate newRentalStartDate){
        if(customer.getRentalStartDate() == null) return true;
        LocalDate oldDate = LocalDate.parse(customer.getRentalStartDate());
        return !oldDate.equals(newRentalStartDate);
    }
}
