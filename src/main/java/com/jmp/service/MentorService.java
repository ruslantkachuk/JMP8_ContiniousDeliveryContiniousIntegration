package com.jmp.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.jmp.dto.MenteeDto;
import com.jmp.dto.MentorDto;
import com.jmp.entity.Mentor;
import com.jmp.repository.MentorRepository;

@Service
public class MentorService {

    @Autowired
    private MentorRepository mentorRepository;

    public MentorDto find(int id) {
        Mentor mentor = mentorRepository.findOne(id);
        MentorDto mentorDto = new MentorDto();
        BeanUtils.copyProperties(mentor, mentorDto);
        return mentorDto;
    }

    public List<MentorDto> findBySkill(String skill) {
        List<Mentor> mentors;
        if (StringUtils.isEmpty(skill)){
            mentors = mentorRepository.findAll();
        } else {
            mentors = mentorRepository.findBySkillAndOrderByLevel(skill);
        }
        return mentors.stream()
                .map(mentor -> {
                    MentorDto dto = new MentorDto();
                    BeanUtils.copyProperties(mentor, dto);
                    return dto;
                }).collect(Collectors.toList());
    }

    public List<MenteeDto> findMentees(int id) {
        Mentor mentor = mentorRepository.findOne(id);
        return mentor.getMentees()
                .stream()
                .map(mentee -> {
                    MenteeDto menteeDto = new MenteeDto();
                    BeanUtils.copyProperties(mentee, menteeDto);
                    return menteeDto;
                })
                .collect(Collectors.toList());
    }

    public void create(MentorDto mentorDto) {
        Mentor mentor = new Mentor();
        BeanUtils.copyProperties(mentorDto, mentor);
        mentorRepository.save(mentor);
    }

    @Transactional
    public void update(MentorDto mentorDto) {
        Mentor mentor = mentorRepository.findOne(mentorDto.getId());
        BeanUtils.copyProperties(mentorDto, mentor);
    }

    public void delete(int id) {
        mentorRepository.delete(id);
    }
}
