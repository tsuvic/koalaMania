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

import com.example.demo.app.CoaraInsertForm;
import com.example.demo.entity.Coara;
import com.example.demo.entity.CoaraImage;
import com.example.demo.repository.CoaraDao;
import com.example.demo.repository.CoaraImageDao;

@Service
public class CoaraServiceImpl implements CoaraService{

	private final CoaraDao dao;
	private final CoaraImageDao coaraImageDao;
	
	@Autowired
	public CoaraServiceImpl(CoaraDao dao,CoaraImageDao coaraImageDao) {
		this.dao = dao;
		this.coaraImageDao = coaraImageDao;
	}

	@Override
	public List<Coara> getAll() {
		return dao.getAll();
	}
	
	Coara coara;
	@Override
	@Transactional
	public void insert(CoaraInsertForm form){
		Coara coara = new Coara();
		coara.setName(form.getName());
		coara.setSex(form.getSex());
		Date birthDate = getDate(form.getBirthYear(),form.getBirthMonth(),form.getBirthDay());
		if(birthDate != null) {
			coara.setBirthdate(birthDate);
		}
		coara.setIs_alive(form.getIs_alive());
		Date deathDate = getDate(form.getDeathYear(),form.getDeathMonth(),form.getDeathDay());
		if(deathDate != null) {
			coara.setDeathdate(deathDate);
		}
		coara.setZoo(form.getZoo());
		coara.setMother(form.getMother());
		coara.setFather(form.getFather());
		coara.setDetails(form.getDetails());
		coara.setFeature(form.getFeature());
		int insertCoara_id = dao.insert(coara);
		//追加するコアラに画像が添付されているか確認
		boolean coaraImageInsetFlag =false;
		for (MultipartFile coaraImgae : form.getCoaraImage()) {
			if(coaraImgae.getSize() != 0){
				coaraImageInsetFlag = true;
				break;
			}
		}
		if(coaraImageInsetFlag) {
			inserCoaraImage(insertCoara_id,form.getCoaraImage());
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
	public Coara findById(Long id) {
		return dao.findById(id);
	}
	
	@Override
	public void update(CoaraInsertForm form) {
		Coara coara = new Coara();
		coara.setCoara_id(form.getCoara_id());
		coara.setName(form.getName());
		coara.setSex(form.getSex());
		Date birthDate = getDate(form.getBirthYear(),form.getBirthMonth(),form.getBirthDay());
		if(birthDate != null) {
			coara.setBirthdate(birthDate);
		}
		coara.setIs_alive(form.getIs_alive());
		Date deathDate = getDate(form.getDeathYear(),form.getDeathMonth(),form.getDeathDay());
		if(deathDate != null) {
			coara.setDeathdate(deathDate);
		}
		coara.setZoo(form.getZoo());
		coara.setMother(form.getMother());
		coara.setFather(form.getFather());
		coara.setDetails(form.getDetails());
		coara.setFeature(form.getFeature());
		dao.update(coara);
		if(form.getCoaraImage()	!= null) {
			inserCoaraImage(form.getCoara_id(),form.getCoaraImage());
		}
		if(form.getDeleteCoaraImageFiles() != null) {
			deleteCoaraImage(form.getDeleteCoaraImageFiles(),form.getCoara_id());
		}
	}
	
	@Override
	@Transactional
	public void delete(int coara_id) {
		dao.delete(coara_id);
		deleteDirs(coara_id);
	}
	
	@Override
	public void inserCoaraImage(int coara_id, List<MultipartFile> coaraImageLust) {
		CoaraImage coaraImage = new CoaraImage();
		coaraImage.setCoara_id(coara_id);
		
		// 写真を格納するファイルのパス
        String filePath =  getcoaraUploadDir(coara_id);
        
        // アップロードファイルを格納するディレクトリを作成する
        
        File uploadDir = mkdirs(filePath);
        
		for(MultipartFile inputImage:coaraImageLust) {
			if(inputImage.getSize() != 0) {
				//ファイルの拡張子を取得する
				String fileExtension = inputImage.getOriginalFilename().substring(inputImage.getOriginalFilename().lastIndexOf("."));
				
				coaraImage.setFiletype(fileExtension);
				
				int coaraImageId = coaraImageDao.insert(coaraImage);
				try {
		            // アップロードファイルを置く
		            File uploadFile =
		                    new File(uploadDir.getPath() + File.separator + String.valueOf(coaraImageId) + fileExtension);
		            
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
	public void deleteCoaraImage(String CoaraImageFilesString,int coara_id) {
		String[] coaraImageFiles = CoaraImageFilesString.split(",");
		List<Integer> coaraImageIds = new ArrayList<Integer>();
		for(String coaraImageFile:coaraImageFiles) {
			int dot = coaraImageFile.lastIndexOf('.');
			String stringCoaraImageId = (dot == -1) ? coaraImageFile : coaraImageFile.substring(0, dot);
			Integer coaraImageId = Integer.valueOf(stringCoaraImageId);
			coaraImageIds.add(coaraImageId);
		}
		coaraImageDao.delete(coaraImageIds);
		deleteFiles(coaraImageFiles,coara_id);
	}
	
	private File  mkdirs(String filePath) {
		File uploadDir = new File(filePath);
		
		// フォルダ作成
        uploadDir.mkdirs();
        
        return uploadDir;
	}
	
	private void  deleteDirs(int coara_id) {
		// 写真を格納するファイルのパス
		File deleteDir = new File(getcoaraUploadDir(coara_id));
		
		 //ディレクトリ内の一覧を取得
        File[] files = deleteDir.listFiles();
        
        //存在するファイル数分ループして再帰的に削除
        for(int i=0; i<files.length; i++) {
            files[i].delete();
        }
		// フォルダ削除
		deleteDir.delete();
	}
	
	private void  deleteFiles(String[] coaraImageFiles,int coara_id) {
		String deleteFilePath = getcoaraUploadDir(coara_id);
		for(String coaraImageFile : coaraImageFiles) {
			File deleteFile = new File(deleteFilePath + File.separator + coaraImageFile);
			deleteFile.delete();
		}
	}
	
	private String getcoaraUploadDir(int coara_id) {
		// 写真を格納するファイルのパス
        StringBuffer dirPath = new StringBuffer("images").append(File.separator)
                                        .append("coara").append(File.separator)
        								.append(String.valueOf(coara_id));
        
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
