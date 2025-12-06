package com.rsbusinesstech.rsbusinesstech_backend.scheduler;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rsbusinesstech.rsbusinesstech_backend.commonUtils.CloudinaryService;
import com.rsbusinesstech.rsbusinesstech_backend.property.dto.PropertyDTO;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class JsonUploadScheduler {

    private static final String EXTERNAL_BASE_PATH = "/opt/app/data/"; // server path.
    private static final String LOCAL_BASE_PATH = "data/";

    @Autowired
    CloudinaryService cloudinaryService;

    String backupFolder = "Vyen_Property_Advisor/Backup";

    @Scheduled (initialDelay = 0, fixedRate =  30 * 60 * 1000)
    //@Scheduled (initialDelay = 0, fixedRate = 24 * 60 * 60 * 1000)
    public void uploadJsonFile() throws IOException {
        System.out.println("======= Scheduler started at "+ new Date() +" ======");

        int success = 0;
        int failed = 0;
        List<String> files = Arrays.asList("buy","commercial","mm2h","newprojects","rent");

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
                Resource resource = new ClassPathResource("data/" + file.toLowerCase() + ".json");
                if(resource.exists()) {
                    Files.copy(resource.getInputStream(), externalFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    System.out.println("Copied " + file + ".json to " + EXTERNAL_BASE_PATH);
                } else {
                    System.out.println("Backup resource not found: " + file + ".json");
                }
            }else if (externalFile.exists()) {
                // Read from external file if it exists.
                try{
                    responseMap = cloudinaryService.uploadRawFile(externalFile, backupFolder, file);
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
                            responseMap = cloudinaryService.uploadRawFile(localFile, backupFolder, file);
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
}
