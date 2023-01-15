package com.demo.example;

import static org.hamcrest.MatcherAssert.assertThat; 
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.beans.factory.annotation.Value;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class HttpRequestTest {

	@Value(value="${local.server.port}")
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	private static final String POST_BODY =
			"""
			{
				"customerNumber" : "12345",
				"age" : 8,
				"amount" : 50000,
				"customerType" : "LOYAL"
			}
			"""
			;
	
	
	@Test
	public void postShouldReturnDiscount() throws Exception {
		var headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    HttpEntity<String> request = new HttpEntity<String>(POST_BODY, headers);
		ResponseEntity<String> result = this.restTemplate
				.postForEntity("http://localhost:" + port + "/get-discount", request, String.class);
		assertThat(result.getBody(), allOf(containsString("discount"), containsString("20")));
	}
}