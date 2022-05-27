package com.example.crud.repository;

import org.springframework.stereotype.Repository;

import com.example.crud.base.BaseRepository;
import com.example.crud.model.User;

@Repository
public interface UserRepository extends BaseRepository<User, Long> {

}
