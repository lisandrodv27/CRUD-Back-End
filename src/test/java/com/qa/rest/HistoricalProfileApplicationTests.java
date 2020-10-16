package com.qa.rest;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.qa.rest.Profile;

import nl.jqno.equalsverifier.EqualsVerifier;

@SpringBootTest
class HistoricalProfileApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void ProfileTestCoverage() {
		EqualsVerifier.forClass(Profile.class).usingGetClass().verify();
	}

}
