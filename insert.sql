INSERT INTO medicalfacility
VALUES(1000, 03, 300, 'Wolf Hospital');
INSERT INTO medicalfacility
VALUES(1001, 02, 150, 'California Health Care');
INSERT INTO medicalfacility
VALUES(1002, 01, 10, 'Suny Medical Center');

INSERT INTO certification
VALUES ('CER01', 'certified1',to_date('09/01/2019','mm/dd/yy'), to_date('09/01/2029','mm/dd/yy'));
INSERT INTO certification
VALUES ('CER001', 'Comprehensive Stroke certification',to_date('11/12/1990','mm/dd/yy'), to_date('11/11/2025','mm/dd/yy'));
INSERT INTO certification
VALUES ('CER002', 'ISO Certification',to_date('05/09/2011','mm/dd/yy'), to_date('02/08/2024','mm/dd/yy'));
INSERT INTO certification
VALUES ('CER003', 'Primary Stroke certification',to_date('01/01/2018','mm/dd/yy'), to_date('12/31/2028','mm/dd/yy'));

INSERT INTO HAS_CERTIFICATIONS 
VALUES (1000,'CER001');
INSERT INTO HAS_CERTIFICATIONS 
VALUES (1001,'CER002');
INSERT INTO HAS_CERTIFICATIONS 
VALUES (1002,'CER002');
INSERT INTO HAS_CERTIFICATIONS 
VALUES (1002,'CER003');

INSERT INTO HAS_LOCATION 
VALUES 
(2650,1000,'Wolf Village','RALEIGH','NC','US');
INSERT INTO HAS_LOCATION 
VALUES 
(2500,1001,'Sacramento','Santa Cruz','CA','US');
INSERT INTO HAS_LOCATION 
VALUES 
(489,1002,' First Avenue','New York','NY','US');

INSERT INTO SERVICE_DEPARTMENT 
VALUES ('Emergency room', 'ER000', 'Musical Robert', null);

INSERT INTO SERVICE_DEPARTMENT 
VALUES ('General practice department', 'GP000', 'Muscular Rob', null);

INSERT INTO SERVICE_DEPARTMENT 
VALUES ('General practice department', 'GP001', 'Millennium Roberten', null);

INSERT INTO SERVICE_DEPARTMENT 
VALUES ('Optometry', 'OP000', 'Medical Robot', 'Eye');

INSERT INTO SERVICE_DEPARTMENT 
VALUES ('Security', 'SE000', 'Miscellaneous Robotor', null);

INSERT INTO SERVICE_DEPARTMENT 
VALUES ('Emergency room', 'ER001', 'Massaging Robin', null);

INSERT INTO HAS_DEPT 
VALUES (1000, 'GP000');

INSERT INTO HAS_DEPT 
VALUES (1000, 'OP000');

INSERT INTO HAS_DEPT 
VALUES (1000, 'SE000');

INSERT INTO HAS_DEPT 
VALUES (1001, 'ER000');

INSERT INTO HAS_DEPT 
VALUES (1001, 'GP001');

INSERT INTO HAS_DEPT 
VALUES (1002, 'ER001');

INSERT INTO SERVICE
VALUES ('SER01', 'Emergency');

INSERT INTO SERVICE
VALUES ('SGP01', 'General practice');

INSERT INTO SERVICE
VALUES ('VIS01', 'Vision');

INSERT INTO has_services
VALUES ('SER01', 'ER000');

INSERT INTO has_services
VALUES ('SGP01', 'GP000');

INSERT INTO has_services
VALUES ('SGP01', 'GP001');

INSERT INTO has_services
VALUES ('VIS01', 'OP000');

INSERT INTO EQUIPMENT
VALUES ('ER combo rack');

INSERT INTO EQUIPMENT
VALUES ('Blood pressure monitor');

INSERT INTO EQUIPMENT
VALUES ('thermometer');

INSERT INTO EQUIPMENT
VALUES ('Vision Screener');

INSERT INTO HAS_EQUIPMENT
VALUES ('SER01', 'ER combo rack');

INSERT INTO HAS_EQUIPMENT
VALUES ('SGP01', 'Blood pressure monitor');

INSERT INTO HAS_EQUIPMENT
VALUES ('SGP01', 'thermometer');

INSERT INTO HAS_EQUIPMENT
VALUES ('VIS01', 'Vision Screener');

INSERT into bodypart values('Heart', 'HRT000');
INSERT into bodypart values('Eye','EYE000');
INSERT into bodypart values('Left Arm', 'ARM000');
INSERT into bodypart values('Right Arm', 'ARM001');
INSERT into bodypart values('Abdominal', 'ABD000');
INSERT into bodypart values('Chest', 'CST000');
INSERT into bodypart values('Head', 'HED000');
INSERT into bodypart values('Other', '000000');

