package com.test.resume.parser.repository;

import com.test.resume.parser.entity.Resume;
import com.test.resume.parser.entity.ResumeEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ResumeEventRepository extends JpaRepository<ResumeEvent, Long> {

}
