package com.rak.feecollection.repository;

import com.rak.feecollection.entity.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReceiptRepository extends JpaRepository<Receipt, Long> {

    List<Receipt> findByStudentId(Long studentId);

    List<Receipt> findAllBySchoolDetail_SchoolNameAndSchoolDetail_Grade(final String schoolName, final String grade);
}