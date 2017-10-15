package com.app.integration;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.hamcrest.collection.IsCollectionWithSize;
import org.hamcrest.core.Is;
import org.hamcrest.core.IsNot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CounterIntegrationTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void acceptanceCriteriaFindCountsForSearchParams() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/counter-api/search")
				.with(SecurityMockMvcRequestPostProcessors.user("user").password("password"))
				.content("{\"searchText\":[\"Duis\",\"Sed\",\"Donec\",\"Augue\",\"Pellentesque\",\"123\"]}")
				.contentType(MediaType.APPLICATION_JSON);
		
		mockMvc.perform(requestBuilder)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("counts", IsCollectionWithSize.hasSize(6)))
				.andExpect(MockMvcResultMatchers.jsonPath("counts[?(@.Duis)].Duis", Is.is(new ArrayList<Integer>(){{add(11);}})))
				.andExpect(MockMvcResultMatchers.jsonPath("counts[?(@.Sed)].Sed", Is.is(new ArrayList<Integer>(){{add(16);}})))
				.andExpect(MockMvcResultMatchers.jsonPath("counts[?(@.Donec)].Donec", Is.is(new ArrayList<Integer>(){{add(8);}})))
				.andExpect(MockMvcResultMatchers.jsonPath("counts[?(@.Augue)].Augue", Is.is(new ArrayList<Integer>(){{add(7);}})))
				.andExpect(MockMvcResultMatchers.jsonPath("counts[?(@.Pellentesque)].Pellentesque", Is.is(new ArrayList<Integer>(){{add(6);}})))
				.andExpect(MockMvcResultMatchers.jsonPath("counts[?(@.123)].123", Is.is(new ArrayList<Integer>(){{add(0);}})));
	}
	
	@Test
	public void acceptanceCriteriaTopWordsInFile() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/counter-api/5")
					.with(SecurityMockMvcRequestPostProcessors.user("user").password("password"))
					.accept("text/csv");

		MvcResult mvcResult = mockMvc.perform(requestBuilder)
									.andExpect(MockMvcResultMatchers.status().isOk())
									.andExpect(MockMvcResultMatchers.content().contentType("text/csv"))
									.andReturn();
		assertEquals(5, mvcResult.getResponse().getContentAsString().split("\\n").length);
	}
	
	@Test
	public void testRetrievalCountForSearchParams() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/counter-api/search")
							.with(SecurityMockMvcRequestPostProcessors.user("user").password("password"))
							.content("{\"searchText\":[\"Duis\"]}")
							.contentType(MediaType.APPLICATION_JSON);
		
		mockMvc.perform(requestBuilder)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("counts", IsCollectionWithSize.hasSize(1)))
				.andExpect(MockMvcResultMatchers.jsonPath("counts[0].Duis", Is.is(11)));
	}
	
	@Test
	public void testRetrievalCountUnauthorized() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/counter-api/search")
							.content("{\"searchText\":[\"Duis\"]}")
							.contentType(MediaType.APPLICATION_JSON);
		
		mockMvc.perform(requestBuilder)
				.andExpect(MockMvcResultMatchers.status().isUnauthorized());
	}
	
	@Test
	public void testRetrievalCountNoRequestParams() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/counter-api/search")
							.with(SecurityMockMvcRequestPostProcessors.user("user").password("password"))
							.contentType(MediaType.APPLICATION_JSON);
		
		mockMvc.perform(requestBuilder)
				.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	
	@Test
	public void testRetrievalCountEmptyParams() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/counter-api/search")
				.with(SecurityMockMvcRequestPostProcessors.user("user").password("password"))
				.content("{\"searchText\":[]}")
				.contentType(MediaType.APPLICATION_JSON);

			mockMvc.perform(requestBuilder)
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andExpect(MockMvcResultMatchers.jsonPath("counts", IsNot.not(0)));
	}
	
	@Test
	public void testRetrievalCountEmptyStringParams() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/counter-api/search")
				.with(SecurityMockMvcRequestPostProcessors.user("user").password("password"))
				.content("{\"searchText\":[\"\"]}")
				.contentType(MediaType.APPLICATION_JSON);

			mockMvc.perform(requestBuilder)
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andExpect(MockMvcResultMatchers.jsonPath("counts[0].*", Is.is(new ArrayList<Integer>(){{add(0);}})));
	}
	
	@Test
	public void testTopWordCounts() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/counter-api/10")
				.with(SecurityMockMvcRequestPostProcessors.user("user").password("password"))
				.accept("text/csv");

			mockMvc.perform(requestBuilder)
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andExpect(MockMvcResultMatchers.content().contentType("text/csv"));
	}
	
	@Test
	public void testTopWordCountsUnAuthorized() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/counter-api/10")
				.contentType(MediaType.APPLICATION_JSON)
				.accept("text/csv");

			mockMvc.perform(requestBuilder)
					.andExpect(MockMvcResultMatchers.status().isUnauthorized());
	}
	
	@Test
	public void testTopWordCountsXLSFileRequest() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/counter-api/10")
				.with(SecurityMockMvcRequestPostProcessors.user("user").password("password"))
				.accept("application/vnd.ms-excel");

		mockMvc.perform(requestBuilder)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("application/vnd.ms-excel"));
	}
	
	@Test
	public void testTopWordCountMissingParams() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/counter-api/ten")
				.with(SecurityMockMvcRequestPostProcessors.user("user").password("password"))
				.accept(MediaType.TEXT_PLAIN);

		mockMvc.perform(requestBuilder)
				.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	
	@Test
	public void testTopWordCountZeroParameter() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/counter-api/0")
				.with(SecurityMockMvcRequestPostProcessors.user("user").password("password"))
				.accept("text/csv");

		mockMvc.perform(requestBuilder)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("text/csv"))
				.andExpect(MockMvcResultMatchers.content().bytes(new byte[0]));
	}
	
}
