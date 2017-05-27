package com.jmp.controller;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jmp.dto.MenteeDto;
import com.jmp.service.MenteeService;

@RunWith(MockitoJUnitRunner.class)
public class MenteeControllerTest {

    private static final Integer MENTEE_ID = 100;
    private static final String MENTEES_ID = "/mentees/{id}";
    private static final String MENTEES = "/mentees";

    private static final ObjectMapper jsonObjectMapper = new ObjectMapper();

    @Mock
    private MenteeService menteeService;

    @InjectMocks
    private MenteeController menteeController;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(menteeController).build();
    }

    @Test
    public void findMenteeById() throws Exception {
        when(menteeService.find(MENTEE_ID)).thenReturn(new MenteeDto());
        mockMvc.perform(get(MENTEES_ID, MENTEE_ID)).andExpect(status().isOk());
        verify(menteeService).find(MENTEE_ID);
    }

    @Test
    public void createMentee() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = post(MENTEES).contentType(MediaType.APPLICATION_JSON).content(
                jsonObjectMapper.writeValueAsString(new MenteeDto()));
        mockMvc.perform(requestBuilder).andExpect(status().isCreated());
        verify(menteeService).create(any(MenteeDto.class));
    }

    @Test
    public void updateMentee() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = put(MENTEES).contentType(MediaType.APPLICATION_JSON).content(
                jsonObjectMapper.writeValueAsString(new MenteeDto()));
        mockMvc.perform(requestBuilder).andExpect(status().isOk());
        verify(menteeService).update(any(MenteeDto.class));
    }

    @Test
    public void deleteMenteeById() throws Exception {
        mockMvc.perform(delete(MENTEES_ID, MENTEE_ID)).andExpect(status().isNoContent());
        verify(menteeService).delete(MENTEE_ID);
    }

}