INSERT INTO HAS_SPECIALITY
VALUES ('OP000', 'Eye');

Insert into medical_staff 
values('Medical', '89001', to_date('06/21/2019','mm/dd/yy'), 'OP000',null, 'Robot',  to_date('04/19/1989','mm/dd/yy'), 'Raleigh');
Insert into medical_staff 
values('Musical', '93001', to_date('08/29/2018','mm/dd/yy'), 'ER000',null, 'Robert',  to_date('01/29/1993','mm/dd/yy'), 'Raleigh');
Insert into medical_staff 
values('Muscular', '67001', to_date('10/12/1983','mm/dd/yy'), 'GP000',null, 'Rob',  to_date('12/09/1967','mm/dd/yy'), 'Raleigh');
Insert into medical_staff 
values('Mechanical', '88001', to_date('06/21/2019','mm/dd/yy'), 'GP000','OP000', 'Roboto',  to_date('05/18/1988','mm/dd/yy'), 'Raleigh');
Insert into medical_staff 
values('Millennium', '91001', to_date('09/20/2018','mm/dd/yy'), 'GP001',null, 'Roberten',  to_date('06/28/1991','mm/dd/yy'), 'Raleigh');
Insert into medical_staff 
values('Missionary', '66001', to_date('10/01/1993','mm/dd/yy'), 'ER000',null, 'Robinson',  to_date('07/08/1966','mm/dd/yy'), 'Raleigh');
Insert into medical_staff 
values('Massaging', '67002', to_date('12/10/1990','mm/dd/yy'), 'ER001',null, 'Robin',  to_date('12/09/1967','mm/dd/yy'), 'Raleigh');

Insert into non_medical_staff 
values('Miscellaneous', '89002', to_date('08/19/2014','mm/dd/yy'), 'SE000',null, 'Robotor',  to_date('04/19/1989','mm/dd/yy'), 'Raleigh');
Insert into non_medical_staff 
values('Musician', '93002', to_date('10/18/2017','mm/dd/yy'), 'SE000',null, 'Root',  to_date('01/29/1993','mm/dd/yy'), 'Raleigh');

INSERT INTO has_medical_medical VALUES ('OP000', '89001');
INSERT INTO has_medical_medical VALUES ('ER000', '93001');
INSERT INTO has_medical_medical VALUES ('GP000', '67001');
INSERT INTO has_medical_medical VALUES ('GP000', '88001');
INSERT INTO has_medical_medical VALUES ('OP000', '88001');
INSERT INTO has_medical_medical VALUES ('GP001', '91001');
INSERT INTO has_medical_medical VALUES ('ER000', '66001');
INSERT INTO has_medical_medical VALUES ('ER001', '67002');

--INSERT INTO has_medical_nonmedical VALUES ('SE000', '89002');
--INSERT INTO has_medical_nonmedical VALUES ('SE000', '93002');

INSERT INTO PATIENT
VALUES (1, 'John', 'Smith', to_date('1/1/1990', 'mm/dd/yyyy'), '9007004567', 100, 'Avent Ferry Road', 'Raleigh', 'North Carolina', 'US');

INSERT INTO PATIENT
VALUES (2, 'Jane', 'Doe', to_date('2/29/2000', 'mm/dd/yyyy'), '9192453245', 1016, 'Lexington Road', 'New York', 'New York', 'US');

INSERT INTO PATIENT
VALUES (3, 'Rock', 'Star', to_date('8/31/1970', 'mm/dd/yyyy'), '5403127893', 1022, 'Amphitheatre Parkway', 'Mountain View', 'California', 'US');

INSERT INTO PATIENT
VALUES (4, 'Sheldon', 'Cooper', to_date('05/26/1984', 'mm/dd/yyyy'), '6184628437', 1210, 'Sacramento', 'Santa Cruz', 'California', 'US');

INSERT INTO ASSESMENT_OUTCOME
VALUES ('High');
INSERT INTO ASSESMENT_OUTCOME
VALUES ('Normal');
INSERT INTO ASSESMENT_OUTCOME
VALUES ('Quarantine');

--not inserting PATIENT_SESSION
--not inserting register

INSERT INTO SYMPTOM VALUES ('Pain', 'SYM01');
INSERT INTO SYMPTOM VALUES ('Diarrhea', 'SYM02');
INSERT INTO SYMPTOM VALUES ('Fever', 'SYM03');
INSERT INTO SYMPTOM VALUES ('Physical Exam', 'SYM04');
INSERT INTO SYMPTOM VALUES ('Lightheadedness', 'SYM05');
INSERT INTO SYMPTOM VALUES ('Blurred vision', 'SYM06');
INSERT INTO SYMPTOM VALUES ('Bleeding', 'SYM07');
INSERT INTO SYMPTOM VALUES ('Headache', 'SYM08');

