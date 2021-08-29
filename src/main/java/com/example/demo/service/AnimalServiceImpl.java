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

import com.example.demo.app.AnimalInsertForm;
import com.example.demo.entity.Animal;
import com.example.demo.entity.AnimalForTree;
import com.example.demo.entity.RelationForTree;
import com.example.demo.entity.Zoo;
import com.example.demo.repository.AnimalDao;
import com.example.demo.repository.AnimalZooHistoryDao;
import com.example.demo.repository.ZooDao;

@Service
public class AnimalServiceImpl implements AnimalService {

	private final AnimalDao animalDao;
	private final CloudinaryService cloudinaryService;
	private final AnimalZooHistoryDao animalZooHistoryDao;
	private final ZooDao zooDao;
	
	@Autowired
	public AnimalServiceImpl(AnimalDao animalDao,
			CloudinaryService cloudinaryService, AnimalZooHistoryDao animalZooHistoryDao,
			ZooDao zooDao) {
		this.animalDao = animalDao;
		this.cloudinaryService = cloudinaryService;
		this.animalZooHistoryDao = animalZooHistoryDao;
		this.zooDao = zooDao;
	}

	@Override
	public List<Animal> getAll() {
		return animalDao.getAll();
	}

	@Override
	public List<Animal> findByKeyword(String keyword) {
		return animalDao.findByKeyword(keyword);
	}

	@Override
	public List<Animal> getMotherList(int animal_id, String birthYear, String birthMonth, String birthDay) {
		Date birthDate = getDate(birthYear, birthMonth, birthDay);
		List<Animal> motherList = animalDao.getMotherList(animal_id, birthDate);
		Animal unknownForInsertForm = new Animal();
		unknownForInsertForm.setName("不明");
		unknownForInsertForm.setAnimal_id(0);
		motherList.add(unknownForInsertForm);
		return motherList;
	}

	@Override
	public List<Animal> getFatherList(int animal_id, String birthYear, String birthMonth, String birthDay) {
		Date birthDate = getDate(birthYear, birthMonth, birthDay);
		List<Animal> fatherList = animalDao.getFatherList(animal_id, birthDate);
		Animal unknownForInsertForm = new Animal();
		unknownForInsertForm.setName("不明");
		unknownForInsertForm.setAnimal_id(0);
		fatherList.add(unknownForInsertForm);
		return fatherList;
	}

	@Override
	public List<Zoo> getZooList() {
		List<Zoo> zooList = zooDao.getZooList();

		//その他をリストの最下部へ
		Zoo other_zoo = new Zoo();
		other_zoo = zooList.get(0);
		zooList.remove(0);
		zooList.add(other_zoo);
		
		//選択なし
		Zoo zoo = new Zoo();
		zoo.setZoo_id(-1);
		zoo.setZoo_name("---");
		zooList.add(0, zoo);

		return zooList;

	}


