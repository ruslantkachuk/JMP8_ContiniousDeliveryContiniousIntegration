package com.jmp.service;

import java.util.Objects;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.jmp.dto.MenteeDto;
import com.jmp.entity.Mentee;
import com.jmp.repository.MenteeRepository;
import com.jmp.repository.MentorRepository;

@Service
public class MenteeService {

    @Autowired
    private MenteeRepository menteeRepository;
    @Autowired
    private MentorRepository mentorRepository;

    public MenteeDto find(int id) {
        Mentee mentee = menteeRepository.findOne(id);
        MenteeDto menteeDto = new MenteeDto();
        BeanUtils.copyProperties(mentee, menteeDto);
        Optional.ofNullable(mentee.getMentor()).ifPresent(mentor -> menteeDto.setIdMentor(mentor.getId()));
        return menteeDto;
    }

    public void create(MenteeDto menteeDto) {
        Mentee mentee = new Mentee();
        BeanUtils.copyProperties(menteeDto, mentee);
        menteeRepository.save(mentee);
    }

    @Transactional
    public void update(@RequestBody MenteeDto menteeDto) {
        Mentee mentee = menteeRepository.findOne(menteeDto.getId());
        if (Objects.nonNull(menteeDto.getIdMentor())) {
            mentee.setMentor(mentorRepository.findOne(menteeDto.getIdMentor()));
        }
        BeanUtils.copyProperties(menteeDto, mentee);
    }

    public void delete(int id) {
        menteeRepository.delete(id);
    }
}
