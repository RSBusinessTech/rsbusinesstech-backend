package com.rsbusinesstech.rsbusinesstech_backend.scheduler;

import com.rsbusinesstech.rsbusinesstech_backend.commonUtils.CloudinaryService;
import com.rsbusinesstech.rsbusinesstech_backend.email.service.EmailService;
import com.rsbusinesstech.rsbusinesstech_backend.propertyManagementSystem.agent.dto.AgentDTO;
import com.rsbusinesstech.rsbusinesstech_backend.propertyManagementSystem.customer.dto.CustomerDTO;
import com.rsbusinesstech.rsbusinesstech_backend.propertyManagementSystem.email.model.RentalPaymentReminderRequest;
import com.rsbusinesstech.rsbusinesstech_backend.propertyManagementSystem.property.dto.PropertyDTO;
import com.rsbusinesstech.rsbusinesstech_backend.utils.DateFormatterUtil;
import com.rsbusinesstech.rsbusinesstech_backend.utils.JsonFileUtil;
import jakarta.annotation.PostConstruct;
import jakarta.mail.MessagingException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.*;

@Service
public class JsonUploadScheduler {
    @Autowired
    CloudinaryService cloudinaryService;

    @Autowired
    JsonFileUtil jsonFileUtil;

    @Autowired
    DateFormatterUtil dateFormatterUtil;

    @Autowired
    EmailService emailService;

    @Autowired
    Validator validator;

    private static final String EXTERNAL_BASE_PATH = "/opt/app/data/"; // server path.
    private static final String LOCAL_BASE_PATH = "opt/app.data/";
    private static final String BACKUP_FOLDER = "Vyen_Property_Advisor/Backup";

    /*
       Schedule:This method will be executed once only, immediately after deployment/restarting server.
       Purpose: Create "/opt/app/data/" folder and Place JSON files there because render free instance will delete it if server restarted.
     */
    @PostConstruct
    public void performBackupOnStartup() throws IOException{
        performBackup();
    }

    /*
        0 → seconds
        0 → minutes
        23 → hour (11 PM)
        * * * → every day, every month, every year
        zone = "Asia/Kuala_Lumpur" → ensures correct Malaysian time
    */

    //Schedule: This cron job will be executed every night at 11:00PM MYT(Malaysian time).
    //Purpose: To send rental payment reminder emails to tenants who have not paid the rentals of current month.
    //@Scheduled (cron = "0 00  23 * * *", zone = "Asia/Kuala_Lumpur")
    public void sendRentalPaymentReminderEmails() throws IOException {
        sendRentalPaymentReminderEmail();
    }

    //Schedule: This cron job will be executed every night at 11:30PM MYT(Malaysian time).
    //Purpose: Take backup from Render server and upload in cloudinary.
    @Scheduled (cron = "0 30 23 * * *", zone = "Asia/Kuala_Lumpur")
    public void uploadJsonFile() throws IOException {
        performBackup();
    }

