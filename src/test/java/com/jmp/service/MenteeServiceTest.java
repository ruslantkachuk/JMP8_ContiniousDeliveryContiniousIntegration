package com.jmp.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.BeanUtils;

import com.jmp.dto.MenteeDto;
import com.jmp.entity.Mentee;
import com.jmp.entity.Mentor;
import com.jmp.repository.MenteeRepository;
import com.jmp.repository.MentorRepository;

@RunWith(MockitoJUnitRunner.class)
public class MenteeServiceTest {

    private static final Integer MENTOR_ID = 100;
    private static final Integer MENTEE_ID = 200;
    private static final String MENTEE_EMAIL = "mentee@epam.com";

    @Captor
    private ArgumentCaptor<Mentee> menteeCaptor;
    @Mock
    private MenteeRepository menteeRepository;
    @Mock
    private MentorRepository mentorRepository;

    @InjectMocks
    private MenteeService menteeService;

    @Test
    public void findMentee() throws Exception {
        when(menteeRepository.findOne(anyInt())).thenReturn(prepareMenteeShortForm());
        MenteeDto actual = menteeService.find(MENTEE_ID);
        verify(menteeRepository).findOne(MENTEE_ID);
        assertThat(actual.getId(), is(MENTEE_ID));
    }

    @Test
    public void createMentee() throws Exception {
        when(menteeRepository.findOne(anyInt())).thenReturn(prepareMenteeShortForm());
        menteeService.create(prepareMenteeDtoShortForm());
        verify(menteeRepository).save(menteeCaptor.capture());
        assertThat(menteeCaptor.getValue().getEmail(), is(MENTEE_EMAIL));

    }

    @Test
    public void updateMentee() throws Exception {
        Mentee mentee = new Mentee();
        when(menteeRepository.findOne(anyInt())).thenReturn(mentee);
        menteeService.update(prepareMenteeDtoShortForm());
        assertThat(mentee, is(equalTo(prepareMenteeShortForm())));
    }

    @Test
    public void updateMenteeWithMentor() throws Exception {
        Mentee mentee = new Mentee();
        Mentor mentor = new Mentor();
        when(menteeRepository.findOne(anyInt())).thenReturn(mentee);
        when(mentorRepository.findOne(anyInt())).thenReturn(mentor);
        menteeService.update(prepareMenteeDtoShortFormWithMentorId());
        assertThat(mentee.getMentor(), is(equalTo(mentor)));
    }

    @Test
    public void deleteMentee() throws Exception {
        menteeService.delete(MENTEE_ID);
        verify(menteeRepository).delete(MENTEE_ID);
    }

    private Mentee prepareMenteeShortForm() {
        Mentee mentee = new Mentee();
        mentee.setId(MENTEE_ID);
        mentee.setEmail(MENTEE_EMAIL);
        return mentee;
    }

    private MenteeDto prepareMenteeDtoShortForm() {
        MenteeDto menteeDto = new MenteeDto();
        BeanUtils.copyProperties(prepareMenteeShortForm(), menteeDto);
        return menteeDto;
    }

    private MenteeDto prepareMenteeDtoShortFormWithMentorId() {
        MenteeDto menteeDto = new MenteeDto();
        BeanUtils.copyProperties(prepareMenteeShortForm(), menteeDto);
        menteeDto.setIdMentor(MENTOR_ID);
        return menteeDto;
    }

}
