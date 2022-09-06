package com.gl.profile.application.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.gl.profile.application.profile.model.UserProfile;
import com.gl.profile.application.repository.UserProfileRepository;

@Service
public class ProfileService {

	@Autowired
	private UserProfileRepository userProfileRepository;

	@Autowired
	private SequenceGeneratorService sequenceGenerator;

	public UserProfile getProfileById(@PathVariable String id) throws Exception {
		Optional<UserProfile> userProfileResp = userProfileRepository.findById(Long.valueOf(id));
		if (userProfileResp.isPresent()) {
			return userProfileResp.get();
		}
		return null;
	}

	public UserProfile createArticle(@RequestBody UserProfile userProfile) {
		userProfile.setId(sequenceGenerator.generateSequence(UserProfile.SEQUENCE_NAME));
		return userProfileRepository.save(userProfile);
	}

	public UserProfile updateProfile(@RequestBody UserProfile userProfileReq) throws Exception {

		Optional<UserProfile> userProfileOpt = userProfileRepository.findById(userProfileReq.getId());
		if (userProfileOpt.isPresent()) {
			UserProfile userProfile = userProfileOpt.get();
			if (userProfileReq.getUser_id() > 0) {
				userProfile.setUser_id(userProfileReq.getUser_id());
			}
			if (null != userProfileReq.getCategory_id()) {
				userProfile.setCategory_id(userProfileReq.getCategory_id());
			}
			if (null != userProfileReq.getBio()) {
				userProfile.setBio(userProfileReq.getBio());
			}
			if (null != userProfileReq.getImage_Url()) {
				userProfile.setImage_Url(userProfileReq.getImage_Url());
			}
			if (null != userProfileReq.getFirst_name()) {
				userProfile.setFirst_name(userProfileReq.getFirst_name());
			}
			if (null != userProfileReq.getLast_name()) {
				userProfile.setImage_Url(userProfileReq.getImage_Url());
			}
			if (null != userProfileReq.getPhoneno()) {
				userProfile.setPhoneno(userProfileReq.getPhoneno());
			}
			return userProfileRepository.save(userProfile);

		} else {
			return userProfileReq;
		}

	}

	public Map<String, Boolean> deleteArticle(@RequestBody UserProfile userProfileReq) throws Exception {
		Map<String, Boolean> response = new HashMap<>();
		Optional<UserProfile> userProfileOpt = userProfileRepository.findById(userProfileReq.getId());
		if (userProfileOpt.isPresent()) {
			userProfileRepository.delete(userProfileReq);
			response.put("deleted", Boolean.TRUE);
		} else {
			response.put("deleted", Boolean.FALSE);
		}
		return response;
	}

}