    private void performBackup() throws IOException{
        System.out.println("======= Scheduler started at "+ new Date() +" ======");

        int success = 0;
        int failed = 0;
        List<String> files = Arrays.asList("buy","commercial","mm2h","newprojects","rent","customer","owner","agent");

        for(String file: files){
            Map<String, String> responseMap = new HashMap<>();
            String publicID = "Vyen_Property_Advisor/Backup/"+ file;
            String externalFilePath = EXTERNAL_BASE_PATH + file.toLowerCase() + ".json";

            cloudinaryService.deleteRawFile(publicID);
            System.out.println("Deleted old file: "+publicID);

            File folder = new File(EXTERNAL_BASE_PATH);
            if(!folder.exists()){
                folder.mkdirs();
            }

            File externalFile = new File(externalFilePath);

            if(!externalFile.exists()){
                Resource resource = new ClassPathResource("opt/app.data/" + file.toLowerCase() + ".json");
                if(resource.exists()) {
                    Files.copy(resource.getInputStream(), externalFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    System.out.println("Copied " + file + ".json to " + EXTERNAL_BASE_PATH);
                } else {
                    System.out.println("Backup resource not found: " + file + ".json");
                }
            }else if (externalFile.exists()) {
                // Read from external file if it exists.
                try{
                    responseMap = cloudinaryService.uploadRawFile(externalFile, BACKUP_FOLDER, file);
                    success++;
                }catch(Exception e){
                    failed++;
                    System.out.println("Upload failed for "+ file + ": "+e.getMessage());
                }
            } else {
                // Fallback: read from classpath resource.
                String localFilePath = LOCAL_BASE_PATH + file.toLowerCase() + ".json";
                Resource resource = new ClassPathResource(localFilePath);
                if (resource.exists()) {
                    File localFile = null;  // This works only if running unpackaged (from IDE), not in JAR.
                    try {
                        localFile = resource.getFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if(localFile != null){
                        try{
                            responseMap = cloudinaryService.uploadRawFile(localFile, BACKUP_FOLDER, file);
                            success++;
                        }catch(Exception e){
                            failed++;
                            System.out.println("Upload failed for "+ file + ": "+e.getMessage());
                        }
                    }
                } else {
                    System.out.println("Resource " + localFilePath + " does not exist");
                    failed++;
                }
            }
            String newPublicID = responseMap.get("publicID");
            System.out.println("newPublicID: "+ newPublicID);
        }
        System.out.println("success = "+ success + ", failed = "+ failed);
        System.out.println("======= Scheduler ended at "+ new Date() +" ======");
    }

    private void sendRentalPaymentReminderEmail() throws IOException{
        System.out.println("======= Rental Payment Reminder Scheduler started at "+ new Date() +" ======");

        int success = 0;
        int failed = 0;

        List<PropertyDTO> allRentalProperties = Optional.ofNullable(jsonFileUtil.readPropertiesByType("rent")).orElse(Collections.emptyList());
        List<CustomerDTO> customers = Optional.ofNullable(jsonFileUtil.readCustomers()).orElse(Collections.emptyList());
        List<AgentDTO> agents = Optional.ofNullable(jsonFileUtil.readAgents()).orElse(Collections.emptyList());

        for(CustomerDTO customer: customers){
           if(customer != null && "Rental".equalsIgnoreCase(customer.getPropertyType())
                   && "No".equalsIgnoreCase(customer.getIsRentalPaid())
                   && dateFormatterUtil.isBeforeOrToday(customer.getRentalStartDate())){
               Optional<PropertyDTO> rentalPropertyOptional =  allRentalProperties
                                                              .stream()
                                                              .filter(property -> Objects.equals(customer.getPropertyId(), property.getId()) &&
                                                                                  Objects.equals(customer.getAgentId(), property.getAgentId()))
                                                              .findFirst();

               Optional<AgentDTO> agentOptional = agents
                                                  .stream().filter(agent -> agent != null && Objects.equals(agent.getId(), customer.getAgentId()))
                                                  .findFirst();

               RentalPaymentReminderRequest rentalPaymentReminderRequest = new RentalPaymentReminderRequest();
               rentalPaymentReminderRequest.setCustomerName(customer.getFullName());
               rentalPaymentReminderRequest.setCustomerEmail(customer.getEmail());

               rentalPropertyOptional.ifPresent(property -> {
                   rentalPaymentReminderRequest.setPropertyName(property.getName());
               });

               agentOptional.ifPresent(agent -> {
                 rentalPaymentReminderRequest.setAgentName(agent.getFullName());
                 rentalPaymentReminderRequest.setAgentEmail(agent.getEmail());
                 rentalPaymentReminderRequest.setAgentMobileNo(agent.getMobileNumber());
               });

               rentalPaymentReminderRequest.setRentalAmount(customer.getRentalAmount());
               rentalPaymentReminderRequest.setRentalStartDate(dateFormatterUtil.getFormattedDate(customer.getRentalStartDate()));
               rentalPaymentReminderRequest.setRentalDueDate(dateFormatterUtil.getFormattedDate(customer.getRentalDueDate()));

               try {
                   Set<ConstraintViolation<RentalPaymentReminderRequest>> violations =  validator.validate(rentalPaymentReminderRequest);
                   if(violations.isEmpty()){
                       emailService.sendRentalPaymentReminderEmail(rentalPaymentReminderRequest);
                       success++;
                   }else {
                       // 1. Log a warning
                       System.out.println("Skipping email for customer " + customer.getEmail() + " due to validation errors:");

                       // 2. Print all violations
                       for (ConstraintViolation<RentalPaymentReminderRequest> violation : violations) {
                           System.out.println(" - " + violation.getPropertyPath() + ": " + violation.getMessage());
                       }
                       failed++;
                   }
               } catch (MessagingException e) {
                   failed++;
                   e.printStackTrace();
                   System.out.println("Upload failed for "+ customer.getEmail() + ": "+e.getMessage());
               }
           }
        }
        System.out.println("======= Rental Payment Reminder Scheduler ended at "+ new Date() +" ======");
        System.out.println("Emails sent successfully: " + success);
        System.out.println("Emails failed/skipped: " + failed);
    }
}
