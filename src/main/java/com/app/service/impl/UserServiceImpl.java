package com.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.app.model.Users;
import com.app.repository.UserRepository;
import com.app.response.specification.UserSpecification;
import com.app.service.UserService;
import com.app.utils.Constant;
 
@Service
public class UserServiceImpl  implements UserService{

	@Autowired
	UserRepository userRepo;
	
	@Override
	public Users getOne(Long id) {
		// TODO Auto-generated method stub
		return userRepo.findById(id).orElse(null);
	}

	@Override
	public Users findById(long id) {
		// TODO Auto-generated method stub
		return userRepo.findById(id);
	}

	@Override
	public void delete(Users users) {
		users.setStatus(Constant.Status.IN_ACTIVE.getValue());
		userRepo.save(users);
	}

	@Override
	public Users save(Users users) {
		// TODO Auto-generated method stub
		return userRepo.save(users);
	}

	@Override
	public Page<Users> findAllSearchPagination(String search,Pageable pageable) {
		// TODO Auto-generated method stub
		return userRepo.findAllSearch(search, pageable);
	}

	 
	@Override
	public List<Users> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void restore(Users users) {
		// TODO Auto-generated method stub
		users.setStatus(Constant.Status.ACTIVE.getValue());
		userRepo.save(users);
	}

	@Override
	public boolean isExistEmail(String email) {
		// TODO Auto-generated method stub
		return userRepo.findByEmail(email) != null ? true : false;
	}

	@Override
	public boolean isExistUsername(String username) {
		// TODO Auto-generated method stub
		return userRepo.findByUsername(username) != null ? true : false;
	}

	@Override
	public Users findByUsername(String username) {
		// TODO Auto-generated method stub
		return userRepo.findByUsername(username);
	}

	@Override
	public Page<Users> doFilterSearchPagingUsers(String searchKey, Integer status, int pageSize, int pageNumber) {
		// TODO Auto-generated method stub
		return userRepo.findAll(new UserSpecification(searchKey, status),
				PageRequest.of(pageNumber - 1, pageSize));
	}

 
}
