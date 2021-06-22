package com.example.demo.service;

import com.example.demo.app.UserForm;
import com.example.demo.entity.LoginUser;

public interface UserService {
	
	LoginUser findById(int user_id);
	
	void updateMyPage(UserForm form);
}
