package com.jmp.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

@Data
@Entity
public class Mentee {
    @Id()
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String level;
    private String mainSkill;

    @ManyToOne
    @JoinColumn(name = "idMentor", referencedColumnName = "id")
    private Mentor mentor;
}
