package com.test.resume.parser.repository;

import com.test.resume.parser.entity.Resume;
import com.test.resume.parser.entity.ResumeEvent;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringJUnit4ClassRunner.class)
@DataJpaTest
class ResumeRepositoryTest {

    @Autowired
    private ResumeRepository resumeRepository;

    @Autowired
    private ResumeEventRepository resumeEventRepository;

    @Test
    public void testResumeEntity() {
        Resume resume = new Resume();
        resume.setResumeName("sdfsf");
        resume.setResumeFile(new byte[]{1,2,3,4});

        resumeRepository.saveAndFlush(resume);

        List<Resume> all = resumeRepository.findAll();
        assertEquals(1, all.size());
    }

    @Test
    public void testCreateNewResumeEvent() {

        // init resume event object
        ResumeEvent event = new ResumeEvent();
        event.setEventName("new event");
        event.setEventCreateDate(new Timestamp(System.currentTimeMillis()));

        // init resume object
        Resume resume = new Resume();
        resume.setResumeName("sdfsf");
        resume.setResumeFile(new byte[]{1,2,3,4});
        resume.setFavorite(true);

        List<Resume> l = new ArrayList<>();
        l.add(resume);
        resumeEventRepository.saveAndFlush(event);

        List<ResumeEvent> allEvents = resumeEventRepository.findAll();

        assertEquals(1, allEvents.size());

    }

}