package com.example.demo.service;

import com.example.demo.app.PostInsertForm;
import com.example.demo.entity.Animal;
import com.example.demo.entity.LoginUser;
import com.example.demo.entity.Post;
import com.example.demo.entity.Zoo;
import com.example.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class PostServiceImpl implements PostService {
	
	private final ZooDao zooDao;
	private final AnimalDao animalDao;
	private final PostDao postDao;
	private final PostImageDao postImageDao;
	private final PostFavoriteDao postFavoriteDao;
	private final CloudinaryService cloudinaryService;
	private final PostDaoImple postDaoImple;
	private final Post post;
	
	@Autowired
	public PostServiceImpl(ZooDao zooDao, AnimalDao animalDao, PostDao postDao, PostImageDao postImageDao, CloudinaryService cloudinaryService, PostFavoriteDao postFavoriteDao, PostDaoImple postDaoImple, Post post) {
		this.zooDao = zooDao;
		this.animalDao = animalDao;
		this.postDao = postDao;
		this.postImageDao = postImageDao;
		this.postFavoriteDao = postFavoriteDao;
		this.cloudinaryService = cloudinaryService;
		this.postDaoImple = postDaoImple;
		this.post = post;
	}

	@Override
	public List<Post> getPostsByUserId(int userId){
		return postFavoriteDao.getPostFavoriteList(postDao.getPostsListByUserId(userId));
	}

	@Override
	public List<Post> getCommentsByUserId(int userId) {
		return postFavoriteDao.getPostFavoriteList(postDao.getCommentsListByUserId(userId));
	}

	@Override
	public List<Post> getImagesByUserId(int userId) {
		return postFavoriteDao.getPostFavoriteList(postDao.getImagesListByUserId(userId));
	}

	@Override
	public List<Post> getFavoritesByUserId(int userId) {
		return postFavoriteDao.getPostFavoriteList(postDao.getFavoritesListByUserId(userId));
	}
	
	@Override
	public Post getPostByPostId(int post_id){
		
		return postFavoriteDao.getPostFavorite(postDao.getPostByPostId(post_id));
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
		parent.setPostId(form.getParentId());
		zoo.setZoo_id(form.getZooId());
		post.setZoo(zoo);
		post.setContents(form.getContents());
		post.setVisitDate(getDate(form.getVisitDate()));
		post.setParentPost(parent);
		
		int insert_id = postDao.insertNewPost(post);
		
		if(form.getImageList() != null) {
			for(int i=0 ; i < form.getImageList().size() ; ++i){
				if(form.getImageList().get(i).getSize() > 0 ) {
					int insert_image_id = postImageDao.insertNewPostImage(insert_id , form.getTagList().get(i));
					String url = insertPostImage(insert_id, form.getImageList().get(i),insert_image_id);
					postImageDao.updateUrl(insert_image_id , url);
				}
			}
		}
	}
	
	@Override
	public String insertPostImage(int post_id, MultipartFile postImageUpload ,int post_image_id ){
		try {	
				// ???????????????????????????????????????
				String profileImagePath = postImageUpload.getOriginalFilename()
						.substring( postImageUpload.getOriginalFilename().lastIndexOf("."));
			
				File uploadFile = new File("images/" + "post/" + post_image_id + profileImagePath);
	
				byte[] bytes = fileResize(postImageUpload.getBytes(), profileImagePath.substring(1));
	
				if (bytes == null) {
					bytes = postImageUpload.getBytes();
				}
	
				BufferedOutputStream uploadFileStream = new BufferedOutputStream(new FileOutputStream(uploadFile));
				uploadFileStream.write(bytes);
	
				uploadFileStream.close();
	
				// cloudinary????????????????????????????????????
				Map resultmap = cloudinaryService.uploadPostImage(uploadFile, post_id);
				String url = (String)resultmap.get("secure_url");
				
				uploadFile.delete();
			
				return url;
				
		} catch (Exception e) {
				// ????????????????????????
			return null;
		} catch (Throwable t) {
				// ????????????????????????
			return null;
		}

	}

	@Override
	public void deletePost(PostInsertForm postInsertForm) {
		postDao.deletePost(postInsertForm.getPostId());
		cloudinaryService.deleteDirs(postInsertForm.getPostId());
	}
	
	public Date getDate(String visitDate) {
		
		String year;
		String month;
		String day;
		String hyphen = "-";
		if(visitDate == null || visitDate.trim().isEmpty()) {
			year = "9999";
			month = "01";
			day = "01";
		}else {
			String[] splitVisitDate = visitDate.split("(???|???|???)");
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
			// TODO ????????????????????? catch ????????????
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
			int width = src.getWidth(); // . ???????????????????????????
			int height = src.getHeight(); // . ??????????????????????????????

			int w = 500; // . ?????????????????????????????????????????????

			int new_height = w * height / width;
			int new_width = w;

			xform = new AffineTransformOp(
					AffineTransform.getScaleInstance((double) new_width / width, (double) new_height / height),
					AffineTransformOp.TYPE_BILINEAR);
			dst = new BufferedImage(new_width, new_height, src.getType());
			xform.filter(src, dst);

			// . ??????????????????????????????????????? byte ??????????????????
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(dst, originalExtension, baos);
			originalImage = baos.toByteArray();
			return originalImage;
		} catch (IOException e) {
			// TODO ????????????????????? catch ????????????
			e.printStackTrace();
			return null;
		}
		
	}

	/* 202207 ??????????????????????????????????????????????????? */
	/* ????????????????????????post??????????????????????????????????????? */
	public Post save(LoginUser user, Post post){

		//TODO ?????????????????????????????????????????????????????????????????????????????????
		if (post.getParentPost() == null) {
			Post parentPost = new Post();
			parentPost.setPostId(0);
			post.setParentPost(parentPost);
		}
		if(post.getZoo()==null){
			Zoo zoo = new Zoo();
			zoo.setZoo_id(0);
			post.setZoo(zoo);
		}

		post.setContents("???????????????");
		post.setVisitDate(new Date());

		return postDaoImple.save(user, post);
	}

}
