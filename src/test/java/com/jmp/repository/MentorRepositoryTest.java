package com.jmp.repository;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import com.jmp.entity.Mentor;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        DbUnitTestExecutionListener.class,
        TransactionDbUnitTestExecutionListener.class})
public class MentorRepositoryTest {

    private static final Integer MENTOR_ID_1 = 1;
    private static final Integer MENTOR_SIZE_LIST = 3;
    private static final String MENTOR_SKILL_JAVA = "Java";
    private static final String MENTOR_SKILL_JAVA_SCRIPT = "Java Script";
    private static final String MENTOR_FIRST_NAME_1 = "MentorFirst1";

    @Autowired
    private MentorRepository mentorRepository;

    @Test
    @DatabaseSetup("/mentor/mentors.xml")
    public void findMentor() throws Exception {
        Mentor mentor = mentorRepository.findOne(MENTOR_ID_1);
        assertThat(mentor, is(notNullValue()));
        assertThat(mentor.getFirstName(), is(equalTo(MENTOR_FIRST_NAME_1)));
    }

    @Test
    @Transactional
    @DatabaseSetup("/mentor/mentors.xml")
    public void findMentorsBySkill() throws Exception {
        List<Mentor> mentors = mentorRepository.findBySkillAndOrderByLevel(MENTOR_SKILL_JAVA);
        assertThat(mentors.isEmpty(), is(false));
        assertThat(mentors.size(), is(MENTOR_SIZE_LIST));
    }

    @Test
    @DatabaseSetup("/mentor/mentors.xml")
    @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT, value = "/mentor/mentorsSaveAfter.xml")
    public void saveMentor() throws Exception {
        Mentor mentor = new Mentor();
        mentor.setFirstName("MentorFirst4");
        mentor.setLastName("MentorLast4");
        mentor.setEmail("MentorFirst4_MentorLast4@epam.com");
        mentor.setLevel("D5");
        mentor.setMainSkill("Java");
        mentorRepository.save(mentor);
    }

    @Test
    @DatabaseSetup("/mentor/mentorsUpdateBefore.xml")
    @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT, value = "/mentor/mentorsUpdateAfter.xml")
    public void updateMentor() throws Exception {
        Mentor mentor = mentorRepository.findOne(MENTOR_ID_1);
        mentor.setMainSkill(MENTOR_SKILL_JAVA_SCRIPT);
        mentorRepository.save(mentor);
    }

    @Test
    @DatabaseSetup("/mentor/mentors.xml")
    @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT, value = "/mentor/mentorsDeleteAfter.xml")
    public void deleteMentor() throws Exception {
        mentorRepository.delete(MENTOR_ID_1);
    }
}
