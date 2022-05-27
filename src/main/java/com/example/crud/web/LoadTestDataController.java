package com.example.crud.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.crud.service.LoadTestDataService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/load-test-data")
public class LoadTestDataController {

	private final LoadTestDataService service;

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public String store() {
		return this.service.loadPosts() + " posts cadastrados!";
	}
}
