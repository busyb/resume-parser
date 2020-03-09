package com.test.resume.parser.repository;

import com.test.resume.parser.entity.Resume;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringJUnit4ClassRunner.class)
@DataJpaTest
class ResumeRepositoryTest {

    @Autowired
    private ResumeRepository resumeRepository;

    @Test
    public void testResumeEntity() {
        Resume resume = new Resume();
        resume.setResumeName("sdfsf");
        resume.setResumeFile(new byte[]{1,2,3,4});

        resumeRepository.saveAndFlush(resume);

        List<Resume> all = resumeRepository.findAll();
        assertEquals(1, all.size());
    }

<<<<<<< Updated upstream
=======
    @Test
    public void testCreateNewResumeEvent() {

        // init resume event object
        ResumeEvent event = new ResumeEvent();
        event.setEventName("new event");
        event.setEventCreateDate(new Date());

        // init resume object
        Resume resume = new Resume();
        resume.setResumeName("sdfsf");
        resume.setResumeFile(new byte[]{1,2,3,4});
        resume.setFavorite(true);

        List<Resume> l = new ArrayList<>();
        l.add(resume);
        event.setResumeList(l);
        resumeEventRepository.saveAndFlush(event);

        List<ResumeEvent> allEvents = resumeEventRepository.findAll();

        assertEquals(1, allEvents.size());
        assertTrue(allEvents.get(0).getResumeList().get(0).isFavorite());

    }

>>>>>>> Stashed changes
}