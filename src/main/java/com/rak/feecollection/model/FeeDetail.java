package com.rak.feecollection.model;

import java.util.HashMap;
import java.util.Map;

public record FeeDetail(String schoolName, String grade) {

    private static final Map<String, Map<String, Double>> feeDetailsBySchoolAndGrade = new HashMap<>();

    static {
        Map<String, Double> gradeAndFeeForABC = new HashMap<>();
        gradeAndFeeForABC.put("1", 1000.0);
        gradeAndFeeForABC.put("2", 2000.0);
        gradeAndFeeForABC.put("3", 3000.0);
        gradeAndFeeForABC.put("4", 4000.0);
        gradeAndFeeForABC.put("5", 5000.0);
        gradeAndFeeForABC.put("6", 6000.0);
        gradeAndFeeForABC.put("7", 7000.0);
        gradeAndFeeForABC.put("8", 8000.0);
        gradeAndFeeForABC.put("9", 9000.0);
        gradeAndFeeForABC.put("10", 10000.0);
        gradeAndFeeForABC.put("11", 11000.0);
        gradeAndFeeForABC.put("12", 12000.0);

        feeDetailsBySchoolAndGrade.put("ABC School", gradeAndFeeForABC);
    }

    public static double getFee(String schoolName, String grade) {
        Map<String, Double> gradeAndFee = feeDetailsBySchoolAndGrade.get(schoolName);
        if (gradeAndFee != null) {
            return gradeAndFee.getOrDefault(grade, 0.0);
        }
        return 0.0;
    }
}
