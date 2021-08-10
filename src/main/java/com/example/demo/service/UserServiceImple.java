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
import java.util.Map;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.demo.app.UserForm;
import com.example.demo.entity.LoginUser;
import com.example.demo.repository.LoginUserDao;

@Service
public class UserServiceImple implements UserService {
	
	private final LoginUserDao loginUserdao;
	private final CloudinaryService cloudinaryService;
	
	@Autowired
	public UserServiceImple(LoginUserDao loginUserdao,CloudinaryService cloudinaryService) {
		// TODO 自動生成されたコンストラクター・スタブ
		this.loginUserdao = loginUserdao;
		this.cloudinaryService = cloudinaryService;
	}

	@Override
	public LoginUser findById(int user_id) {
		// TODO 自動生成されたメソッド・スタブ
		return loginUserdao.findById(user_id);
	}
	
	@Override
	public void updateMyPage(UserForm form) {
		//プロフィール画像
		String profileImageurl = form.getProfileImagePath();
		if (!form.getUserProfileImageUpload().isEmpty()) {
			profileImageurl = updateUserProfileImage(form);
		}
		
		LoginUser loginUser = new LoginUser(form.getName() ,"dummy", 
				"ROLE_USER", form.getUser_id() ,null, 0, null, form.getProfile(), 
				0, profileImageurl, form.isTwitterLinkFlag());
		
		LoginUser newLoginUser = loginUserdao.updateMyPage(loginUser);
		
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(newLoginUser, null,
				AuthorityUtils.createAuthorityList(newLoginUser.getRole()));
		SecurityContextHolder.getContext().setAuthentication(token);
	}
	
	private String updateUserProfileImage(UserForm form) {
		try {
			
			File uploadFile = new File("images/user/profile/" + form.getUser_id() + form.getUserProfileImageUpload().getOriginalFilename()
					.substring(form.getUserProfileImageUpload().getOriginalFilename().lastIndexOf(".")));

			byte[] bytes = fileResize(form.getUserProfileImageUpload().getBytes(), form.getUserProfileImageUpload().getOriginalFilename()
					.substring(form.getUserProfileImageUpload().getOriginalFilename().lastIndexOf(".") + 1));

			if (bytes == null) {
				bytes = form.getUserProfileImageUpload().getBytes();
			}

			BufferedOutputStream uploadFileStream = new BufferedOutputStream(new FileOutputStream(uploadFile));
			uploadFileStream.write(bytes);

			uploadFileStream.close();

			// cloudinaryに写真をアップロードする
			Map resultmap = cloudinaryService.uploadUserProfileImage(uploadFile, form.getUser_id());
			
			String url = (String)resultmap.get("secure_url");
			
			uploadFile.delete();
			
			return url;
			
		} catch (Exception e) {
			// 異常終了時の処理
		} catch (Throwable t) {
			// 異常終了時の処理
		}
		return null;
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

			int w = 500; // . 幅をこの数値に合わせて調整する

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
