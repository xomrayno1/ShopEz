package com.app.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.app.model.Users;


public interface UserService {
	Users getOne(Long id);
	
	Users findById(long id);
	 
	void delete(Users users);
	
	Users save(Users users);
	
	Page<Users> findAllSearchPagination(String search, Pageable pageable);
	
	void restore(Users users);
	
	boolean isExistEmail(String email);
	
	boolean isExistUsername(String username);
	
	Users findByUsername(String username);
	
	List<Users> findAll();
	
	Page<Users> doFilterSearchPagingUsers(String searchKey,  int pageSize, int pageNumber);
	 
}
