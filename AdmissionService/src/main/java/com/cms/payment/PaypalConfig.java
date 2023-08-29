//package com.cms.payment;
//
//
//import java.util.Map;
//
//
//import com.paypal.base.rest.APIContext;
//import com.paypal.base.rest.OAuthTokenCredential;
//import com.paypal.base.rest.PayPalRESTException;
//
//
//public class PaypalConfig {
//
//	
//	private String clientId;
//	
//	private String clientSecret;
//	
//	private String mode;
//
//	
//	public Map<String, String> paypalSdkConfig() {
//		return null;
//	}
//
//	
//	public OAuthTokenCredential oAuthTokenCredential() {
//		return null;
//	}
//
//	
//	public APIContext apiContext() throws PayPalRESTException {
//		return null;
//	}
//
//}
//
















package com.cms.payment;


import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.OAuthTokenCredential;
import com.paypal.base.rest.PayPalRESTException;


@Configuration
public class PaypalConfig {

	@Value("${paypal.client.id}")
	private String clientId;
	@Value("${paypal.client.secret}")
	private String clientSecret;
	@Value("${paypal.mode}")
	private String mode;

	@Bean
	public Map<String, String> paypalSdkConfig() {
		Map<String, String> configMap = new HashMap<>();
		configMap.put("mode", mode);
		return configMap;
//	return null;
	}

	@Bean
	public OAuthTokenCredential oAuthTokenCredential() {
		return new OAuthTokenCredential(clientId, clientSecret, paypalSdkConfig());
//	return null;
	}

	@Bean
	public APIContext apiContext() throws PayPalRESTException {
		APIContext context = new APIContext(oAuthTokenCredential().getAccessToken());
		context.setConfigurationMap(paypalSdkConfig());
		return context;
			//return apiContext(); //self corrected
//		return null;    extra added
	}

}
