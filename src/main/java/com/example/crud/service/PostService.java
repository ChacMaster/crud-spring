package com.example.crud.service;

import org.springframework.stereotype.Service;

import com.example.crud.base.BaseService;
import com.example.crud.model.Post;
import com.example.crud.repository.PostRepository;

@Service
public class PostService extends BaseService<Long, Post, PostRepository> {

	public PostService(PostRepository repository) {
		super(repository);
	}
}
