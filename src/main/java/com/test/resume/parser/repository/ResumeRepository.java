package com.test.resume.parser.repository;

import com.test.resume.parser.entity.Resume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ResumeRepository extends JpaRepository<Resume, Long> {

}
