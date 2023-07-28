package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

class GenericControllerTest {

	private RestTemplate restTemplate;

	@BeforeEach
	public void setUp() {
		restTemplate = new RestTemplate();
	}

	public static HttpEntity<?> createHttpEntity(Object requestBody) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return new HttpEntity<>(requestBody, headers);
	}

	@Test
	void testHandleGenericRequest() throws Exception {
		GenericData<User, OtherClass> genericData = new GenericData<>();
		genericData.setData(getUserObject());
		genericData.setOther(getOtherClassObject());
		GenericData<String, String> genericData2 = new GenericData<>("inside data param", null);

		var body = Arrays.asList(genericData, genericData2, "teste");
		var bodyWithTypes = Utils.addTypes(body);
		ResponseEntity<Object[]> response = restTemplate.exchange("http://localhost:8080/generic", //
				HttpMethod.POST, createHttpEntity(bodyWithTypes), Object[].class);
		var resBody = response.getBody();
		for (Object object : resBody) {
			System.out.print(object + " | ");
		}
		System.out.println();
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(resBody);
	}

	private User getUserObject() {
		User user = new User();
		user.setName("The Developer");
		user.setAge(30);
		return user;
	}

	private OtherClass getOtherClassObject() {
		OtherClass otherClass = new OtherClass();
		otherClass.setName("Java");
		otherClass.setDesc("Dev");
		return otherClass;
	}
}
