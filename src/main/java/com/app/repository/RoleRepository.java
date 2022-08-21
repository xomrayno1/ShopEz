package com.app.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.app.model.Roles;

 
@Repository
public interface RoleRepository extends CrudRepository<Roles, Long>{

	 
}
