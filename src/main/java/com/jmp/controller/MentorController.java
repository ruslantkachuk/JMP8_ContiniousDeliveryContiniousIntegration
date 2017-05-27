package com.jmp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.jmp.dto.MenteeDto;
import com.jmp.dto.MentorDto;
import com.jmp.service.MentorService;

@RestController
@RequestMapping("/mentors")
public class MentorController {
    @Autowired
    private MentorService mentorService;

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public MentorDto find(@PathVariable("id") int id) {
        return mentorService.find(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(path = "/{id}/mentees", method = RequestMethod.GET)
    public List<MenteeDto> findMentees(@PathVariable("id") int id) {
        return mentorService.findMentees(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET)
    public List<MentorDto> findBySkill(@RequestParam(name = "skill", required = false) String skill) {
        return mentorService.findBySkill(skill);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST)
    public void create(@RequestBody MentorDto mentorDto) {
        mentorService.create(mentorDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.PUT)
    public void update(@RequestBody MentorDto mentorDto) {
        mentorService.update(mentorDto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") int id) {
        mentorService.delete(id);
    }
}
