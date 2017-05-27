package com.jmp.controller;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.assertj.core.util.Lists;
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
import com.jmp.dto.MentorDto;
import com.jmp.service.MentorService;

@RunWith(MockitoJUnitRunner.class)
public class MentorControllerTest {

    private static final Integer MENTOR_ID = 100;
    private static final String MENTOR_SKILL = "Java";
    private static final String MENTOR_PARAM_SKILL = "skill";

    private static final String MENTORS_ID = "/mentors/{id}";
    private static final String MENTORS_ID_MENTEES = "/mentors/{id}/mentees";
    private static final String MENTORS = "/mentors";

    private static final ObjectMapper jsonObjectMapper = new ObjectMapper();

    @Mock
    private MentorService mentorService;

    @InjectMocks
    private MentorController mentorController;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(mentorController).build();
    }

    @Test
    public void findMentorById() throws Exception {
        when(mentorService.find(MENTOR_ID)).thenReturn(new MentorDto());
        mockMvc.perform(get(MENTORS_ID, MENTOR_ID)).andExpect(status().isOk());
        verify(mentorService).find(MENTOR_ID);
    }

    @Test
    public void findMenteesByMentorId() throws Exception {
        when(mentorService.findMentees(MENTOR_ID)).thenReturn(Lists.newArrayList(new MenteeDto()));
        mockMvc.perform(get(MENTORS_ID_MENTEES, MENTOR_ID)).andExpect(status().isOk());
        verify(mentorService).findMentees(MENTOR_ID);
    }

    @Test
    public void findMenteesBySkill() throws Exception {
        when(mentorService.findBySkill(MENTOR_SKILL)).thenReturn(Lists.newArrayList(new MentorDto()));
        mockMvc.perform(get(MENTORS).param(MENTOR_PARAM_SKILL, MENTOR_SKILL)).andExpect(status().isOk());
        verify(mentorService).findBySkill(MENTOR_SKILL);
    }

    @Test
    public void createMentor() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = post(MENTORS).contentType(MediaType.APPLICATION_JSON).content(
                jsonObjectMapper.writeValueAsString(new MentorDto()));
        mockMvc.perform(requestBuilder).andExpect(status().isCreated());
        verify(mentorService).create(any(MentorDto.class));
    }

    @Test
    public void updateMentor() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = put(MENTORS).contentType(MediaType.APPLICATION_JSON).content(
                jsonObjectMapper.writeValueAsString(new MentorDto()));
        mockMvc.perform(requestBuilder).andExpect(status().isOk());
        verify(mentorService).update(any(MentorDto.class));
    }

    @Test
    public void deleteMentorById() throws Exception {
        mockMvc.perform(delete(MENTORS_ID, MENTOR_ID)).andExpect(status().isNoContent());
        verify(mentorService).delete(MENTOR_ID);
    }

}
