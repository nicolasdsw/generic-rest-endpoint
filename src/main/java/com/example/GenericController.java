package com.example;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/generic")
public class GenericController {

	@PostMapping
	public ResponseEntity<List<Object>> handleGenericRequest(@RequestBody(required = false) List<Object> params) {
		try {
			var arr = Utils.removeTypes(params);
			System.out.println(arr);
			return ResponseEntity.ok(arr);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

}
