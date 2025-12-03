package com.rsbusinesstech.rsbusinesstech_backend.commonUtils;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CloudinaryService {

    @Autowired
    private Cloudinary cloudinary;

    public Map<String,List<String>> uploadFiles(List<MultipartFile> files){
        Map<String,List<String>> responseMap = new HashMap<>();
        List<String> urls = new ArrayList<>();
        List<String> publicIDs = new ArrayList<>();

        Map<String, Object> map = new HashMap<>();
        map.put("resource_type", "auto");           //resource_type → tells Cloudinary to automatically detect whether the file is an image or video.
        map.put("folder", "Vyen_Property_Advisor"); //folder → tells Cloudinary which folder to store the file in.

        try{
            for(MultipartFile file: files){
                Map uploadResult = cloudinary.uploader().upload(file.getBytes(),map);
                String url = uploadResult.get("secure_url").toString();
                String publicId = uploadResult.get("public_id").toString();

                urls.add(url);
                publicIDs.add(publicId);
            }
            responseMap.put("urls",urls);
            responseMap.put("publicIDs",publicIDs);

             return responseMap;
        }catch(Exception e){
            throw new RuntimeException("Image upload failed: " + e.getMessage(), e);
        }
    }

    public void deleteImages(List<String> publicIDs){
        try{
            for(String publicID: publicIDs){
                cloudinary.uploader().destroy(publicID,new HashMap<>());
            }
        }
        catch(Exception e){
            throw new RuntimeException("Failed to delete image: " + e.getMessage(), e);
        }
    }

    public void deleteImage(String publicID){
        try{
                cloudinary.uploader().destroy(publicID,new HashMap<>());
        }
        catch(Exception e){
            throw new RuntimeException("Failed to delete image: " + e.getMessage(), e);
        }
    }
}
