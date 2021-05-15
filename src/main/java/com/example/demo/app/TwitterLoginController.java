package com.example.demo.app;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.OAuthAuthorization;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationContext;

@Controller
public class TwitterLoginController {

	@Autowired
	private HttpSession session;

	@Autowired
	private HttpServletRequest request;
	
	@Autowired
    @Qualifier("spring.social.twitter.app-id")
    String twitterAppId;

    @Autowired
    @Qualifier("spring.social.twitter.app-secret")
    String twitterAppsecret;
    
    @Autowired
    @Qualifier("spring.social.twitter.callback-url")
    String twitterCallBackUrl;

	@RequestMapping("/oauth/twitter/auth")
	String loginTwitter() {

		Twitter twitter = new TwitterFactory().getInstance();
		RequestToken requestToken = null;
		
		try {
			twitter.setOAuthConsumer(twitterAppId, twitterAppsecret);
			requestToken = twitter.getOAuthRequestToken(twitterCallBackUrl);
			session.setAttribute("requestToken", requestToken);
		} catch (TwitterException e) {
			throw new RuntimeException(e);
		}
		return "redirect:" + requestToken.getAuthenticationURL();
	}

	@RequestMapping("/oauth/twitter/access")
	String loginTwitterAccess() {
		
		Configuration conf = ConfigurationContext.getInstance();
		RequestToken requestToken = (RequestToken) session.getAttribute("requestToken");

		// token secretが無い場合はエラーとする。
		if (requestToken == null || StringUtils.isBlank(requestToken.getTokenSecret())) {
			throw new RuntimeException("token secret is null.");
		}

		AccessToken accessToken = new AccessToken(requestToken.getToken(), requestToken.getTokenSecret());
		OAuthAuthorization oath = new OAuthAuthorization(conf);

		oath.setOAuthAccessToken(accessToken);
		String verifier = request.getParameter("oauth_verifier");
		try {
			accessToken = oath.getOAuthAccessToken(verifier);
			Twitter twitter = new TwitterFactory().getInstance();
			twitter.setOAuthConsumer(twitterAppId, twitterAppsecret);
			twitter.setOAuthAccessToken(accessToken);
			System.out.println(twitter.verifyCredentials());
			
		} catch (TwitterException e) {
			e.printStackTrace();
		}

		//User user = new User(accessToken.getScreenName(), new Long(accessToken.getUserId()).toString(),AuthorityUtils.createAuthorityList("ROLE_USER"));

		/*UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user, null,
				AuthorityUtils.createAuthorityList("ROLE_USER"));
		SecurityContextHolder.getContext().setAuthentication(token);*/

		session.setAttribute("accessToken", accessToken);

		return "redirect:/";
	}
}
