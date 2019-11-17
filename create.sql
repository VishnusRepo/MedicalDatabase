create sequence patient_seq start with 5;

create sequence session_seq start with 1;

CREATE TABLE MedicalFacility (
    facility_id INTEGER,
    classification VARCHAR(32),
    capacity INTEGER,
    name VARCHAR(32),
    PRIMARY KEY(facility_id)
);

CREATE TABLE Certification (
    cert_acronym VARCHAR(32),
    name VARCHAR(50),
    datecertified DATE,
    expirationdate DATE,
    PRIMARY KEY(cert_acronym)
);

CREATE TABLE HAS_CERTIFICATIONS (
facility_id INTEGER,
cert_acronym VARCHAR(32),
primary key(facility_id,cert_acronym),
foreign key(facility_id) REFERENCES MedicalFacility(facility_id),
foreign key(cert_acronym) REFERENCES certification(cert_acronym)
ON DELETE CASCADE
);

CREATE TABLE has_location (
house_no INTEGER,
facility_id INTEGER,
street varchar2(20),
city varchar2(20),
state varchar2(20),
country varchar2(20),
PRIMARY KEY(house_no,facility_id),
FOREIGN KEY(faCility_id) REFERENCES MEDICALFACILITY 
ON DELETE CASCADE);

CREATE TABLE SERVICE_DEPARTMENT (
    NAME VARCHAR(32),
    DEPT_CODE VARCHAR(5),
    DIRECTOR_NAME VARCHAR(32),
    BODY_PART VARCHAR2(32),
    PRIMARY KEY (DEPT_CODE)
    );

CREATE TABLE HAS_DEPT (
facility_id INTEGER,
dept_code VARCHAR(32),
primary key(facility_id,dept_code),
foreign key(facility_id) REFERENCES MedicalFacility(facility_id),
foreign key(dept_code) REFERENCES SERVICE_DEPARTMENT(dept_code)
ON DELETE CASCADE
);

CREATE TABLE Service (
      service_code VARCHAR2(32) PRIMARY KEY,
      service_name VARCHAR2(32) NOT NULL);

CREATE TABLE has_services (
      service_code VARCHAR2(32),
      DEPT_CODE VARCHAR(5),
      primary key(service_code,DEPT_CODE),
      foreign key(service_code) REFERENCES Service(service_code) on delete cascade,
      foreign key(DEPT_CODE) REFERENCES SERVICE_DEPARTMENT(DEPT_CODE) on delete cascade
);

CREATE TABLE Equipment (
      equipment_name VARCHAR2(32) PRIMARY KEY     
);

CREATE TABLE has_equipment (
      service_code VARCHAR2(32),
      equipment_name VARCHAR2(32),
      primary key(service_code,equipment_name),
      foreign key(service_code) REFERENCES Service(service_code) on delete cascade,
      foreign key(equipment_name) REFERENCES Equipment(equipment_name) on delete cascade
);

CREATE TABLE BodyPart (
     bodypart_name VARCHAR2(32) PRIMARY KEY,
bodypart_code varchar(6)
);

CREATE TABLE has_speciality (
      DEPT_CODE VARCHAR(5),
      bodypart_name VARCHAR2(32) NOT NULL,
      primary key(DEPT_CODE,bodypart_name),
      foreign key(DEPT_CODE) REFERENCES SERVICE_DEPARTMENT(DEPT_CODE) on delete cascade,
      foreign key(bodypart_name) REFERENCES BodyPart(bodypart_name) on delete cascade
);

CREATE TABLE Medical_staff (
      first_name VARCHAR2(32),
      employee_id VARCHAR2(32) PRIMARY KEY,
      hire_date DATE,
      primary_service_dept_code VARCHAR2(5),
      SECONDARY_SERVICE_DEPT_CODE VARCHAR2(5),
      last_name varchar2(32),
      DOB date,
      city varchar2(32),
      FOREIGN KEY(primary_service_dept_code) references SERVICE_DEPARTMENT(DEPT_CODE),
      FOREIGN KEY(SECONDARY_SERVICE_DEPT_CODE) references SERVICE_DEPARTMENT(DEPT_CODE)
);

