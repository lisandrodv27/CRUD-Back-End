package com.qa.rest;

import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.qa.service.ProfileService;

@RestController
@CrossOrigin
public class ProfileController {

	private ProfileService service;

	private ProfileController(ProfileService service) {
		super();
		this.service = service;
	}

	@PostMapping("/create")
	public ResponseEntity<Profile> createProfile(@RequestBody Profile profile) {
		return new ResponseEntity<Profile>(this.service.createProfile(profile), HttpStatus.CREATED);
	}

	@GetMapping("/get")
	public ResponseEntity<List<Profile>> getProfile() {
		return ResponseEntity.ok(this.service.getProfile());
	}

	@DeleteMapping("/remove/{id}")
	public ResponseEntity<Object> deleteProfile(@PathVariable Long id) {
		if (this.service.deleteProfile(id)) {
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/update")
	public ResponseEntity<Profile> updateProfile(@RequestBody Profile profile, @PathParam("id") Long id) {
		return new ResponseEntity<Profile>(this.service.updateProfile(profile, id), HttpStatus.ACCEPTED);
	}

}
