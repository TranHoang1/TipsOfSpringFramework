package com.example.demo.controller;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.service.UserService;

@RestController
public class UserController {

	@Autowired
	UserService userService;
	

	@PostMapping(value = "/saves", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE }, produces = "application/json")
	public ResponseEntity saveUsers(@RequestParam(value = "files") MultipartFile[] mFile) throws Exception {
		for (MultipartFile multipartFile : mFile) {
			userService.saveUser(multipartFile);
		}
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@GetMapping(value = "/findAll")
	public CompletableFuture<ResponseEntity> findAll() {
		return userService.findAllUser().thenApply(ResponseEntity::ok);
	}

}
