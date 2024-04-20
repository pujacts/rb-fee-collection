package com.rak.feecollection.repository;

import com.rak.feecollection.entity.SchoolDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchoolDetailRepository extends JpaRepository<SchoolDetail, Long> {
    SchoolDetail findBySchoolNameAndGrade(final String schoolName, final String grade);

    SchoolDetail findBySchoolName(String schoolName);
}
