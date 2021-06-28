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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.app.KoalaInsertForm;
import com.example.demo.entity.Koala;
import com.example.demo.entity.KoalaForTree;
import com.example.demo.entity.KoalaImage;
import com.example.demo.entity.RelationForTree;
import com.example.demo.entity.Zoo;
import com.example.demo.repository.KoalaDao;
import com.example.demo.repository.KoalaImageDao;

@Service
public class KoalaServiceImpl implements KoalaService {

	private final KoalaDao dao;
	private final KoalaImageDao koalaImageDao;
	private final CloudinaryService cloudinaryService;
	
	@Autowired
	public KoalaServiceImpl(KoalaDao dao, KoalaImageDao koalaImageDao, CloudinaryService cloudinaryService) {
		this.dao = dao;
		this.koalaImageDao = koalaImageDao;
		this.cloudinaryService = cloudinaryService;
	}

	@Override
	public List<Koala> getAll() {
		return dao.getAll();
	}

	@Override
	public List<Koala> findByKeyword(String keyword) {
		return dao.findByKeyword(keyword);
	}

	@Override
	public List<Koala> getMotherList(int koala_id, String birthYear, String birthMonth, String birthDay) {
		Date birthDate = getDate(birthYear, birthMonth, birthDay);
		List<Koala> motherList = dao.getMotherList(koala_id, birthDate);

		return motherList;
	}

	@Override
	public List<Koala> getFatherList(int koala_id, String birthYear, String birthMonth, String birthDay) {
		Date birthDate = getDate(birthYear, birthMonth, birthDay);
		List<Koala> fatherList = dao.getFatherList(koala_id, birthDate);

		return fatherList;
	}

	@Override
	public List<Zoo> getZooList() {
		List<Zoo> zooList = dao.getZooList();

		Zoo other_zoo = new Zoo();
		other_zoo = zooList.get(0);
		zooList.remove(0);
		zooList.add(other_zoo);

		Zoo zoo = new Zoo();
		zoo.setZoo_id(-1);
		zoo.setZoo_name("---");
		zooList.add(0, zoo);

		return zooList;

	}

	Koala koala;

	@Override
	@Transactional
	public void insert(KoalaInsertForm form) {
		Koala koala = new Koala();
		koala.setName(form.getName());
		koala.setSex(form.getSex());
		Date birthDate = getDate(form.getBirthYear(), form.getBirthMonth(), form.getBirthDay());
		if (birthDate != null) {
			koala.setBirthdate(birthDate);
		}
		koala.setIs_alive(form.getIs_alive());
		Date deathDate = getDate(form.getDeathYear(), form.getDeathMonth(), form.getDeathDay());
		if (deathDate != null) {
			koala.setDeathdate(deathDate);
		}
		koala.setZoo(form.getZoo());
		koala.setDetails(form.getDetails());
		koala.setFeature(form.getFeature());
		koala.setMother_id(form.getMother_id());
		koala.setFather_id(form.getFather_id());
		koala.setProfileImagePath((form.getKoalaProfileImageUpload().getOriginalFilename())
				.substring(form.getKoalaProfileImageUpload().getOriginalFilename().lastIndexOf(".")));

		int insertKoala_id = dao.insert(koala);
		// 追加するコアラに画像が添付されているか確認
		boolean koalaImageInsetFlag = false;
		for (MultipartFile koalaImgae : form.getKoalaImage()) {
			if (koalaImgae.getSize() != 0) {
				koalaImageInsetFlag = true;
				break;
			}
		}
		if (koalaImageInsetFlag) {
			insertKoalaImage(insertKoala_id, form.getKoalaImage());
		}

//		プロフィール画像
		boolean koalaProfileImageInsetFlag = false;
		if (form.getKoalaProfileImageUpload() != null) {
			koalaProfileImageInsetFlag = true;

		}
		if (koalaProfileImageInsetFlag) {
			insertKoalaProfileImage(insertKoala_id, form.getKoalaProfileImageUpload(), koala.getProfileImagePath());
		}

	}

