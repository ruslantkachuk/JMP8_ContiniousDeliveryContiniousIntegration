package com.jmp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenteeDto {
    private Integer id;
    private Integer idMentor;
    private String firstName;
    private String lastName;
    private String email;
    private String level;
    private String mainSkill;
}
