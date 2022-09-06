package com.gl.profile.application.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.gl.profile.application.profile.model.UserProfile;

@Repository
public interface UserProfileRepository extends MongoRepository<UserProfile, Long>{

}