CREATE TABLE students (
    cpf VARCHAR(255) UNIQUE NOT NULL,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL
);

CREATE TABLE phones (
    code VARCHAR(255),
    number VARCHAR(255),
    student_cpf VARCHAR(255),
    PRIMARY KEY (code, number),
    FOREIGN KEY(student_cpf) REFERENCES students(cpf)
);

CREATE TABLE indications (
    indicator_cpf VARCHAR(255) NOT NULL,
    indicated_cpf VARCHAR(255) NOT NULL,
    indication_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (indicator_cpf, indicated_cpf),
    FOREIGN KEY (indicator_cpf) REFERENCES students(cpf),
    FOREIGN KEY (indicated_cpf) REFERENCES students(cpf)
);

ALTER TABLE students ADD COLUMN password VARCHAR(255) NOT NULL;