CREATE TABLE Non_medical_staff (
      first_name VARCHAR2(32),
      employee_id VARCHAR2(32) PRIMARY KEY,
      hire_date DATE,
      primary_service_dept_code VARCHAR2(5),
      SECONDARY_SERVICE_DEPT_CODE VARCHAR2(5),
      last_name varchar2(32),
      DOB date,
      city varchar2(32),
      FOREIGN KEY(primary_service_dept_code) references SERVICE_DEPARTMENT(DEPT_CODE),
      FOREIGN KEY(SECONDARY_SERVICE_DEPT_CODE) references SERVICE_DEPARTMENT(DEPT_CODE)
);

CREATE TABLE has_medical_medical (
    DEPT_CODE VARCHAR(5),
    employee_id VARCHAR2(32),
    primary key(DEPT_CODE,employee_id),
    foreign key(DEPT_CODE) REFERENCES SERVICE_DEPARTMENT(DEPT_CODE) on delete cascade,
    foreign key(employee_id) REFERENCES Medical_staff(employee_id) on delete cascade
);

CREATE TABLE has_medical_nonmedical (
    DEPT_CODE VARCHAR(5),
    employee_id VARCHAR2(32),
    primary key(DEPT_CODE,employee_id),
    foreign key(DEPT_CODE) REFERENCES SERVICE_DEPARTMENT(DEPT_CODE) on delete cascade,
    foreign key(employee_id) REFERENCES non_medical_staff(employee_id) on delete cascade
);

create table PATIENT
(
patient_id number PRIMARY KEY,
first_name varchar2(15),
last_name varchar2(15),
dob date,
phone_number varchar(10),
apt_number number,
street varchar2(20),
city varchar2(20),
state varchar2(20),
country varchar2(20)
);

CREATE TABLE Assesment_outcome (
     ass_code VARCHAR2(32) PRIMARY KEY
);



CREATE TABLE PATIENT_SESSION (
    appointment_id INTEGER,
    patient_id number,
    systolic NUMBER(3),
    diastolic NUMBER(3),
    check_in_time timestamp,
    temperature INTEGER,
    ass_code varchar2(32),
    PRIMARY KEY(APPOINTMENT_ID),
   check_out_time timestamp,
   PATIENT_ACK VARCHAR2(100),
   DISCHARGE_DATE timestamp,
    out_time timestamp,
    TREATMENT_STARTTIME TIMESTAMP,
    facility_id INTEGER,
    foreign key(patient_id) references patient(patient_id) on delete cascade,
    foreign key(ass_code) references assesment_outcome(ass_code) on delete cascade,
    foreign key(facility_id) REFERENCES MedicalFacility(facility_id)
    );

CREATE TABLE register (
      patient_id number,
      facility_id INTEGER,
      primary key(patient_id,facility_id),
      foreign key(patient_id) REFERENCES patient(patient_id) on delete cascade,
      foreign key(facility_id) REFERENCES medicalfacility(facility_id) on delete cascade
);

create table symptom
(
symptom_name varchar2(30) primary key,
sym_code varchar2(5) UNIQUE

);

create table symptom_scale
(
symptom_name varchar2(30),
value varchar2(10),
primary key(symptom_name, value),
foreign key(symptom_name) references symptom(symptom_name)
);

create table has_symptom
(
appointment_id integer,
symptom_name varchar2(30),
bodypart_name varchar2(32), 
duration number,
is_chronic varchar2(1),
incident varchar2(500),
value varchar2(30),
primary key(appointment_id, symptom_name, bodypart_name),
foreign key(appointment_id) references patient_session(appointment_id) on delete cascade,
foreign key(symptom_name, value) references symptom_scale(symptom_name, value) on delete cascade,
foreign key(bodypart_name) references bodypart(bodypart_name) on delete cascade
);

create table affected
(
symptom_name varchar2(30) primary key,
bodypart_name varchar2(30),
foreign key(bodypart_name) references bodypart(bodypart_name) on delete cascade
);

