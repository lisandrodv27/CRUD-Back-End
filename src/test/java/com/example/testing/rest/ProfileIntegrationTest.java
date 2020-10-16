package com.example.testing;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.rest.Profile;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ProfileIntegrationTest {

	@Autowired
	private MockMvc mockMVC;

	@Autowired
	private ObjectMapper mapper;

	@Test
	void readProfileTest() throws Exception {
		Profile profile = new Profile("Julius Caesar", "Roman", "A description");
		profile.setId(1L);
		List<Profile> profiles = new ArrayList<>();
		profiles.add(profile);
		String getResponseBody = this.mapper.writeValueAsString(profiles);
		this.mockMVC.perform(get("/get")).andExpect(status().isOk()).andExpect(content().json(getResponseBody));

	}

	@Test
	void createProfileTest() throws Exception {
		Profile newProfile = new Profile("Julius Caesar", "Roman", "A description");
		String body = this.mapper.writeValueAsString(newProfile);
		RequestBuilder createRequestBody = post("/create").contentType(MediaType.APPLICATION_JSON).content(body);

		ResultMatcher checkStatus = status().isCreated();

		Profile savedProfile = new Profile("Julius Caesar", "Roman", "A description");
		savedProfile.setId(1L); // 2L??

		String resultBody = this.mapper.writeValueAsString(savedProfile);
		ResultMatcher checkBody = content().json(resultBody);

		this.mockMVC.perform(createRequestBody).andExpect(checkStatus).andExpect(checkBody);

		MvcResult result = this.mockMVC.perform(createRequestBody).andExpect(checkStatus).andReturn();

	}

	@Test
	void deleteProfileTest() throws Exception {
		RequestBuilder deleteRequest = delete("/remove/1");
		ResultMatcher checkStatus = status().is(200);
		this.mockMVC.perform(deleteRequest).andExpect(checkStatus);

	}

	@Test
	void updateProfileTest() throws Exception {
		Profile newProfile = new Profile("Julius Caesar", "Roman", "A description");
		String body = this.mapper.writeValueAsString(newProfile);
		RequestBuilder updateRequestBody = put("/update?id=1").contentType(MediaType.APPLICATION_JSON).content(body);

		ResultMatcher checkStatus = status().isAccepted();

		Profile savedProfile = new Profile("Julius Caesar", "Roman", "A description");
		savedProfile.setId(1L);

		String resultBody = this.mapper.writeValueAsString(savedProfile);
		ResultMatcher checkBody = content().json(resultBody);

		this.mockMVC.perform(updateRequestBody).andExpect(checkStatus).andExpect(checkBody);

		MvcResult result = this.mockMVC.perform(updateRequestBody).andExpect(checkStatus).andReturn();

	}

}
