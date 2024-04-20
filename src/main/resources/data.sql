CREATE TABLE IF NOT EXISTS school_detail (
    school_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    school_name VARCHAR(255) NOT NULL,
    grade VARCHAR(255) NOT NULL
);
INSERT INTO school_detail (school_name, grade) VALUES ('ABC School', '10');
INSERT INTO school_detail (school_name, grade) VALUES ('XYZ School', '12');