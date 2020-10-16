package com.qa.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import com.qa.rest.Profile;
import com.qa.rest.ProfileRepository;

@SpringBootTest
@ActiveProfiles(profiles = "test")
public class ProfileServiceUnitTest {

	@Autowired
	private ProfileService service;

	@MockBean
	private ProfileRepository repository;

	@Test
	void testProfileCreate() {
		Long id = 1L;
		Profile newProfile = new Profile("Julius Caesar", "Roman", "Famous Roman general");
		Profile savedProfile = new Profile("Hannibal Barca", "Punic", "A description");
		savedProfile.setId(id);

		Mockito.when(this.repository.save(newProfile)).thenReturn(savedProfile);

		assertThat(this.repository.save(newProfile)).isEqualTo(savedProfile);

		Mockito.verify(this.repository, Mockito.times(1)).save(newProfile);
	}

	@Test
	void testProfileRead() {
		Profile profile = new Profile("Julius Caesar", "Roman", "Famous Roman general");
		profile.setId(1L);
		List<Profile> profiles = new ArrayList<>();
		profiles.add(profile);

		Mockito.when(this.repository.findAll()).thenReturn(profiles);

		assertThat(this.service.getProfile()).isEqualTo(profiles);

		Mockito.verify(this.repository, Mockito.times(1)).findAll();

	}

	@Test
	void testProfileDelete() {
		Long id = 1L;
		boolean foundProfile = false;

		Mockito.when(this.repository.existsById(id)).thenReturn(false);

		assertThat(this.service.deleteProfile(id)).isEqualTo(true);

		Mockito.verify(this.repository, Mockito.times(1)).existsById(id);

	}

	@Test
	void testProfileUpdate() {
		Long id = 1L;
		Profile newProfile = new Profile("Queen Victoria", "British", "A royal description");
		Profile oldProfile = new Profile("Some ancient fella", "Persian", "An ancient description");
		Profile updatedProfile = new Profile("Queen Victoria", "British", "A royal description");
		updatedProfile.setId(id);

		Mockito.when(this.repository.findById(id)).thenReturn(Optional.of(oldProfile));
		Mockito.when(this.repository.save(updatedProfile)).thenReturn(updatedProfile);

		assertThat(this.service.updateProfile(newProfile, id)).isEqualTo(updatedProfile);

		Mockito.verify(this.repository, Mockito.times(1)).findById(id);
		Mockito.verify(this.repository, Mockito.times(1)).save(updatedProfile);
	}
}
