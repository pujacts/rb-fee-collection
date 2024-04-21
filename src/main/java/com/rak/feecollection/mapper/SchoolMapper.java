package com.rak.feecollection.mapper;

import com.rak.feecollection.entity.School;
import com.rak.feecollection.model.SchoolDto;
import org.springframework.util.Assert;

public class SchoolMapper {

    public static SchoolDto toDto(School school) {
        Assert.notNull(school, "school is null");
        SchoolDto schoolDto = new SchoolDto();
        schoolDto.setSchoolName(school.getSchoolName());
        schoolDto.setGrade(school.getGrade());
        schoolDto.setSchoolId(school.getSchoolId());
        return schoolDto;
    }

    public static School toEntity(SchoolDto schoolDto) {
        Assert.notNull(schoolDto, "school is null");
        School school = new School();
        school.setSchoolName(schoolDto.getSchoolName());
        school.setGrade(schoolDto.getGrade());
        school.setSchoolId(schoolDto.getSchoolId());
        return school;
    }

}
