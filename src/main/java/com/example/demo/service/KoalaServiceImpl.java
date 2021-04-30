package com.example.demo.service;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.app.KoalaInsertForm;
import com.example.demo.entity.Koala;
import com.example.demo.entity.KoalaImage;
import com.example.demo.repository.KoalaDao;
import com.example.demo.repository.KoalaImageDao;

@Service
public class KoalaServiceImpl implements KoalaService{

	private final KoalaDao dao;
	private final KoalaImageDao koalaImageDao;
	
	@Autowired
	public KoalaServiceImpl(KoalaDao dao,KoalaImageDao koalaImageDao) {
		this.dao = dao;
		this.koalaImageDao = koalaImageDao;
	}

	@Override
	public List<Koala> getAll() {
		return dao.getAll();
	}
	
	Koala koala;
	@Override
	@Transactional
	public void insert(KoalaInsertForm form){
		Koala koala = new Koala();
		koala.setName(form.getName());
		koala.setSex(form.getSex());
		Date birthDate = getDate(form.getBirthYear(),form.getBirthMonth(),form.getBirthDay());
		if(birthDate != null) {
			koala.setBirthdate(birthDate);
		}
		koala.setIs_alive(form.getIs_alive());
		Date deathDate = getDate(form.getDeathYear(),form.getDeathMonth(),form.getDeathDay());
		if(deathDate != null) {
			koala.setDeathdate(deathDate);
		}
		koala.setZoo(form.getZoo());
		koala.setMother(form.getMother());
		koala.setFather(form.getFather());
		koala.setDetails(form.getDetails());
		koala.setFeature(form.getFeature());
		int insertKoala_id = dao.insert(koala);
		//追加するコアラに画像が添付されているか確認
		boolean koalaImageInsetFlag =false;
		for (MultipartFile koalaImgae : form.getKoalaImage()) {
			if(koalaImgae.getSize() != 0){
				koalaImageInsetFlag = true;
				break;
			}
		}
		if(koalaImageInsetFlag) {
			inserKoalaImage(insertKoala_id,form.getKoalaImage());
		}
	}
	
