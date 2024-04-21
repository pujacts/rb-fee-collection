package com.rak.feecollection.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Student Dto")
public class StudentDto {
    @Schema(description = "Student Id")
    private long studentId;
    @Schema(description = "Student Name")
    private String studentName;
    @Schema(description = "Mobile Number")
    private String mobileNumber;
    @Schema(description = "School Dto")
    private SchoolDto schoolDto;

    public long getStudentId() {
        return studentId;
    }

    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public SchoolDto getSchoolDto() {
        return schoolDto;
    }

    public void setSchoolDto(SchoolDto schoolDto) {
        this.schoolDto = schoolDto;
    }
}
