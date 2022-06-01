package com.example.crud.web.pages;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.example.crud.model.Post;
import com.example.crud.service.PostService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
@ManagedBean
@ViewScoped
public class PostPageController {
    private final PostService service;
    private List<Post> posts;

    @PostConstruct
    public void init() {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        posts = service.findAll(null, sort).stream().limit(100).toList();
    }

    public List<Post> getPosts() {
        return posts;
    }
}