	public Date getDate(String year, String month, String day) {
		String hyphen = "-";
		// 年、月、日の中に"--"が含まれていたら、"9999-01-01"を返す。
		String dummyValue = "0";
		if (year.equals(dummyValue) || month.equals(dummyValue) || day.equals(dummyValue)) {
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
	public Koala findById(int id) {
		return dao.findById(id);
	}
	
	@Override
	public 	Map<String, Object> getKoalaForTree(int id) {
		
		//オブジェクトルート、オブジェクト調整用のインスタンスの生成
		KoalaForTree root = new KoalaForTree();
		
		//メインのコアラオブジェクトを生成
		KoalaForTree mainKoala = dao.getKoalaForTree(id);
		
		KoalaForTree motherKoala = new KoalaForTree();
		if (mainKoala.getMother_id() != 0) {
			motherKoala = dao.getKoalaForTree(mainKoala.getMother_id());
		} else {
			motherKoala.setId(9990);
			motherKoala.setName("不明");
		}
		
		KoalaForTree fatherKoala = new KoalaForTree();
		if (mainKoala.getMother_id() != 0) {
			fatherKoala = dao.getKoalaForTree(mainKoala.getFather_id());
		} else {
			fatherKoala.setId(9998);
			fatherKoala.setName("不明");
		}
				
		//祖父母
		KoalaForTree paternalGrandFather = new KoalaForTree();
		if (fatherKoala.getFather_id() != 0) {
			paternalGrandFather = dao.getKoalaForTree(fatherKoala.getFather_id());
		} else {
			paternalGrandFather.setId(9997);
			paternalGrandFather.setName("不明");
		}
		
		KoalaForTree paternalGrandMother = new KoalaForTree();
		if (fatherKoala.getMother_id() != 0) {
			paternalGrandMother = dao.getKoalaForTree(fatherKoala.getMother_id());
		} else {
			paternalGrandMother.setId(9996);
			paternalGrandMother.setName("不明");
		}
		
		KoalaForTree maternalGrandFather = new KoalaForTree();
		if (motherKoala.getFather_id() != 0) {
			maternalGrandFather = dao.getKoalaForTree(motherKoala.getFather_id());
		} else {
			maternalGrandFather.setId(9995);
			maternalGrandFather.setName("不明");
		}
		
		KoalaForTree maternalGrandMother = new KoalaForTree();
		if (motherKoala.getMother_id() != 0) {
			maternalGrandMother = dao.getKoalaForTree(motherKoala.getMother_id());
		} else {
			maternalGrandMother.setId(9994);
			maternalGrandMother.setName("不明");
		}
		

		
		//家系図向けにコアラのフィールドの整理　上記の検索用に取得した値を書き換える　後でJSで0は削る
		mainKoala.setNo_parent(false);
		mainKoala.setHidden(false);
		fatherKoala.setHidden(false);
		fatherKoala.setNo_parent(false);
		motherKoala.setHidden(false);
		motherKoala.setNo_parent(false);
		paternalGrandMother.setHidden(false);
		paternalGrandFather.setHidden(false);
		maternalGrandMother.setHidden(false);
		maternalGrandFather.setHidden(false);
		
		//プロフィール画像の有無の確認（ベタ書き。今後共通化する）
		if(mainKoala.getProfileImagePath() == null){
				mainKoala.setProfileImagePath("/images/defaultKoala.png");
			}
		if(fatherKoala.getProfileImagePath() == null){
			fatherKoala.setProfileImagePath("/images/defaultKoala.png");
		}
		if(motherKoala.getProfileImagePath() == null){
			motherKoala.setProfileImagePath("/images/defaultKoala.png");
		}
		if(paternalGrandMother.getProfileImagePath() == null){
			paternalGrandMother.setProfileImagePath("/images/defaultKoala.png");
		}
		if(paternalGrandFather.getProfileImagePath() == null){
			paternalGrandFather.setProfileImagePath("/images/defaultKoala.png");
		}
		if(maternalGrandMother.getProfileImagePath() == null){
			maternalGrandMother.setProfileImagePath("/images/defaultKoala.png");
		}
		if(maternalGrandFather.getProfileImagePath() == null){
			maternalGrandFather.setProfileImagePath("/images/defaultKoala.png");
		}
		//性別の確認（ベタ書き。今後共通化する）
		if(mainKoala.getSex() == 99){
			mainKoala.setSex(0);
		}
	if(fatherKoala.getSex() == 99){
		fatherKoala.setSex(1);
	}
	if(motherKoala.getSex() == 99){
		motherKoala.setSex(2);
	}
	if(paternalGrandMother.getSex() == 99){
		paternalGrandMother.setSex(2);
	}
	if(paternalGrandFather.getSex() == 99){
		paternalGrandFather.setSex(1);
	}
	if(maternalGrandMother.getSex() == 99){
		maternalGrandMother.setSex(2);
	}
	if(maternalGrandFather.getSex() == 99){
		maternalGrandFather.setSex(1);
	}
		
		//①コアラの親子関係の整理
		List<KoalaForTree> koalaForTreeLayerMain = new ArrayList<KoalaForTree>();
		List<KoalaForTree> koalaForTreeLayerFather = new ArrayList<KoalaForTree>();
		List<KoalaForTree> koalaForTreeLayerAdjustmentForMain = new ArrayList<KoalaForTree>();
		List<KoalaForTree> koalaForTreeLayerMother = new ArrayList<KoalaForTree>();
		List<KoalaForTree> koalaForTreeLayerRoot = new ArrayList<KoalaForTree>();
		List<KoalaForTree> koalaForTreeLayerChildren = new ArrayList<KoalaForTree>();

		KoalaForTree adjustmentForMain = new KoalaForTree();
		KoalaForTree adjustmentForFather = new KoalaForTree();
		KoalaForTree adjustmentForMother = new KoalaForTree();
		KoalaForTree adjustmentForMain2 = new KoalaForTree();
		
		koalaForTreeLayerMain.add(mainKoala);
		
		//メインコアラの配偶者（複数の可能性あり）とその子供達
		int sex = mainKoala.getSex(); //メインのコアラが父親か母親か
		List<KoalaForTree> childrenList = dao.getChildrenKoalaForTree(mainKoala.getId(), sex); 
//		List<KoalaForTree> spouseList = new ArrayList<KoalaForTree>();
//		Map<Integer,  KoalaForTree> spouseMap = new HashMap<Integer,  KoalaForTree>();
		Map<Object, List<KoalaForTree>> childrenMap;
		
		for (KoalaForTree child : childrenList) {
			child.setNo_parent(false);
			child.setHidden(false);
			if(child.getProfileImagePath() == null){
				child.setProfileImagePath("/images/defaultKoala.png");
			}
		}
		
		//メインのコアラの配偶者の性別のIDを用いてリストからグループ化した子供マップに変換
		if (sex == 1) {
			childrenMap = childrenList.stream().collect(Collectors.groupingBy(i -> i.getMother_id()));
		} else {
			childrenMap = childrenList.stream().collect(Collectors.groupingBy(i -> i.getFather_id()));
		}
		
//		子供マップから配偶者（複数の可能性あり）を取得 & 両親のレイヤーに詰め込身を行う
		int h = 1;
		for (Entry<Object, List<KoalaForTree>> entry : childrenMap.entrySet()) {
			KoalaForTree spouseKoala = dao.getKoalaForTree((int)entry.getKey());
			spouseKoala.setHidden(false);
			if(spouseKoala.getProfileImagePath() == null){
				mainKoala.setProfileImagePath("/images/defaultKoala.png");
			}
			koalaForTreeLayerMain.add(	new KoalaForTree());
			if(maternalGrandFather.getProfileImagePath() == null){
				mainKoala.setProfileImagePath("/images/defaultKoala.png");
			}
			koalaForTreeLayerMain.get(h).setChildren(childrenMap.get((int)entry.getKey())); //調整用のnewしたKoalaForTreeに子供リストをセット
			koalaForTreeLayerMain.add(spouseKoala);
			h += 2;
		}

//ゴミ。テスト用。リスト型をマップ型へ変換 keyはid
//		if (sex == 1) {
//			spouseMap = spouseList.stream().collect(Collectors.toMap(KoalaForTree::getMother_id, i -> i));
//		} else {
//			spouseMap = spouseList.stream().collect(Collectors.toMap(KoalaForTree::getFather_id, i -> i));
//		}

		
		//メインコアラの兄弟達
		List<KoalaForTree> brotherKoalaList = dao.getBrotherKoalaForTree(mainKoala.getId(), mainKoala.getMother_id(), mainKoala.getFather_id());
		for (KoalaForTree brotherKoala : brotherKoalaList) {
			brotherKoala.setNo_parent(false);
			brotherKoala.setHidden(false);
			koalaForTreeLayerMain.add(brotherKoala);			
		}
		
		adjustmentForMain.setChildren(koalaForTreeLayerMain);
		
		koalaForTreeLayerAdjustmentForMain.add(adjustmentForMain);	
		koalaForTreeLayerFather.add(fatherKoala);
		koalaForTreeLayerMother.add(motherKoala);
		adjustmentForFather.setChildren(koalaForTreeLayerFather);
		adjustmentForMother.setChildren(koalaForTreeLayerMother);	
		adjustmentForMain2.setChildren(koalaForTreeLayerAdjustmentForMain);
		
		koalaForTreeLayerRoot.add(paternalGrandFather);
		koalaForTreeLayerRoot.add(adjustmentForFather);
		koalaForTreeLayerRoot.add(paternalGrandMother);
		koalaForTreeLayerRoot.add(adjustmentForMain2);
		koalaForTreeLayerRoot.add(maternalGrandFather);
		koalaForTreeLayerRoot.add(adjustmentForMother);
		koalaForTreeLayerRoot.add(maternalGrandMother);
		root.setChildren(koalaForTreeLayerRoot);
		
		//②コアラの関係性のリストの作成、オブジェクトの詰め込み
		List<RelationForTree> relationForTree = new ArrayList<RelationForTree>();

		RelationForTree relationForTree1 = new RelationForTree();
		relationForTree1.setSource(fatherKoala);
		relationForTree1.setTarget(motherKoala);
		
		RelationForTree relationForTree2 = new RelationForTree();
		relationForTree2.setSource(paternalGrandFather);
		relationForTree2.setTarget(paternalGrandMother);
		
		RelationForTree relationForTree3 = new RelationForTree();
		relationForTree3.setSource(maternalGrandFather);
		relationForTree3.setTarget(maternalGrandMother);
		
		relationForTree.add(relationForTree1);
		relationForTree.add(relationForTree2);
		relationForTree.add(relationForTree3);
		
		//mainコアラと配偶者の関係性
		//祖父母、両親は確実に表示するが、配偶者は存在する場合に表示する
		int r = 3;
		for (Entry<Object, List<KoalaForTree>> entry : childrenMap.entrySet()) {
			KoalaForTree spouseKoala = dao.getKoalaForTree((int)entry.getKey());
			relationForTree.add(new RelationForTree());
			relationForTree.get(r).setSource(mainKoala);
			relationForTree.get(r).setTarget(spouseKoala);
			r++;
		}
		
		//①と②をMapでフロントに返却
		Map<String, Object> mapForTree = new HashMap<String, Object>();
		mapForTree.put("koalaForTree", root);
		mapForTree.put("relationForTree", relationForTree);
		
		return mapForTree;
	}
	
	@Override
	public void update(KoalaInsertForm form) {
		Koala koala = new Koala();
		koala.setKoala_id(form.getKoala_id());
		koala.setName(form.getName());
		koala.setSex(form.getSex());
		Date birthDate = getDate(form.getBirthYear(), form.getBirthMonth(), form.getBirthDay());
		if (birthDate != null) {
			koala.setBirthdate(birthDate);
		}
		koala.setIs_alive(form.getIs_alive());
		Date deathDate = getDate(form.getDeathYear(), form.getDeathMonth(), form.getDeathDay());
		if (deathDate != null) {
			koala.setDeathdate(deathDate);
		}
		koala.setMother_id(form.getMother_id());
		koala.setFather_id(form.getFather_id());
		;
		koala.setZoo(form.getZoo());
		koala.setDetails(form.getDetails());
		koala.setFeature(form.getFeature());
		koala.setProfileImagePath(form.getProfileImagePath());
		String profileImagePath =  null;
		if( !form.getKoalaProfileImageUpload().isEmpty()) {
			profileImagePath = form.getKoalaProfileImageUpload().getOriginalFilename()
					.substring(form.getKoalaProfileImageUpload().getOriginalFilename().lastIndexOf("."));
			koala.setProfileImagePath(profileImagePath);
		}
		dao.update(koala);

//		プロフィール画像登録 or 更新
	
		if (profileImagePath  != null) {
			insertKoalaProfileImage(form.getKoala_id(), form.getKoalaProfileImageUpload(), profileImagePath);
		}

		//		コアラ画像登録		
		if (form.getKoalaImage() != null) {
			insertKoalaImage(form.getKoala_id(), form.getKoalaImage());
		}

//		コアラ画像削除
		if (form.getDeleteKoalaImageFiles() != null) {
			deleteKoalaImage(form.getDeleteKoalaImageFiles(), form.getKoala_id());
		}
	}

	
	
	@Override
	@Transactional
	public void delete(int koala_id) {
		dao.delete(koala_id);
		deleteDirs(koala_id);
	}

	@Override
	public List<KoalaImage> findKoalaImageById(int id) {
		return koalaImageDao.findByKoala_id(id);
	}

	@Override
	public void insertKoalaProfileImage(int koala_id, MultipartFile koalaProfileImageUpload, String profileImagePath) {
		try {
			//
			File uploadFile = new File("images/" + koala_id + profileImagePath);

			byte[] bytes = fileResize(koalaProfileImageUpload.getBytes(), profileImagePath.substring(1));

			if (bytes == null) {
				bytes = koalaProfileImageUpload.getBytes();
			}

			BufferedOutputStream uploadFileStream = new BufferedOutputStream(new FileOutputStream(uploadFile));
			uploadFileStream.write(bytes);

			uploadFileStream.close();

			// cloudinaryに写真をアップロードする
			Map resultmap = cloudinaryService.uploadKoalaProfileImage(uploadFile, koala_id);
			String url = (String)resultmap.get("secure_url");
			dao.urlUpdate(koala_id, url);
			
			uploadFile.delete();
		} catch (Exception e) {
			// 異常終了時の処理
		} catch (Throwable t) {
			// 異常終了時の処理
		}

	}

	@Override
	public void insertKoalaImage(int koala_id, List<MultipartFile> koalaImageLust) {
		KoalaImage koalaImage = new KoalaImage();
		koalaImage.setKoala_id(koala_id);

		for (MultipartFile inputImage : koalaImageLust) {
			if (inputImage.getSize() != 0) {
				// ファイルの拡張子を取得する
				String profileImagePath = inputImage.getOriginalFilename()
						.substring(inputImage.getOriginalFilename().lastIndexOf("."));

				koalaImage.setFiletype(profileImagePath);

				int koalaImageId = koalaImageDao.insert(koalaImage);
				try {
					// アップロードファイルを置く
					File uploadFile = new File("images/" + koalaImageId + profileImagePath);

					byte[] bytes = fileResize(inputImage.getBytes(), profileImagePath.substring(1));

					if (bytes == null) {
						bytes = inputImage.getBytes();
					}

					BufferedOutputStream uploadFileStream = new BufferedOutputStream(new FileOutputStream(uploadFile));

					uploadFileStream.write(bytes);

					uploadFileStream.close();

					// cloudinaryに写真をアップロードする
					Map resultmap = cloudinaryService.uploadKoalaImage(uploadFile, koala_id);
					uploadFile.delete();
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
	public void deleteKoalaImage(String KoalaImageFilesString, int koala_id) {
		String[] koalaImageFiles = KoalaImageFilesString.split(",");

		// cloudinaryにあるファイルを削除
		List<String> koalaImageIds = cloudinaryService.deleteKoalaImage(koalaImageFiles, koala_id);

		koalaImageDao.delete(koalaImageIds);
	}

	private void deleteDirs(int koala_id) {
		cloudinaryService.deleteDirs(koala_id);
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
