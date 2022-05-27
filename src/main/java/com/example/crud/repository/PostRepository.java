package com.example.crud.repository;

import org.springframework.stereotype.Repository;

import com.example.crud.base.BaseRepository;
import com.example.crud.model.Post;

@Repository
public interface PostRepository extends BaseRepository<Post, Long> {

}
