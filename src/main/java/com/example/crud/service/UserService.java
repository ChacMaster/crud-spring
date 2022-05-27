package com.example.crud.service;

import org.springframework.stereotype.Service;

import com.example.crud.base.BaseService;
import com.example.crud.model.User;
import com.example.crud.repository.UserRepository;

@Service
public class UserService extends BaseService<Long, User, UserRepository> {

	public UserService(UserRepository repository) {
		super(repository);
	}
}
