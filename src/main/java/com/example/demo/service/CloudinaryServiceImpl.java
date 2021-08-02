package com.example.demo.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Service
public class CloudinaryServiceImpl implements CloudinaryService{
	@Autowired
    @Qualifier("com.cloudinary.cloud_name")
    String mCloudName;

    @Autowired
    @Qualifier("com.cloudinary.api_key")
    String mApiKey;

    @Autowired
    @Qualifier("com.cloudinary.api_secret")
    String mApiSecret;
    
    @Override
    public Map uploadAnimalImage(File uploadFile,int animal_id) {
        String uploadCloudinaryFolderPath = getanimalUploadDir(animal_id) +"/";
        Map optionMap = ObjectUtils.asMap("folder",uploadCloudinaryFolderPath);
        optionMap.put("use_filename", true);
        optionMap.put("unique_filename", false);
        try {
        	Cloudinary cloudinary = new Cloudinary("cloudinary://"+mApiKey+":"+mApiSecret+"@"+mCloudName);
        	Map resultMap = cloudinary.uploader().upload(uploadFile, optionMap);
			return resultMap;
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			return null;
		}
    }
    @Override
    public Map uploadAnimalProfileImage(File uploadFile,int animal_id) {
        String uploadCloudinaryFolderPath = getanimalUploadDir(animal_id) +"/profile/" ;
        Map optionMap = ObjectUtils.asMap("folder",uploadCloudinaryFolderPath);
        optionMap.put("use_filename", true);
        optionMap.put("unique_filename", false);
        try {
        	Cloudinary cloudinary = new Cloudinary("cloudinary://"+mApiKey+":"+mApiSecret+"@"+mCloudName);
        	Map resultMap = cloudinary.uploader().upload(uploadFile, optionMap);
			return resultMap;
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			return null;
		}
    }
    
    @Override
	public List<String> deleteAnimalImage(String[] animalImageFiles,int animal_id) {
    	List<String> animalImageIds = new ArrayList<String>();
		List<String> publicIds = new ArrayList<String>();
		for(String animalImageFile:animalImageFiles) {
			int dot = animalImageFile.lastIndexOf('.');
			String stringAnimalImageId = (dot == -1) ? animalImageFile : animalImageFile.substring(0, dot);
			String animalImageId = stringAnimalImageId;
			animalImageIds.add(animalImageId);
			String publicId = getanimalUploadDir(animal_id) +  "/" + animalImageId;
			publicIds.add(publicId);
		}
		
		try {
        	Cloudinary cloudinary = new Cloudinary("cloudinary://"+mApiKey+":"+mApiSecret+"@"+mCloudName);
			cloudinary.api().deleteResources(publicIds, ObjectUtils.emptyMap());
			return animalImageIds;
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			return null;
		}
    }
    
    @Override
	public void  deleteDirs(int post_id) {
		try {
			Cloudinary cloudinary = new Cloudinary("cloudinary://"+mApiKey+":"+mApiSecret+"@"+mCloudName);
			Map resultmap = cloudinary.search().expression("folder:" + getPostUploadDir(post_id)).execute();
			List<Map> resultList = (List<Map>) resultmap.get("resources");
			List<String> publicIds = new ArrayList<String>();
			for(Map result:resultList) {
				 publicIds.add((String)result.get("public_id"));
			}
			cloudinary.api().deleteResources(publicIds, ObjectUtils.emptyMap())	;
			cloudinary.api().deleteFolder(getPostUploadDir(post_id), ObjectUtils.emptyMap());
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
    }
    
    @Override
	public	Map uploadUserProfileImage(File uploadFile,int user_id) {
    	
	    String uploadCloudinaryFolderPath = getUserUploadDir(user_id) +"/profile/" ;
	    Map optionMap = ObjectUtils.asMap("folder",uploadCloudinaryFolderPath);
	    optionMap.put("use_filename", true);
	    optionMap.put("unique_filename", false);
	    try {
	    	Cloudinary cloudinary = new Cloudinary("cloudinary://"+mApiKey+":"+mApiSecret+"@"+mCloudName);
	    	Map resultMap = cloudinary.uploader().upload(uploadFile, optionMap);
			return resultMap;
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			return null;
		}
	}
    
    @Override
	public	Map uploadPostImage(File uploadFile,int post_id) {
    	
	    String uploadCloudinaryFolderPath = getPostUploadDir(post_id);
	    Map optionMap = ObjectUtils.asMap("folder",uploadCloudinaryFolderPath);
	    optionMap.put("use_filename", true);
	    optionMap.put("unique_filename", false);
	    try {
	    	Cloudinary cloudinary = new Cloudinary("cloudinary://"+mApiKey+":"+mApiSecret+"@"+mCloudName);
	    	Map resultMap = cloudinary.uploader().upload(uploadFile, optionMap);
			return resultMap;
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			return null;
		}
	}
    
    private String getanimalUploadDir(int animal_id) {
		// 写真を格納するファイルのパス
        StringBuffer dirPath = new StringBuffer("animal").append("/")
        								.append(String.valueOf(animal_id));
        
        return dirPath.toString();
    }
    
    private String getUserUploadDir(int user_id) {
		// 写真を格納するファイルのパス
        StringBuffer dirPath = new StringBuffer("user").append("/")
        								.append(String.valueOf(user_id));
        
        return dirPath.toString();
    }
    
    private String getPostUploadDir(int post_id) {
		// 写真を格納するファイルのパス
        StringBuffer dirPath = new StringBuffer("post").append("/")
        								.append(String.valueOf(post_id));
        
        return dirPath.toString();
    }
}
