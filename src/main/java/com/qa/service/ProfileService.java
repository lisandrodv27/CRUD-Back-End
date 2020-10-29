package com.qa.service;

import java.util.List;
import java.util.Optional;

importpackage com.qa.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qa.exceptions.ProfileNotFoundException;
import com.qa.rest.Profile;
import com.qa.rest.ProfileRepository;

@Service
public class ProfileService {

	@Autowired
	private ProfileRepository repository;

	private ProfileService(ProfileRepository repository) {
		super();
		this.repository = repository;
	}

	public List<Profile> getProfile() {
		return this.repository.findAll();
	}

	public Profile createProfile(Profile profile) {
		return this.repository.save(profile);
	}

	public boolean deleteProfile(Long id) {
		this.repository.deleteById(id);
		return !this.repository.existsById(id);
	}

	public Profile updateProfile(Profile profile, Long id) {
		Optional<Profile> optProfile = this.repository.findById(id);
		Profile oldProfile = optProfile.orElseThrow(() -> new ProfileNotFoundException());

		oldProfile.setName(profile.getName());
		oldProfile.setNationality(profile.getNationality());
		oldProfile.setDescription(profile.getDescription());

		Profile updatedProfile = this.repository.save(oldProfile);
		return updatedProfile;

	}
} 