	@Override
	@Transactional
	public void insert(AnimalInsertForm form) {
		Animal animal = new Animal();
		animal.setName(form.getName());
		animal.setSex(form.getSex());
		Date birthDate = getDate(form.getBirthYear(), form.getBirthMonth(), form.getBirthDay());
		if (birthDate != null) {
			animal.setBirthdate(birthDate);
		}
		animal.setIs_alive(form.getIs_alive());
		Date deathDate = getDate(form.getDeathYear(), form.getDeathMonth(), form.getDeathDay());
		if (deathDate != null) {
			animal.setDeathdate(deathDate);
		}
		animal.setDetails(form.getDetails());
		animal.setFeature(form.getFeature());
		Animal motherAnimal = new Animal();
		Animal fatherAnimal = new Animal();
		motherAnimal.setAnimal_id(form.getMother_id());
		fatherAnimal.setAnimal_id(form.getFather_id());
		animal.setMotherAnimal(motherAnimal);
		animal.setFatherAnimal(fatherAnimal);
		int insertAnimal_id = animalDao.insert(animal);

		//プロフィール画像登録
		if (!form.getAnimalProfileImageUpload().isEmpty()) {
			insertAnimalProfileImage(insertAnimal_id, form.getAnimalProfileImageUpload(), animal.getProfileImagePath());
		}
		
		//入園退園の経歴登録
		List<Date> admissionDateList = new ArrayList<Date>();
		for (int i = 0; i < form.getAdmissionYear().size(); i++) {
			Date admissionDate = getDate(form.getAdmissionYear().get(i),form.getAdmissionMonth().get(i), form.getAdmissionDay().get(i));
			admissionDateList.add(admissionDate);
		}
		
		List<Date> exitDateList = new ArrayList<Date>();
		for (int i = 0; i < form.getExitYear().size(); i++) {
			Date exitDate = getDate(form.getExitYear().get(i),form.getExitMonth().get(i), form.getExitDay().get(i));
			exitDateList.add(exitDate);
		}
		
		
		animalZooHistoryDao.insertZooHistory(insertAnimal_id, form.getInsertZoo(),admissionDateList, exitDateList);

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
	public Animal findById(int id) {
		Animal animal =  animalDao.findById(id);
		return animalZooHistoryDao.getAnimalZooHistory(id, animal);
	}
	
	@Override
	public 	Map<String, Object> getAnimalForTree(int id) {
		
		//オブジェクトルート、オブジェクト調整用のインスタンスの生成
		AnimalForTree root = new AnimalForTree();
		
		//メインのコアラオブジェクトを生成
		AnimalForTree mainAnimal = animalDao.getAnimalForTree(id);
		
		AnimalForTree motherAnimal = new AnimalForTree();
		if (mainAnimal.getMother_id() != 0) {
			motherAnimal = animalDao.getAnimalForTree(mainAnimal.getMother_id());
		} else {
			motherAnimal.setId(9990);
			motherAnimal.setName("不明");
		}
		
		AnimalForTree fatherAnimal = new AnimalForTree();
		if (mainAnimal.getFather_id() != 0) {
			fatherAnimal = animalDao.getAnimalForTree(mainAnimal.getFather_id());
		} else {
			fatherAnimal.setId(9998);
			fatherAnimal.setName("不明");
		}
				
		//祖父母
		AnimalForTree paternalGrandFather = new AnimalForTree();
		if (fatherAnimal.getFather_id() != 0) {
			paternalGrandFather = animalDao.getAnimalForTree(fatherAnimal.getFather_id());
		} else {
			paternalGrandFather.setId(9997);
			paternalGrandFather.setName("不明");
		}
		
		AnimalForTree paternalGrandMother = new AnimalForTree();
		if (fatherAnimal.getMother_id() != 0) {
			paternalGrandMother = animalDao.getAnimalForTree(fatherAnimal.getMother_id());
		} else {
			paternalGrandMother.setId(9996);
			paternalGrandMother.setName("不明");
		}
		
		AnimalForTree maternalGrandFather = new AnimalForTree();
		if (motherAnimal.getFather_id() != 0) {
			maternalGrandFather = animalDao.getAnimalForTree(motherAnimal.getFather_id());
		} else {
			maternalGrandFather.setId(9995);
			maternalGrandFather.setName("不明");
		}
		
		AnimalForTree maternalGrandMother = new AnimalForTree();
		if (motherAnimal.getMother_id() != 0) {
			maternalGrandMother = animalDao.getAnimalForTree(motherAnimal.getMother_id());
		} else {
			maternalGrandMother.setId(9994);
			maternalGrandMother.setName("不明");
		}
		

		
		//家系図向けにコアラのフィールドの整理　上記の検索用に取得した値を書き換える　後でJSで0は削る
		mainAnimal.setNo_parent(false);
		mainAnimal.setHidden(false);
		fatherAnimal.setHidden(false);
		fatherAnimal.setNo_parent(false);
		motherAnimal.setHidden(false);
		motherAnimal.setNo_parent(false);
		paternalGrandMother.setHidden(false);
		paternalGrandFather.setHidden(false);
		maternalGrandMother.setHidden(false);
		maternalGrandFather.setHidden(false);
		
		//プロフィール画像の有無の確認（ベタ書き。今後共通化する）
		if(mainAnimal.getProfileImagePath() == null){
				mainAnimal.setProfileImagePath("/images/defaultAnimal.png");
			}
		if(fatherAnimal.getProfileImagePath() == null){
			fatherAnimal.setProfileImagePath("/images/defaultAnimal.png");
		}
		if(motherAnimal.getProfileImagePath() == null){
			motherAnimal.setProfileImagePath("/images/defaultAnimal.png");
		}
		if(paternalGrandMother.getProfileImagePath() == null){
			paternalGrandMother.setProfileImagePath("/images/defaultAnimal.png");
		}
		if(paternalGrandFather.getProfileImagePath() == null){
			paternalGrandFather.setProfileImagePath("/images/defaultAnimal.png");
		}
		if(maternalGrandMother.getProfileImagePath() == null){
			maternalGrandMother.setProfileImagePath("/images/defaultAnimal.png");
		}
		if(maternalGrandFather.getProfileImagePath() == null){
			maternalGrandFather.setProfileImagePath("/images/defaultAnimal.png");
		}
		//性別の確認（ベタ書き。今後共通化する）
		if(mainAnimal.getSex() == 99){
			mainAnimal.setSex(0);
		}
	if(fatherAnimal.getSex() == 99){
		fatherAnimal.setSex(1);
	}
	if(motherAnimal.getSex() == 99){
		motherAnimal.setSex(2);
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
		List<AnimalForTree> animalForTreeLayerMain = new ArrayList<AnimalForTree>();
		List<AnimalForTree> animalForTreeLayerFather = new ArrayList<AnimalForTree>();
		List<AnimalForTree> animalForTreeLayerAdjustmentForMain = new ArrayList<AnimalForTree>();
		List<AnimalForTree> animalForTreeLayerMother = new ArrayList<AnimalForTree>();
		List<AnimalForTree> animalForTreeLayerRoot = new ArrayList<AnimalForTree>();
		List<AnimalForTree> animalForTreeLayerChildren = new ArrayList<AnimalForTree>();

		AnimalForTree adjustmentForMain = new AnimalForTree();
		AnimalForTree adjustmentForFather = new AnimalForTree();
		AnimalForTree adjustmentForMother = new AnimalForTree();
		AnimalForTree adjustmentForMain2 = new AnimalForTree();
		
		animalForTreeLayerMain.add(mainAnimal);
		
		//メインコアラの配偶者（複数の可能性あり）とその子供達
		int sex = mainAnimal.getSex(); //メインのコアラが父親か母親か
		List<AnimalForTree> childrenList = animalDao.getChildrenAnimalForTree(mainAnimal.getId(), sex); 
//		List<AnimalForTree> spouseList = new ArrayList<AnimalForTree>();
//		Map<Integer,  AnimalForTree> spouseMap = new HashMap<Integer,  AnimalForTree>();
		Map<Object, List<AnimalForTree>> childrenMap;
		
		for (AnimalForTree child : childrenList) {
			child.setNo_parent(false);
			child.setHidden(false);
			if(child.getProfileImagePath() == null){
				child.setProfileImagePath("/images/defaultAnimal.png");
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
		for (Entry<Object, List<AnimalForTree>> entry : childrenMap.entrySet()) {
			AnimalForTree spouseAnimal = animalDao.getAnimalForTree((int)entry.getKey());
			spouseAnimal.setHidden(false);
			if(spouseAnimal.getProfileImagePath() == null){
				mainAnimal.setProfileImagePath("/images/defaultAnimal.png");
			}
			animalForTreeLayerMain.add(	new AnimalForTree());
			if(maternalGrandFather.getProfileImagePath() == null){
				mainAnimal.setProfileImagePath("/images/defaultAnimal.png");
			}
			animalForTreeLayerMain.get(h).setChildren(childrenMap.get((int)entry.getKey())); //調整用のnewしたAnimalForTreeに子供リストをセット
			animalForTreeLayerMain.add(spouseAnimal);
			h += 2;
		}

//ゴミ。テスト用。リスト型をマップ型へ変換 keyはid
//		if (sex == 1) {
//			spouseMap = spouseList.stream().collect(Collectors.toMap(AnimalForTree::getMother_id, i -> i));
//		} else {
//			spouseMap = spouseList.stream().collect(Collectors.toMap(AnimalForTree::getFather_id, i -> i));
//		}

		
		//メインコアラの兄弟達
		
		if (mainAnimal.getMother_id() != 0 || mainAnimal.getFather_id() != 0) {
		List<AnimalForTree> brotherAnimalList = animalDao.getBrotherAnimalForTree(mainAnimal.getId(), mainAnimal.getMother_id(), mainAnimal.getFather_id());
		for (AnimalForTree brotherAnimal : brotherAnimalList) {
			brotherAnimal.setNo_parent(false);
			brotherAnimal.setHidden(false);
			animalForTreeLayerMain.add(brotherAnimal);			
		}
		}
		
		adjustmentForMain.setChildren(animalForTreeLayerMain);
		
		animalForTreeLayerAdjustmentForMain.add(adjustmentForMain);	
		animalForTreeLayerFather.add(fatherAnimal);
		animalForTreeLayerMother.add(motherAnimal);
		adjustmentForFather.setChildren(animalForTreeLayerFather);
		adjustmentForMother.setChildren(animalForTreeLayerMother);	
		adjustmentForMain2.setChildren(animalForTreeLayerAdjustmentForMain);
		
		animalForTreeLayerRoot.add(paternalGrandFather);
		animalForTreeLayerRoot.add(adjustmentForFather);
		animalForTreeLayerRoot.add(paternalGrandMother);
		animalForTreeLayerRoot.add(adjustmentForMain2);
		animalForTreeLayerRoot.add(maternalGrandFather);
		animalForTreeLayerRoot.add(adjustmentForMother);
		animalForTreeLayerRoot.add(maternalGrandMother);
		root.setChildren(animalForTreeLayerRoot);
		
		//②コアラの関係性のリストの作成、オブジェクトの詰め込み
		List<RelationForTree> relationForTree = new ArrayList<RelationForTree>();

		RelationForTree relationForTree1 = new RelationForTree();
		relationForTree1.setSource(fatherAnimal);
		relationForTree1.setTarget(motherAnimal);
		
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
		for (Entry<Object, List<AnimalForTree>> entry : childrenMap.entrySet()) {
			AnimalForTree spouseAnimal = animalDao.getAnimalForTree((int)entry.getKey());
			relationForTree.add(new RelationForTree());
			relationForTree.get(r).setSource(mainAnimal);
			relationForTree.get(r).setTarget(spouseAnimal);
			r++;
		}
		
		//①と②をMapでフロントに返却
		Map<String, Object> mapForTree = new HashMap<String, Object>();
		mapForTree.put("animalForTree", root);
		mapForTree.put("relationForTree", relationForTree);
		
		return mapForTree;
	}
	
	@Override
	public void update(AnimalInsertForm form) {
		Animal animal = new Animal();
		animal.setAnimal_id(form.getAnimal_id());
		animal.setName(form.getName());
		animal.setSex(form.getSex());
		Date birthDate = getDate(form.getBirthYear(), form.getBirthMonth(), form.getBirthDay());
		if (birthDate != null) {
			animal.setBirthdate(birthDate);
		}
		animal.setIs_alive(form.getIs_alive());
		Date deathDate = getDate(form.getDeathYear(), form.getDeathMonth(), form.getDeathDay());
		if (deathDate != null) {
			animal.setDeathdate(deathDate);
		}
		Animal motherAnimal = new Animal();
		Animal fatherAnimal = new Animal();
		motherAnimal.setAnimal_id(form.getMother_id());
		fatherAnimal.setAnimal_id(form.getFather_id());
		animal.setMotherAnimal(motherAnimal);
		animal.setFatherAnimal(fatherAnimal);
		animal.setDetails(form.getDetails());
		animal.setFeature(form.getFeature());
		animal.setProfileImagePath(form.getProfileImagePath());
		String profileImagePath =  null;
		if( !form.getAnimalProfileImageUpload().isEmpty()) {
			profileImagePath = form.getAnimalProfileImageUpload().getOriginalFilename()
					.substring(form.getAnimalProfileImageUpload().getOriginalFilename().lastIndexOf("."));
			animal.setProfileImagePath(profileImagePath);
		}
		animalDao.update(animal);

//		プロフィール画像登録 or 更新
	
		if (profileImagePath  != null) {
			insertAnimalProfileImage(form.getAnimal_id(), form.getAnimalProfileImageUpload(), profileImagePath);
		}
		
		//入退園履歴
		List<Date> admissionDateList = new ArrayList<Date>();
		for (int i = 0; i < form.getAdmissionYear().size(); i++) {
			Date admissionDate = getDate(form.getAdmissionYear().get(i),form.getAdmissionMonth().get(i), form.getAdmissionDay().get(i));
			admissionDateList.add(admissionDate);
		}
		
		List<Date> exitDateList = new ArrayList<Date>();
		for (int i = 0; i < form.getExitYear().size(); i++) {
			Date exitDate = getDate(form.getExitYear().get(i),form.getExitMonth().get(i), form.getExitDay().get(i));
			exitDateList.add(exitDate);
		}
		
		animalZooHistoryDao.deleteAllAnimalZooHistory(form.getAnimal_id());
		animalZooHistoryDao.insertZooHistory(form.getAnimal_id(), form.getInsertZoo(),admissionDateList, exitDateList);
		
	}

	@Override
	@Transactional
	public void delete(int animal_id) {
		animalDao.delete(animal_id);
	}

	@Override
	public void insertAnimalProfileImage(int animal_id, MultipartFile animalProfileImageUpload, String profileImagePath) {
		try {
			//
			File uploadFile = new File("images/" + animal_id + profileImagePath);

			byte[] bytes = fileResize(animalProfileImageUpload.getBytes(), profileImagePath.substring(1));

			if (bytes == null) {
				bytes = animalProfileImageUpload.getBytes();
			}

			BufferedOutputStream uploadFileStream = new BufferedOutputStream(new FileOutputStream(uploadFile));
			uploadFileStream.write(bytes);

			uploadFileStream.close();

			// cloudinaryに写真をアップロードする
			Map resultmap = cloudinaryService.uploadAnimalProfileImage(uploadFile, animal_id);
			String url = (String)resultmap.get("secure_url");
			animalDao.urlUpdate(animal_id, url);
			
			uploadFile.delete();
		} catch (Exception e) {
			// 異常終了時の処理
		} catch (Throwable t) {
			// 異常終了時の処理
		}

	}

	private void deleteDirs(int animal_id) {
		cloudinaryService.deleteDirs(animal_id);
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
			dst = new BufferedImage(new_width, new_height, src.getType()==0?5:src.getType());
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
