package com.rsbusinesstech.rsbusinesstech_backend.commonUtils;

import com.cloudinary.Cloudinary;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CloudinaryService {

    @Autowired
    private Cloudinary cloudinary;

    public Map<String,List<String>> uploadFiles(List<MultipartFile> files, String folder){
        Map<String,List<String>> responseMap = new HashMap<>();
        List<String> urls = new ArrayList<>();
        List<String> publicIDs = new ArrayList<>();

        Map<String, Object> map = new HashMap<>();
        map.put("resource_type", "auto");           //resource_type → tells Cloudinary to automatically detect whether the file is an image or video.
        map.put("folder", folder);                 //folder → tells Cloudinary which folder to store the file in.

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

    public Map<String,String> uploadFile(MultipartFile file, String folder){
        Map<String,String> responseMap = new HashMap<>();
        String url = null;
        String publicID = null;

        Map<String, Object> map = new HashMap<>();
        map.put("resource_type", "auto");           //resource_type → tells Cloudinary to automatically detect whether the file is an image or video.
        map.put("folder", folder); //folder → tells Cloudinary which folder to store the file in.

        try{
             Map uploadResult = cloudinary.uploader().upload(file.getBytes(),map);
             url = uploadResult.get("secure_url").toString();
             publicID = uploadResult.get("public_id").toString();

             responseMap.put("url",url);
             responseMap.put("publicID",publicID);

            return responseMap;
        }catch(Exception e){
            throw new RuntimeException("Image upload failed: " + e.getMessage(), e);
        }
    }


    //Upload non-media files like JSON,TXT,CSV,PDF etc.
    public Map<String,String> uploadRawFile(File file, String folder, String fileName){
        Map<String,String> responseMap = new HashMap<>();
        String url = null;
        String publicID = null;

        Map<String, Object> map = new HashMap<>();
        map.put("resource_type", "raw");    //resource_type → tells Cloudinary to automatically detect whether the file is a media if we ise "auto",it works well for media file like images,videso etc but for non-media files like json we need "raw" which tells cloudinary that it is a raw file so don't treat it as media file.
        map.put("folder", folder);         //folder → tells Cloudinary which folder to store the file in.
        map.put("public_id", fileName);

        try{
             Map uploadResult = cloudinary.uploader().upload(file,map);
             url = uploadResult.get("secure_url").toString();
             publicID = uploadResult.get("public_id").toString();

             responseMap.put("url",url);
             responseMap.put("publicID",publicID);
            return responseMap;
        }catch(Exception e){
            throw new RuntimeException("File upload failed: " + e.getMessage(), e);
        }
    }

    public void deleteFiles(List<String> publicIDs){
        try{
            for(String publicID: publicIDs){
                cloudinary.uploader().destroy(publicID,new HashMap<>());
            }
        }
        catch(Exception e){
            throw new RuntimeException("Failed to delete image: " + e.getMessage(), e);
        }
    }

    public void deleteFile(String publicID){
        try{
            // Cloudinary raw public_id should not contain file extension.
            if (publicID != null && publicID.endsWith(".json")) {
                publicID = publicID.substring(0, publicID.length() - 5); // remove only last ".json"
            }
            cloudinary.uploader().destroy(publicID,new HashMap<>());
        }
        catch(Exception e){
            throw new RuntimeException("Failed to delete image: " + e.getMessage(), e);
        }
    }

    public void deleteRawFile(String publicID){
        try{
            if (StringUtils.isEmpty(publicID)) {
                return;
            }
            Map<String, Object> options = new HashMap<>();
            options.put("resource_type", "raw");

            cloudinary.uploader().destroy(publicID, options);
        } catch(Exception e){
            throw new RuntimeException("Failed to delete file: " + e.getMessage(), e);
        }
    }

}