	public Date getDate(String year , String month , String day){
		String hyphen = "-";
		//年、月、日の中に"--"が含まれていたら、"9999-01-01"を返す。
		String dummyValue = "0";
		if(year.equals(dummyValue) || month.equals(dummyValue) || day.equals(dummyValue)) {
			year = "9999";
			month = "01";
			day = "01";
		}
		StringBuilder sb = new StringBuilder();
		sb.append(year);
		sb.append(hyphen);
		sb.append(month);
		sb.append(hyphen);
		sb.append(day);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date date = dateFormat.parse(sb.toString());
			return date;
		} catch (ParseException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public Koala findById(Long id) {
		return dao.findById(id);
	}
	
	@Override
	public void update(KoalaInsertForm form) {
		Koala koala = new Koala();
		koala.setKoala_id(form.getKoala_id());
		koala.setName(form.getName());
		koala.setSex(form.getSex());
		Date birthDate = getDate(form.getBirthYear(),form.getBirthMonth(),form.getBirthDay());
		if(birthDate != null) {
			koala.setBirthdate(birthDate);
		}
		koala.setIs_alive(form.getIs_alive());
		Date deathDate = getDate(form.getDeathYear(),form.getDeathMonth(),form.getDeathDay());
		if(deathDate != null) {
			koala.setDeathdate(deathDate);
		}
		koala.setZoo(form.getZoo());
		koala.setMother(form.getMother());
		koala.setFather(form.getFather());
		koala.setDetails(form.getDetails());
		koala.setFeature(form.getFeature());
		dao.update(koala);
		if(form.getKoalaImage()	!= null) {
			inserKoalaImage(form.getKoala_id(),form.getKoalaImage());
		}
		if(form.getDeleteKoalaImageFiles() != null) {
			deleteKoalaImage(form.getDeleteKoalaImageFiles(),form.getKoala_id());
		}
	}
	
	@Override
	@Transactional
	public void delete(int koala_id) {
		dao.delete(koala_id);
		deleteDirs(koala_id);
	}
	
	@Override
	public void inserKoalaImage(int koala_id, List<MultipartFile> koalaImageLust) {
		KoalaImage koalaImage = new KoalaImage();
		koalaImage.setKoala_id(koala_id);
		
		// 写真を格納するファイルのパス
        String filePath =  getkoalaUploadDir(koala_id);
        
        // アップロードファイルを格納するディレクトリを作成する
        
        File uploadDir = mkdirs(filePath);
        
		for(MultipartFile inputImage:koalaImageLust) {
			if(inputImage.getSize() != 0) {
				//ファイルの拡張子を取得する
				String fileExtension = inputImage.getOriginalFilename().substring(inputImage.getOriginalFilename().lastIndexOf("."));
				
				koalaImage.setFiletype(fileExtension);
				
				int koalaImageId = koalaImageDao.insert(koalaImage);
				try {
		            // アップロードファイルを置く
		            File uploadFile =
		                    new File(uploadDir.getPath() + File.separator + String.valueOf(koalaImageId) + fileExtension);
		            
		            byte[] bytes = fileResize(inputImage.getBytes(),fileExtension.substring(1));
		            
		            if(bytes == null) {
		            	bytes = inputImage.getBytes();
		            }
		            
		            BufferedOutputStream uploadFileStream =
		                    new BufferedOutputStream(new FileOutputStream(uploadFile));
		           
		            uploadFileStream.write(bytes);
		            
		            uploadFileStream.close();
		            
		        } catch (Exception e) {
		            // 異常終了時の処理
		        	continue;
		        } catch (Throwable t) {
		            // 異常終了時の処理
		        	continue;
		        }
			}
		}
	}
	
	@Override
	public void deleteKoalaImage(String KoalaImageFilesString,int koala_id) {
		String[] koalaImageFiles = KoalaImageFilesString.split(",");
		List<Integer> koalaImageIds = new ArrayList<Integer>();
		for(String koalaImageFile:koalaImageFiles) {
			int dot = koalaImageFile.lastIndexOf('.');
			String stringKoalaImageId = (dot == -1) ? koalaImageFile : koalaImageFile.substring(0, dot);
			Integer koalaImageId = Integer.valueOf(stringKoalaImageId);
			koalaImageIds.add(koalaImageId);
		}
		koalaImageDao.delete(koalaImageIds);
		deleteFiles(koalaImageFiles,koala_id);
	}
	
	private File  mkdirs(String filePath) {
		File uploadDir = new File(filePath);
		
		// フォルダ作成
        uploadDir.mkdirs();
        
        return uploadDir;
	}
	
	private void  deleteDirs(int koala_id) {
		// 写真を格納するファイルのパス
		File deleteDir = new File(getkoalaUploadDir(koala_id));
		
		 //ディレクトリ内の一覧を取得
        File[] files = deleteDir.listFiles();
        
        //存在するファイル数分ループして再帰的に削除
        for(int i=0; i<files.length; i++) {
            files[i].delete();
        }
		// フォルダ削除
		deleteDir.delete();
	}
	
	private void  deleteFiles(String[] koalaImageFiles,int koala_id) {
		String deleteFilePath = getkoalaUploadDir(koala_id);
		for(String koalaImageFile : koalaImageFiles) {
			File deleteFile = new File(deleteFilePath + File.separator + koalaImageFile);
			deleteFile.delete();
		}
	}
	
	private String getkoalaUploadDir(int koala_id) {
		// 写真を格納するファイルのパス
        StringBuffer dirPath = new StringBuffer("images").append(File.separator)
                                        .append("koala").append(File.separator)
        								.append(String.valueOf(koala_id));
        
        return dirPath.toString();
    }
	
	private byte[] fileResize(byte[] originalImage ,String originalExtension) {
		BufferedImage src = null;
		BufferedImage dst = null;
		AffineTransformOp xform = null;
		
		InputStream is = new ByteArrayInputStream(originalImage);
		try {
			src = ImageIO.read( is );
			int width = src.getWidth();    //. オリジナル画像の幅
			int height = src.getHeight();  //. オリジナル画像の高さ
			
			int w = 200; //. 幅をこの数値に合わせて調整する
			
			int new_height = w * height / width;
			int new_width = w;
			
			xform = new AffineTransformOp( AffineTransform.getScaleInstance( ( double )new_width / width,
					( double )new_height / height ), AffineTransformOp.TYPE_BILINEAR );
			dst = new BufferedImage( new_width, new_height, src.getType() );
			xform.filter(src, dst );
			
			//. 変換後のバイナリイメージを byte 配列に再格納
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(dst, originalExtension, baos);
			originalImage = baos.toByteArray();
			  return originalImage;
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			return null;
		}
	}
		
}
