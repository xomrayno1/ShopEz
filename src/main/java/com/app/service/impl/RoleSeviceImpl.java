package com.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.model.Roles;
import com.app.repository.RoleRepository;
import com.app.service.RoleService;


@Service
public class RoleSeviceImpl implements RoleService {
	@Autowired
	RoleRepository roleRepo;

	@Override
	public Roles findById(long id) {
		// TODO Auto-generated method stub
		return roleRepo.findById(id).orElse(null);
	}

}
