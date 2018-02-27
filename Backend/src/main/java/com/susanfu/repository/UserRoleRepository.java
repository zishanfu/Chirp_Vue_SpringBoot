package com.susanfu.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.susanfu.entity.security.UserRole;

public interface UserRoleRepository extends MongoRepository<UserRole, String>{

}
