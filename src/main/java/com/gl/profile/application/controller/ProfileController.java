package com.gl.profile.application.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gl.profile.application.profile.model.UserProfile;
import com.gl.profile.application.service.ProfileService;

@RestController
@RequestMapping("/api/v1")
public class ProfileController {

	@Autowired
	private ProfileService profileService;

	@GetMapping("/profile/{id}")
	public ResponseEntity<UserProfile> getProfileById(@PathVariable String id) throws Exception {
		UserProfile userProfileResp = profileService.getProfileById(id);
		if (null != userProfileResp) {
			return ResponseEntity.ok().body(userProfileResp);
		} else {
			UserProfile userProfile = new UserProfile();
			userProfile.setId(Long.valueOf(id));
			return ResponseEntity.badRequest().body(userProfile);
		}

	}

	@PostMapping("/profile")
	public UserProfile createArticle(@RequestBody UserProfile userProfile) {
		return profileService.createArticle(userProfile);
	}

	@PutMapping("/profile")
	public ResponseEntity<UserProfile> updateProfile(@RequestBody UserProfile userProfileReq) throws Exception {
		UserProfile resp = profileService.updateProfile(userProfileReq);
		if (null != resp) {
			return ResponseEntity.ok(resp);
		} else {
			return ResponseEntity.badRequest().body(userProfileReq);
		}

	}

	@DeleteMapping("/profile")
	public Map<String, Boolean> deleteArticle(@RequestBody UserProfile userProfileReq) throws Exception {
		Map<String, Boolean> response = profileService.deleteArticle(userProfileReq);
		return response;
	}
}