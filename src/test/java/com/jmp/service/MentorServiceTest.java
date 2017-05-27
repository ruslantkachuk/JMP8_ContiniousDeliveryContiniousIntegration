package com.jmp.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.BeanUtils;

import com.jmp.dto.MenteeDto;
import com.jmp.dto.MentorDto;
import com.jmp.entity.Mentee;
import com.jmp.entity.Mentor;
import com.jmp.repository.MentorRepository;

@RunWith(MockitoJUnitRunner.class)
public class MentorServiceTest {

    private static final Integer MENTOR_ID = 100;
    private static final String MENTOR_EMAIL = "mentor@epam.com";
    private static final String MENTOR_SKILL = "Java";

    @Captor
    private ArgumentCaptor<Mentor> mentorCaptor;
    @Mock
    private MentorRepository mentorRepository;

    @InjectMocks
    private MentorService mentorService;

    @Test
    public void findMentor() throws Exception {
        when(mentorRepository.findOne(anyInt())).thenReturn(prepareMentorShortForm());
        MentorDto actual = mentorService.find(MENTOR_ID);
        verify(mentorRepository).findOne(MENTOR_ID);
        assertThat(actual.getId(), is(MENTOR_ID));
    }

    @Test
    public void findAllMenteesForMentor() throws Exception {
        when(mentorRepository.findOne(anyInt())).thenReturn(prepareMentorShortFormWithMentees());
        List<MenteeDto> actual = mentorService.findMentees(MENTOR_ID);
        verify(mentorRepository).findOne(MENTOR_ID);
        assertThat(actual.size(), is(prepareMentorShortFormWithMentees().getMentees().size()));
    }

    @Test
    public void findAllMentorsBySkill() throws Exception {
        List<Mentor> mentors = Lists.newArrayList(prepareMentorShortForm(), prepareMentorShortForm());
        when(mentorRepository.findBySkillAndOrderByLevel(MENTOR_SKILL)).thenReturn(mentors);
        List<MentorDto> actual = mentorService.findBySkill(MENTOR_SKILL);
        assertThat(actual.size(), is(mentors.size()));
    }

    @Test
    public void createMentor() throws Exception {
        when(mentorRepository.findOne(anyInt())).thenReturn(prepareMentorShortForm());
        mentorService.create(prepareMentorDtoShortForm());
        verify(mentorRepository).save(mentorCaptor.capture());
        assertThat(mentorCaptor.getValue().getEmail(), is(MENTOR_EMAIL));

    }

    @Test
    public void updateMentor() throws Exception {
        Mentor mentor = new Mentor();
        when(mentorRepository.findOne(anyInt())).thenReturn(mentor);
        mentorService.update(prepareMentorDtoShortForm());
        assertThat(mentor, is(equalTo(prepareMentorShortForm())));
    }


    @Test
    public void deleteMentee() throws Exception {
        mentorService.delete(MENTOR_ID);
        verify(mentorRepository).delete(MENTOR_ID);
    }

    private Mentor prepareMentorShortForm() {
        Mentor mentor = new Mentor();
        mentor.setId(MENTOR_ID);
        mentor.setEmail(MENTOR_EMAIL);
        return mentor;
    }

    private Mentor prepareMentorShortFormWithMentees() {
        Mentor mentor = new Mentor();
        mentor.setId(MENTOR_ID);
        mentor.setEmail(MENTOR_EMAIL);
        mentor.setMentees(Lists.newArrayList(new Mentee(), new Mentee()));
        return mentor;
    }

    private MentorDto prepareMentorDtoShortForm() {
        MentorDto mentorDto = new MentorDto();
        BeanUtils.copyProperties(prepareMentorShortForm(), mentorDto);
        return mentorDto;
    }
}
