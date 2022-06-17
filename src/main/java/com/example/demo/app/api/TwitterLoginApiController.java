//SPAテスト用に実装したが、本番では不要
//package com.example.demo.app.api;
//
//import com.example.demo.entity.LoginUser;
//import com.example.demo.service.TwitterLoginService;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import twitter4j.Twitter;
//import twitter4j.TwitterException;
//import twitter4j.TwitterFactory;
//import twitter4j.auth.AccessToken;
//import twitter4j.auth.OAuthAuthorization;
//import twitter4j.auth.RequestToken;
//import twitter4j.conf.Configuration;
//import twitter4j.conf.ConfigurationContext;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
//@RequestMapping("/api")
//@Controller
//public class TwitterLoginApiController {
//
//	@Autowired
//    @Qualifier("spring.social.twitter.app-id")
//    private String twitterApiKey;
//
//    @Autowired
//    @Qualifier("spring.social.twitter.app-secret")
//    private String twitterApiSecret;
//
//    @Autowired
//    @Qualifier("spring.social.twitter.callback-url")
//    private String twitterCallBackUrl;
//
//    @Autowired
//    private HttpSession session;
//
//    @Autowired
//    private TwitterLoginService twitterLoginService;
//
//    @RequestMapping("/oauth/twitter/request")
//    String requestTwitter(){
//        Twitter twitter = new TwitterFactory().getInstance();
//        RequestToken requestToken;
//
//        try {
//            twitter.setOAuthConsumer(twitterApiKey, twitterApiSecret);
//            requestToken = twitter.getOAuthRequestToken(twitterCallBackUrl);
//            session.setAttribute("requestToken", requestToken);
//        } catch (TwitterException e) {
//            throw new RuntimeException();
//        }
//        return "redirect:" + requestToken.getAuthenticationURL();
//    }
//
//    @RequestMapping("/oauth/twitter/login")
//    String loginTwitter(HttpServletResponse response,HttpServletRequest request) {
//
//        Configuration conf = ConfigurationContext.getInstance();
//        RequestToken requestToken = (RequestToken) session.getAttribute("requestToken");
//
//        // token secretが無い場合はエラーとする。
//        if (requestToken == null || StringUtils.isBlank(requestToken.getTokenSecret())) {
//            throw new RuntimeException("token secret is null.");
//        }
//
//        AccessToken accessToken = new AccessToken(requestToken.getToken(), requestToken.getTokenSecret());
//        OAuthAuthorization oath = new OAuthAuthorization(conf);
//
//        oath.setOAuthAccessToken(accessToken);
//        String verifier = request.getParameter("oauth_verifier");
//        try {
//            if(verifier != null) {
//                accessToken = oath.getOAuthAccessToken(verifier);
//            }else {
//                return "redirect:/login";
//            }
//            Twitter twitter = new TwitterFactory().getInstance();
//            twitter.setOAuthConsumer(twitterApiKey, twitterApiSecret);
//            twitter.setOAuthAccessToken(accessToken);
//            //ユーザーを認証する
//            twitterLoginService.userLogin(twitter.verifyCredentials(),response,request);
//        } catch (TwitterException e) {
//            e.printStackTrace();
//        }
//
//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//
//        return "redirect:/user/mypage/" + ((LoginUser) principal).getUser_id();
//    }
//
//
//
//
//
//
//}
