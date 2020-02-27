package com.example.demo.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;

	@Async
	public CompletableFuture<List<User>> saveUser(MultipartFile file) throws Exception {
		long start = System.currentTimeMillis();
		List<User> users = getCsvFile(file);
		users = userRepository.saveAll(users);
		long end = System.currentTimeMillis();
		return CompletableFuture.completedFuture(users);
	}

	@Async
	public CompletableFuture<List<User>> findAllUser() {
		List<User> users = userRepository.findAll();
		return CompletableFuture.completedFuture(users);
	}

	private List<User> getCsvFile(final MultipartFile mFile) throws Exception {
		final List<User> users = new ArrayList<>();
		try (final BufferedReader br = new BufferedReader(new InputStreamReader(mFile.getInputStream()))) {
			String line;
			while ((line = br.readLine()) != null) {
				final String[] data = line.split(",");
				final User user = new User();
				user.setName(data[0]);
				user.setEmail(data[1]);
				user.setGender(data[2]);
				users.add(user);
			}
			return users;
		} catch (IOException e) {
			throw new Exception("not found csv file !!");
		}

	}
}
