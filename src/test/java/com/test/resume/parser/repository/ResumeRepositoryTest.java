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

}