package com.example.demo.app.api;

import com.example.demo.app.UserAuthenticationUtil;
import com.example.demo.service.TwitterLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpSession;

@Controller
public class TwitterLoginApiController {

	@Autowired
	private HttpSession session;
	
	@Autowired
	private TwitterLoginService service;
	
	@Autowired
	private UserAuthenticationUtil userAuthenticationUtil;
	
	@Autowired
    @Qualifier("spring.social.twitter.app-id")
    String twitterAppId;

    @Autowired
    @Qualifier("spring.social.twitter.app-secret")
    String twitterAppsecret;
    
    @Autowired
    @Qualifier("spring.social.twitter.callback-url")
    String twitterCallBackUrl;


}
