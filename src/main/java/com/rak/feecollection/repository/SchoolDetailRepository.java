package com.rak.feecollection.repository;

import com.rak.feecollection.entity.School;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchoolDetailRepository extends JpaRepository<School, Long> {
    School findBySchoolNameAndGrade(final String schoolName, final String grade);

    School findBySchoolName(String schoolName);
}
