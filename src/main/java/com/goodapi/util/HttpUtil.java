package com.goodapi.util;

import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * @author msaritas
 *
 */
public class HttpUtil {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Value("${rest.deftimeout}")
	private Integer defTimeout;

	private static final String AUTHORIZATION = "Authorization";

	private static final String BASIC = "Basic";

	/**
	 * Send a POST request to the given Url. The default timeout will be use.
	 *
	 * @param url
	 * @param payload
	 * @param httpHeaders
	 * @param responseType
	 * @param <E>
	 * @return
	 */
	public <E> E post(String url, Object payload, HttpHeaders httpHeaders, Class<E> responseType) {
		return post(url, payload, httpHeaders, responseType, 0);
	}

	/**
	 * Send a POST request to the given Url.
	 *
	 * @param url
	 * @param payload
	 * @param httpHeaders
	 * @param responseType
	 * @param timeout      Request timout value as secont. When 0 then default
	 *                     timeout value will be use
	 * @param <E>
	 * @return
	 */
	public <E> E post(String url, Object payload, HttpHeaders httpHeaders, Class<E> responseType, int timeout) {
		LOGGER.debug("[HttpUtil][POST][Address:{}]", url);
		RestTemplate restTemplate = null;
		if (timeout <= 0) {
			timeout = defTimeout;
		}
		restTemplate = new RestTemplate();
		restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
		HttpComponentsClientHttpRequestFactory rf = (HttpComponentsClientHttpRequestFactory) restTemplate
				.getRequestFactory();
		rf.setReadTimeout(timeout * 1000);
		rf.setConnectTimeout(timeout * 1000);

		HttpEntity<Object> request = new HttpEntity<Object>(payload, httpHeaders);
		return restTemplate.exchange(url, HttpMethod.POST, request, responseType).getBody();
	}

	/**
	 * 
	 * @param url
	 * @param payload
	 * @param httpHeaders
	 * @param responseType
	 * @param connectTimeOut
	 * @param readTimeOut
	 * @return
	 */
	public <E> E post(String url, Object payload, HttpHeaders httpHeaders, Class<E> responseType, int connectTimeOut,
			int readTimeOut) {
		LOGGER.debug("[HttpUtil][POST][Address:{}]", url);
		RestTemplate restTemplate = null;

		restTemplate = new RestTemplate();
		restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
		HttpComponentsClientHttpRequestFactory rf = (HttpComponentsClientHttpRequestFactory) restTemplate
				.getRequestFactory();
		rf.setReadTimeout(readTimeOut);
		rf.setConnectTimeout(connectTimeOut);

		HttpEntity<Object> request = new HttpEntity<Object>(payload, httpHeaders);
		return restTemplate.exchange(url, HttpMethod.POST, request, responseType).getBody();
	}

	/**
	 * Send a GET request to the given Url. The default timeout will be use.
	 *
	 * @param url
	 * @param httpHeaders
	 * @param responseType
	 * @param <E>
	 * @return
	 */
	public <E> E get(String url, HttpHeaders httpHeaders, Class<E> responseType) {
		return get(url, httpHeaders, responseType, 0);
	}

	/**
	 * Send a GET request to the given Url.
	 *
	 * @param url
	 * @param httpHeaders
	 * @param responseType
	 * @param timeout      Request timout value as secont. When 0 then default
	 *                     timeout value will be use
	 * @param <E>
	 * @return
	 */
	public <E> E get(String url, HttpHeaders httpHeaders, Class<E> responseType, int timeout) {
		LOGGER.debug("[HttpUtil][GET][Address:{}]", url);
		RestTemplate restTemplate = null;
		if (timeout <= 0) {
			timeout = defTimeout;
		}
		restTemplate = new RestTemplate();
		restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
		HttpComponentsClientHttpRequestFactory rf = (HttpComponentsClientHttpRequestFactory) restTemplate
				.getRequestFactory();
		rf.setReadTimeout(timeout * 1000);
		rf.setConnectTimeout(timeout * 1000);

		HttpEntity<Object> request = new HttpEntity<Object>(httpHeaders);
		return restTemplate.exchange(url, HttpMethod.GET, request, responseType).getBody();
	}

	/**
	 * 
	 * @param userName
	 * @param password
	 * @return
	 */
	public HttpHeaders setAuthentication(String userName, String password) {
		String plainCreds = userName + ":" + password;
		byte[] plainCredsBytes = plainCreds.getBytes();
		byte[] base64CredsBytes = Base64.getEncoder().encode(plainCredsBytes);
		String base64Creds = new String(base64CredsBytes);
		HttpHeaders headers = new HttpHeaders();
		headers.add(AUTHORIZATION, BASIC + base64Creds);
		headers.setContentType(MediaType.APPLICATION_JSON);
		return headers;
	}

}
