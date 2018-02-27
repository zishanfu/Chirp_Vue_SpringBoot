package com.susanfu.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.susanfu.entity.security.Role;

public interface RoleRepository extends MongoRepository<Role, String>{
	
}
