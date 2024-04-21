package com.rak.feecollection.model;


import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "School dto")
public class SchoolDto {

    @Schema(description = "School Id")
    private Long schoolId;
    @Schema(description = "School Name")
    private String schoolName;
    @Schema(description = "School Grade")
    private String grade;

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public Long getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Long schoolId) {
        this.schoolId = schoolId;
    }
}
