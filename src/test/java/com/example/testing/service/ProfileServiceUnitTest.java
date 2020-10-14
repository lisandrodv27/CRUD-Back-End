package com.example.testing.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.qa.rest.Profile;
import com.qa.rest.ProfileRepository;
import com.qa.service.ProfileService;

@SpringBootTest
public class ProfileServiceUnitTest {

	@Autowired
	private ProfileService service;

	@MockBean
	private ProfileRepository repository;

	@Test
	void testProfileCreate() {
		Long id = 1L;
		Profile newProfile = new Profile("Hannibal Barca", "Punic", "A description");
		Profile savedProfile = new Profile("Hannibal Barca", "Punic", "A description");
		savedProfile.setId(id);

		Mockito.when(this.repository.save(newProfile)).thenReturn(savedProfile);

		assertThat(this.repository.save(newProfile)).isEqualTo(savedProfile);
	}

	@Test
	void testProfileUpdate() {
		Long id = 1L;
		Profile newProfile = new Profile("Queen Victoria", "British", "A description");
		Profile oldProfile = new Profile("Some ancient fella", "Persian", "An ancient description");
		Profile updatedProfile = new Profile("Queen Victoria", "British", "A description");
		updatedProfile.setId(id);

		Mockito.when(this.repository.findById(id)).thenReturn(Optional.of(oldProfile));
		Mockito.when(this.repository.save(updatedProfile)).thenReturn(updatedProfile);

		assertThat(this.service.updateProfile(newProfile, id)).isEqualTo(updatedProfile);
	}

}