INSERT INTO SYMPTOM_SCALE VALUES ('Pain', '1');
INSERT INTO SYMPTOM_SCALE VALUES ('Pain', '2');
INSERT INTO SYMPTOM_SCALE VALUES ('Pain', '3');
INSERT INTO SYMPTOM_SCALE VALUES ('Pain', '4');
INSERT INTO SYMPTOM_SCALE VALUES ('Pain', '5');
INSERT INTO SYMPTOM_SCALE VALUES ('Pain', '6');
INSERT INTO SYMPTOM_SCALE VALUES ('Pain', '7');
INSERT INTO SYMPTOM_SCALE VALUES ('Pain', '8');
INSERT INTO SYMPTOM_SCALE VALUES ('Pain', '9');
INSERT INTO SYMPTOM_SCALE VALUES ('Pain', '10');
INSERT INTO SYMPTOM_SCALE VALUES ('Diarrhea', 'Normal');
INSERT INTO SYMPTOM_SCALE VALUES ('Diarrhea', 'Severe');
INSERT INTO SYMPTOM_SCALE VALUES ('Fever', 'Low');
INSERT INTO SYMPTOM_SCALE VALUES ('Fever', 'High');
INSERT INTO SYMPTOM_SCALE VALUES ('Physical Exam', 'Normal');
INSERT INTO SYMPTOM_SCALE VALUES ('Physical Exam', 'Premium');
INSERT INTO SYMPTOM_SCALE VALUES ('Lightheadedness', 'Normal');
INSERT INTO SYMPTOM_SCALE VALUES ('Lightheadedness', 'Severe');
INSERT INTO SYMPTOM_SCALE VALUES ('Blurred vision', 'Normal');
INSERT INTO SYMPTOM_SCALE VALUES ('Blurred vision', 'Severe');
INSERT INTO SYMPTOM_SCALE VALUES ('Bleeding', 'Moderate');
INSERT INTO SYMPTOM_SCALE VALUES ('Bleeding', 'Heavy');
INSERT INTO SYMPTOM_SCALE VALUES ('Headache', '1');
INSERT INTO SYMPTOM_SCALE VALUES ('Headache', '2');
INSERT INTO SYMPTOM_SCALE VALUES ('Headache', '3');
INSERT INTO SYMPTOM_SCALE VALUES ('Headache', '4');
INSERT INTO SYMPTOM_SCALE VALUES ('Headache', '5');
INSERT INTO SYMPTOM_SCALE VALUES ('Headache', '6');
INSERT INTO SYMPTOM_SCALE VALUES ('Headache', '7');
INSERT INTO SYMPTOM_SCALE VALUES ('Headache', '8');
INSERT INTO SYMPTOM_SCALE VALUES ('Headache', '9');
INSERT INTO SYMPTOM_SCALE VALUES ('Headache', '10');

--not inserting has_symptom

INSERT INTO AFFECTED VALUES ('Diarrhea', 'Abdominal');
INSERT INTO AFFECTED VALUES ('Lightheadedness', 'Head');
INSERT INTO AFFECTED VALUES ('Blurred vision', 'Eye');
INSERT INTO AFFECTED VALUES ('Headache', 'Head');

INSERT INTO RULES
VALUES (1, 'Pain', 'Chest', '7', '>', 'High');
INSERT INTO RULES
VALUES (1, 'Fever', null, 'High', '=', 'High');
INSERT INTO RULES
VALUES (2, 'Headache', 'Head', '7', '>', 'High');
INSERT INTO RULES
VALUES (2, 'Blurred vision', 'Eye', null, '=', 'High');
INSERT INTO RULES
VALUES (2, 'Lightheadedness', 'Head', null, '=', 'High');
INSERT INTO RULES
VALUES (3, 'Headache', 'Head', '7', '<=', 'Normal');
INSERT INTO RULES
VALUES (3, 'Blurred vision', 'Eye', null, '>', 'Normal');

INSERT INTO negative_exp_out values(0, 'NA');
INSERT INTO negative_exp_out values(1, 'Misdiagnosis');
INSERT INTO negative_exp_out values(2, 'Patient acquired an infection during hospital stay');

INSERT INTO REFER_REASON values(1, 'service unavailable at time of visit');
INSERT INTO REFER_REASON values(2, 'service not present at facility');
INSERT INTO REFER_REASON values(3, 'non payment');