create table rules
(
rule_id number,
symptom_name varchar2(30),
bodypart_name varchar2(30),
value varchar2(30),
equality_type varchar2(2),
ass_code varchar2(30),
primary key(rule_id, symptom_name),
foreign key(bodypart_name) references bodypart(bodypart_name) on delete cascade,
foreign key(symptom_name, value) references symptom_scale(symptom_name, value) on delete cascade,
foreign key(ass_code) references assesment_outcome(ass_code) on delete cascade
);

CREATE TABLE Negative_Exp_Out (
     neg_code number PRIMARY KEY,
     negative_description VARCHAR(100) 
);

CREATE TABLE DISCHARGE_STATUS (
      PATIENT_ID INTEGER,
      APPOINTMENT_ID INTEGER,
      OUTCOME_STATUS VARCHAR2(32),
      NEG_CODE INTEGER,
      FACILITY_ID INTEGER,
      REFERRER_ID VARCHAR2(32),
      SERVICE_CODE VARCHAR2(32),
      NEG_ADD_INFO VARCHAR2(100),
      PRIMARY KEY(PATIENT_ID,APPOINTMENT_ID),
      TREAT_INFO VARCHAR2(100),
      foreign key(PATIENT_ID) REFERENCES PATIENT(PATIENT_ID) on delete cascade,
      foreign key(APPOINTMENT_ID) REFERENCES PATIENT_SESSION(APPOINTMENT_ID) on delete cascade,
      foreign key(NEG_CODE) REFERENCES NEGATIVE_EXP_OUT(NEG_CODE) on delete cascade,
      foreign key(FACILITY_ID) REFERENCES MEDICALFACILITY(FACILITY_ID) on delete cascade,
      foreign key(REFERRER_ID) REFERENCES MEDICAL_STAFF(EMPLOYEE_ID) on delete cascade,
      foreign key(SERVICE_CODE) REFERENCES Service(SERVICE_CODE) on delete cascade  
);

CREATE TABLE REFER_REASON (
      REASON_CODE INTEGER,
      REFER_INFO VARCHAR2(100),
      PRIMARY KEY(REASON_CODE)
);

CREATE TABLE REFERRAL_STATUS (
      PATIENT_ID INTEGER,
      APPOINTMENT_ID INTEGER,
      REASON_CODE INTEGER,
      SERVICE_CODE VARCHAR2(32),
      REFER_ADD_INFO VARCHAR2(100),
      PRIMARY KEY(PATIENT_ID,APPOINTMENT_ID,REASON_CODE,SERVICE_CODE),
      foreign key(PATIENT_ID) REFERENCES PATIENT(PATIENT_ID) on delete cascade,
      foreign key(APPOINTMENT_ID) REFERENCES PATIENT_SESSION(APPOINTMENT_ID) on delete cascade,
      foreign key(REASON_CODE) REFERENCES REFER_REASON(REASON_CODE) on delete cascade,
      foreign key(SERVICE_CODE) REFERENCES Service(SERVICE_CODE) on delete cascade  
);

CREATE TABLE has_nonmedical_nonmedical (
    DEPT_CODE VARCHAR(5),
    employee_id VARCHAR2(32),
    primary key(DEPT_CODE,employee_id),
    foreign key(DEPT_CODE) REFERENCES SERVICE_DEPARTMENT(DEPT_CODE) on delete cascade,
    foreign key(employee_id) REFERENCES non_medical_staff(employee_id) on delete cascade
);

create or replace view facility_referred_facility as
select dis.facility_id facility_id, fac_dep.facility_id referred_facility_id, count(*) count
from
discharge_status dis,
referral_status ref,
has_dept fac_dep,
has_services dep_ser
where
fac_dep.dept_code = dep_ser.dept_code
and ref.service_code = dep_ser.service_code
and dis.appointment_id = ref.appointment_id
group by dis.facility_id, fac_dep.facility_id
;

create or replace function demo_query_3
return varchar2
is
returnVal VARCHAR2(200):='';
BEGIN
FOR item IN
(select facility_id from medicalfacility)
LOOP
select CONCAT(CONCAT(returnVal || item.facility_id || ' - ' ,NVL(referred_facility_id,'-')), '\n') into returnVal from facility_referred_facility where facility_id = item.facility_id;
END LOOP;
RETURN returnVal;
END demo_query_3;
/