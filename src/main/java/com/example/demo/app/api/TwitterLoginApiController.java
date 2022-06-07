package com.example.demo.app.api;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RequestMapping("/api")
@Controller
public class TwitterLoginApiController {

	@Autowired
    @Qualifier("spring.social.twitter.app-id")
    private String twitterApiKey;

    @Autowired
    @Qualifier("spring.social.twitter.app-secret")
    private String twitterApiSecret;
    
    @Autowired
    @Qualifier("spring.social.twitter.callback-url")
    private String twitterCallBackUrl;

    private HttpSession session;

    @RequestMapping("/oauth/twitter/request")
    String requestTwitter(){
        Twitter twitter = new TwitterFactory().getInstance();
        RequestToken requestToken;

        try {
            twitter.setOAuthConsumer(twitterApiKey, twitterApiSecret);
            requestToken = twitter.getOAuthRequestToken(twitterCallBackUrl);
            session.setAttribute("requestToken", requestToken);
        } catch (TwitterException e) {
            throw new RuntimeException();
        }
        return "redirect:" + requestToken.getAuthenticationURL();
    }

    @RequestMapping
    String loginTwitter(HttpServletRequest req, HttpServletResponse res){
        Configuration conf = ConfigurationContext.getInstance();
        RequestToken requestToken = (RequestToken) session.getAttribute("requestToken");

        if (requestToken == null){
            throw new RuntimeException("requestToken is null");
        }

        if (StringUtils.isBlank(requestToken.getTokenSecret())){
            throw new RuntimeException("Token Secret is blank")
        }

        

        return null;
    }



}
