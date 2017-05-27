package com.jmp.repository;

import org.springframework.data.repository.CrudRepository;

import com.jmp.entity.Mentee;

public interface MenteeRepository extends CrudRepository<Mentee, Integer> {
}
