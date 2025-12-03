package com.rsbusinesstech.rsbusinesstech_backend.customer.service;

import com.rsbusinesstech.rsbusinesstech_backend.commonUtils.CloudinaryService;
import com.rsbusinesstech.rsbusinesstech_backend.customer.dto.CustomerDTO;
import com.rsbusinesstech.rsbusinesstech_backend.customer.utils.JsonFileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService
{
    @Autowired
    CloudinaryService cloudinaryService;

    //This method will give all the customers for a particular Type.
    public List<CustomerDTO> getAllCustomers(){
        return JsonFileUtil.readCustomers();
    }


    //This method will add a Property by it's Type.
    public CustomerDTO addCustomer(CustomerDTO customer){

        List<CustomerDTO> customers = JsonFileUtil.readCustomers();
        long nextId = customers.stream().mapToLong(CustomerDTO::getId).max().orElse(0)+1;

        customer.setId(nextId);

        customers.add(customer);

        JsonFileUtil.writeCustomers(customers);
        return customer;
    }

    //This method will update a Property by it's Type.
    public CustomerDTO updateCustomer(CustomerDTO updatedCustomer, Long id){
        List<CustomerDTO> customers = JsonFileUtil.readCustomers();

        for(int i = 0; i < customers.size() ; i++){
            if(customers.get(i).getId().equals(id)){
                updatedCustomer.setId(id);

                // Only delete and replace images if new images are provided.
                if(updatedCustomer.getImageUrl() != null && !updatedCustomer.getImageUrl().isEmpty()) {
                    if(customers.get(i).getImagePublicId() != null && !customers.get(i).getImagePublicId().isEmpty()) {
                        cloudinaryService.deleteImage(customers.get(i).getImagePublicId());
                    }
                } else {
                    // Keep existing images if no new images are uploaded.
                    updatedCustomer.setImageUrl(customers.get(i).getImageUrl());
                    updatedCustomer.setImagePublicId(customers.get(i).getImagePublicId());
                }

                customers.set(i,updatedCustomer);
                JsonFileUtil.writeCustomers(customers);
              return updatedCustomer;
            }
        }
        throw new RuntimeException("Property not found");
    }

    // This method will delete a Property by its Type & Id, including Cloudinary images.
    public boolean deleteCustomer(Long id) {
        List<CustomerDTO> customers = JsonFileUtil.readCustomers();

        // Find the customer to delete.
        CustomerDTO customerToDelete = customers.stream()
                .filter(customer -> customer.getId().equals(id))
                .findFirst()
                .orElse(null);

        if (customerToDelete != null) {
            // 1. Delete existing images from Cloudinary.
            if (customerToDelete.getImagePublicId() != null && !customerToDelete.getImagePublicId().isEmpty()) {
                cloudinaryService.deleteImage(customerToDelete.getImagePublicId());
            }

            // 2. Remove property from list.
            customers.remove(customerToDelete);

            // 3. Write updated list back to JSON.
            JsonFileUtil.writeCustomers(customers);
            return true;
        }
        return false; // Property not found.
    }

}
