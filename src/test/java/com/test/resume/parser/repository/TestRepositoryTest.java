package com.test.resume.parser.repository;

import com.test.resume.parser.model.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringJUnit4ClassRunner.class)
@DataJpaTest
class TestRepositoryTest {

    @Autowired
    private TestRepository testRepository;

    @org.junit.jupiter.api.Test
    public void  testDatabase() {
        Test t = new Test("123", "456");
        testRepository.saveAndFlush(t);

        List<Test> list = testRepository.findAll();

        assertNotNull(list);

        assertEquals(1, list.size());
    }

    @org.junit.jupiter.api.Test
    public void testResume() {

    }


}