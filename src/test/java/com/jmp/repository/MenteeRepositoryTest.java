package com.jmp.repository;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import com.jmp.entity.Mentee;
import com.jmp.entity.Mentor;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
public class MenteeRepositoryTest {

    private static final Integer MENTEE_ID_1 = 1;
    private static final Integer MENTOR_ID_1 = 1;
    private static final String MENTEE_FIRST_NAME_1 = "MenteeFirst1";

    @Autowired
    private MenteeRepository menteeRepository;

    @Test
    @DatabaseSetup("/mentee/mentees.xml")
    public void findMentee() throws Exception {
        Mentee mentee = menteeRepository.findOne(MENTEE_ID_1);
        System.out.println(mentee);
        assertThat(mentee, is(notNullValue()));
        assertThat(mentee.getFirstName(), is(equalTo(MENTEE_FIRST_NAME_1)));
    }

    @Test
    @DatabaseSetup("/mentee/mentees.xml")
    @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT, value = "/mentee/menteesSaveAfter.xml")
    public void saveMentee() throws Exception {
        Mentee mentee = new Mentee();
        mentee.setFirstName("MenteeFirst4");
        mentee.setLastName("MenteeLast4");
        mentee.setEmail("MenteeFirst4_MenteeLast4@epam.com");
        mentee.setLevel("D3");
        mentee.setMainSkill("Java");
        menteeRepository.save(mentee);
    }

    @Test
    @DatabaseSetup("/mentee/menteesUpdateBefore.xml")
    @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT, value = "/mentee/menteesUpdateAfter.xml")
    public void updateMentee() throws Exception {
        Mentee mentee = menteeRepository.findOne(MENTEE_ID_1);
        Mentor mentor = new Mentor();
        mentor.setId(MENTOR_ID_1);
        mentee.setMentor(mentor);
        menteeRepository.save(mentee);
    }

    @Test
    @DatabaseSetup("/mentee/mentees.xml")
    @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT, value = "/mentee/menteesDeleteAfter.xml")
    public void deleteMentee() throws Exception {
        menteeRepository.delete(MENTEE_ID_1);
    }
}
