package com.rsbusinesstech.rsbusinesstech_backend.customer.service;

import com.rsbusinesstech.rsbusinesstech_backend.commonUtils.CloudinaryService;
import com.rsbusinesstech.rsbusinesstech_backend.customer.dto.CustomerDTO;
import com.rsbusinesstech.rsbusinesstech_backend.utils.JsonFileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class CustomerService
{
    @Autowired
    JsonFileUtil jsonFileUtil;

    @Autowired
    CloudinaryService cloudinaryService;

    //This method will give all the customers for a particular Type.
    public List<CustomerDTO> getAllCustomers(){
        return jsonFileUtil.readCustomers();
    }


    //This method will add a Property by it's Type.
    public CustomerDTO addCustomer(CustomerDTO customer){

        if(customer == null){
           throw new IllegalArgumentException("Customer cann't be null");
        }

        List<CustomerDTO> customers = jsonFileUtil.readCustomers();
        long nextId = customers.stream().mapToLong(CustomerDTO::getId).max().orElse(0)+1;
        customer.setId(nextId);
        customer.setCreatedAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss a")));  //a → AM/PM marker.
        customer.setCreatedBy("admin");
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
                updatedCustomer.setUpdatedAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss a"))); //a → AM/PM marker.
                updatedCustomer.setUpdatedBy("admin");

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

                customer.setRentalStartDate(rentalStartDate.toString());
                customer.setRentalDueDate(rentalDueDate.toString());
            }
        }
    }
}
