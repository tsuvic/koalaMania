package com.example.demo.app;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ZooHistoryValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return AnimalInsertForm.class.isAssignableFrom(clazz);
	}

	@Override
	    public void validate(Object target, Errors errors) {

	    	AnimalInsertForm form = (AnimalInsertForm) target;
	    	List<Integer> insertZoo = form.getInsertZoo();
	    	List<String> admissionYear = form.getAdmissionYear();
	    	List<String> admissionMonth = form.getAdmissionMonth();
	    	List<String> admissionDay = form.getAdmissionDay();
	    	List<String> exitYear = form.getExitYear();
	    	List<String> exitMonth = form.getExitMonth();
	    	List<String> exitDay = form.getExitDay();
	    	String birthYear = form.getBirthYear();
	    	String birthMonth = form.getBirthMonth();
	    	String birthDay = form.getBirthDay();
	    	
//	    	未入力の場合、相関チェックを実施しない
//	        if (!StringUtils.hasLength(password)) { 
//	            return;
//	        }
	    	
	        if (!Objects.equals(admissionYear.get(0), birthYear)) {
	            errors.rejectValue("admissionYear", "",
	            "※バリデーションテスト。誕生日と最初の動物園入園日は同一年である必要があります。");
	        }
	        
	        for (int i = 0; i < insertZoo.size(); i++) {
		        if (insertZoo.get(i) == -1) {
		            errors.rejectValue("insertZoo", "", "※バリデーションテスト。動物園は入力必須");
		            break;
	        }
		}
	        
	    }
}
