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
    public Map uploadKoalaImage(File uploadFile,int koala_id) {
        String uploadCloudinaryFolderPath = getkoalaUploadDir(koala_id) +"/";
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
	public List<String> deleteKoalaImage(String[] koalaImageFiles,int koala_id) {
    	List<String> koalaImageIds = new ArrayList<String>();
		List<String> publicIds = new ArrayList<String>();
		for(String koalaImageFile:koalaImageFiles) {
			int dot = koalaImageFile.lastIndexOf('.');
			String stringKoalaImageId = (dot == -1) ? koalaImageFile : koalaImageFile.substring(0, dot);
			String koalaImageId = stringKoalaImageId;
			koalaImageIds.add(koalaImageId);
			String publicId = getkoalaUploadDir(koala_id) +  "/" + koalaImageId;
			publicIds.add(publicId);
		}
		
		try {
        	Cloudinary cloudinary = new Cloudinary("cloudinary://"+mApiKey+":"+mApiSecret+"@"+mCloudName);
			cloudinary.api().deleteResources(publicIds, ObjectUtils.emptyMap());
			return koalaImageIds;
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			return null;
		}
    }
    
    @Override
	public void  deleteDirs(int koala_id) {
		try {
			Cloudinary cloudinary = new Cloudinary("cloudinary://"+mApiKey+":"+mApiSecret+"@"+mCloudName);
			Map resultmap = cloudinary.search().expression("folder:" + getkoalaUploadDir(koala_id)).execute();
			List<Map> resultList = (List<Map>) resultmap.get("resources");
			List<String> publicIds = new ArrayList<String>();
			for(Map result:resultList) {
				 publicIds.add((String)result.get("public_id"));
			}
			cloudinary.api().deleteResources(publicIds, ObjectUtils.emptyMap())	;
			cloudinary.api().deleteFolder(getkoalaUploadDir(koala_id), ObjectUtils.emptyMap());
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
    }
    
    private String getkoalaUploadDir(int koala_id) {
		// 写真を格納するファイルのパス
        StringBuffer dirPath = new StringBuffer("koala").append("/")
        								.append(String.valueOf(koala_id));
        
        return dirPath.toString();
    }
}
