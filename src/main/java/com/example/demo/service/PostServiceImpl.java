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
import java.util.Map;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.app.PostInsertForm;
import com.example.demo.entity.Animal;
import com.example.demo.entity.Post;
import com.example.demo.entity.Zoo;
import com.example.demo.repository.AnimalDao;
import com.example.demo.repository.PostDao;
import com.example.demo.repository.PostImageDao;
import com.example.demo.repository.ZooDao;

@Service
public class PostServiceImpl implements PostService {
	
	private final ZooDao zooDao;
	private final AnimalDao animalDao;
	private final PostDao postDao;
	private final PostImageDao postImageDao;
	private final CloudinaryService cloudinaryService;
	
	@Autowired
	public PostServiceImpl(ZooDao zooDao,AnimalDao animalDao,PostDao postDao,PostImageDao postImageDao,CloudinaryService cloudinaryService) {
		this.zooDao = zooDao;
		this.animalDao = animalDao;
		this.postDao = postDao;
		this.postImageDao = postImageDao;
		this.cloudinaryService = cloudinaryService;
	}

	@Override
	public Zoo getZooById(int zoo_id) {
		return zooDao.findById(zoo_id);
	}
	
	@Override
	public List<Animal> getAnimalListByZooId(int zoo_id){
		return animalDao.getAnimalListByZooId(zoo_id);
	}
	
	@Override
	public void insertNewPost(PostInsertForm form) {
		Post post = new Post();
		Zoo zoo = new Zoo();
		Post parent = new Post();
		parent.setPost_id(form.getParent_id());
		zoo.setZoo_id(form.getZoo_id());
		post.setZoo(zoo);
		post.setContents(form.getContents());
		post.setVisitDate(getDate(form.getVisitdate()));
		post.setParent(parent);
		
		int insert_id = postDao.isertNewPost(post);
		
		List<String> imageAdressList = insertPostImage(insert_id, form.getImageList());
		
		postImageDao.insertNewPostImage(insert_id , form.getTagList(),imageAdressList);
	}
	
	@Override
	public List<String> insertPostImage(int post_id, List<MultipartFile> postImageUploadList) {
		List<String> imageAdressList = new ArrayList<String>();
		try {
			for(int i = 0; i < postImageUploadList.size(); ++i) {
				
				if(postImageUploadList.get(i).getSize() <= 0) {
					imageAdressList.add(null);
					continue;
				}
				
				// ファイルの拡張子を取得する
				String profileImagePath = postImageUploadList.get(i).getOriginalFilename()
						.substring(postImageUploadList.get(i).getOriginalFilename().lastIndexOf("."));
			
				File uploadFile = new File("images/" + "post/" + post_id + "_" + + i +profileImagePath);
	
				byte[] bytes = fileResize(postImageUploadList.get(i).getBytes(), profileImagePath.substring(1));
	
				if (bytes == null) {
					bytes = postImageUploadList.get(i).getBytes();
				}
	
				BufferedOutputStream uploadFileStream = new BufferedOutputStream(new FileOutputStream(uploadFile));
				uploadFileStream.write(bytes);
	
				uploadFileStream.close();
	
				// cloudinaryに写真をアップロードする
				Map resultmap = cloudinaryService.uploadPostImage(uploadFile, post_id);
				String url = (String)resultmap.get("secure_url");
				
				uploadFile.delete();
				
				imageAdressList.add(url);
			}
			
			return imageAdressList;
		} catch (Exception e) {
				// 異常終了時の処理
			return null;
		} catch (Throwable t) {
				// 異常終了時の処理
			return null;
		}

	}
	
	public Date getDate(String visitDate) {
		String year;
		String month;
		String day;
		String hyphen = "-";
		if(visitDate.trim().isEmpty()) {
			year = "9999";
			month = "01";
			day = "01";
		}else {
			String[] splitVisitDate = visitDate.split("(年|月|日)");
			year = splitVisitDate[0];
			month = splitVisitDate[1];
			day = splitVisitDate[2];
			if(month.length() == 1) {
				month = "0" + month;
			}
			if(day.length() == 1) {
				day = "0" + day;
			}
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
	
	private byte[] fileResize(byte[] originalImage, String originalExtension) {
		BufferedImage src = null;
		BufferedImage dst = null;
		AffineTransformOp xform = null;

		InputStream is = new ByteArrayInputStream(originalImage);
		try {
			src = ImageIO.read(is);
			int width = src.getWidth(); // . オリジナル画像の幅
			int height = src.getHeight(); // . オリジナル画像の高さ

			int w = 200; // . 幅をこの数値に合わせて調整する

			int new_height = w * height / width;
			int new_width = w;

			xform = new AffineTransformOp(
					AffineTransform.getScaleInstance((double) new_width / width, (double) new_height / height),
					AffineTransformOp.TYPE_BILINEAR);
			dst = new BufferedImage(new_width, new_height, src.getType());
			xform.filter(src, dst);

			// . 変換後のバイナリイメージを byte 配列に再格納
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
