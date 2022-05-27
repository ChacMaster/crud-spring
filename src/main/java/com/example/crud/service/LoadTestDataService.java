package com.example.crud.service;

import java.util.ArrayList;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.example.crud.model.Post;
import com.example.crud.repository.PostRepository;
import com.thedeanda.lorem.LoremIpsum;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class LoadTestDataService {

	private final PostRepository postRepository;

	@Transactional
	public Integer loadPosts() {
		var posts = new ArrayList<Post>();
		for (int i = 0; i < 1000; i++) {
			var lorem = LoremIpsum.getInstance();
			var post = Post.builder().title(lorem.getTitle(2, 4)).body(lorem.getWords(10, 40)).build();
			posts.add(post);
		}
		this.postRepository.saveAll(posts);
		return posts.size();
	}

	public void deletePosts() {
		this.postRepository.deleteAll();
	}
}
