--------------CREATE TABLES--------------

/*DROP TABLE PAYMENTS;
DROP TABLE CART;
DROP TABLE CLASSIFIEDS;
DROP TABLE CATEGORY;
DROP TABLE PAYMENT_METHODS;
DROP TABLE USERS;
*/
CREATE TABLE USERS(
    ID NUMBER PRIMARY KEY,
    EMAIL VARCHAR2(50) UNIQUE,
    PASSWORD VARCHAR2(50) NOT NULL,
    IS_ADMIN VARCHAR2(1) DEFAULT 'N' CHECK ( IS_ADMIN IN ('N','Y')  ),
    FIRST_NAME VARCHAR2(50) NOT NULL ,
    LAST_NAME VARCHAR2(50) NOT NULL,
    ADDRESS VARCHAR2(250) NOT NULL,
    PHONE VARCHAR2(50),
    STATUS	VARCHAR2(50) CHECK ( STATUS IN ('ACTIVE', 'INACTIVE') )
);


CREATE TABLE PAYMENT_METHODS(
ID NUMBER PRIMARY KEY,
USER_ID NUMBER REFERENCES USERS(ID),
PAYMENT_METHOD VARCHAR2(50) CHECK( PAYMENT_METHOD IN ('CASH ON DELIVERY', 'CREDIT CARD', 'DEBIT CARD', 'NET BANKING') ),
CARD_NUMBER VARCHAR2(50), 
EXP_MONTH NUMBER ,
EXP_YEAR NUMBER ,  
NAME_ON_CARD VARCHAR2(50) 
);


CREATE TABLE CATEGORY (
ID	NUMBER PRIMARY KEY,
PRIMARY_CATEGORY	VARCHAR2 (50) not null,
SUB_CATEGORY	VARCHAR2 (50) not null);


CREATE TABLE CLASSIFIEDS(		
ID	NUMBER	PRIMARY KEY	,
CATEGORY_ID	NUMBER 	REFERENCES CATEGORY(ID)	,
TITLE	VARCHAR2(50)	NOT NULL	,
DESCRIPTION	VARCHAR2(500)	NOT NULL,
PRICE	NUMBER 	CHECK(PRICE > 0)	,
PHONE	VARCHAR2(15)	NOT NULL	,
EMAIL	VARCHAR2(50)	NOT NULL	,
UPDATE_DATE DATE,
CREATE_DATE DATE,
USER_ID NUMBER,
STATE   VARCHAR2(50)    DEFAULT 'POSTED' 
CHECK ( STATE IN ('POSTED', 'APPROVED', 'REJECTED', 'SOLD', 'REMOVED') )
);


CREATE TABLE CART(
ID	NUMBER PRIMARY KEY,
CLASSIFIED_ID	NUMBER REFERENCES CLASSIFIEDS(ID),
BIDPRICE	NUMBER CHECK(BIDPRICE > 0),
STATUS	VARCHAR2(50) DEFAULT 'BID' CHECK( STATUS IN ('BID', 'APPROVE') ),
BIDDER_ID NUMBER REFERENCES USERS(ID) 
);


CREATE TABLE PAYMENTS(
ID NUMBER PRIMARY KEY,
CLASSIFIED_ID NUMBER REFERENCES CLASSIFIEDS(ID),
USER_ID NUMBER REFERENCES USERS(ID),
PAYMENT_METHOD_ID  REFERENCES PAYMENT_METHODS(ID),
CART_ID NUMBER REFERENCES CART(ID),
AMOUNT NUMBER,
TRANSACTION_DATE DATE
);


--------------CREATE SEQUENCE --------------


DROP SEQUENCE  CART_ID;
DROP SEQUENCE  CATEGORY_ID;
DROP SEQUENCE  CLASSIFIED_ID;
DROP SEQUENCE  PAYMENTS_ID;
DROP SEQUENCE  PAYMENT_METHOD_ID;
DROP SEQUENCE  USER_ID;


CREATE SEQUENCE  CART_ID MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1
 NOCACHE NOORDER  NOCYCLE  NOKEEP  NOSCALE  GLOBAL ;

 
CREATE SEQUENCE  CATEGORY_ID  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1
 NOCACHE NOORDER  NOCYCLE  NOKEEP  NOSCALE  GLOBAL ;


CREATE SEQUENCE  CLASSIFIED_ID  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 21 NOCACHE  NOORDER  NOCYCLE  NOKEEP  NOSCALE  GLOBAL ;


CREATE SEQUENCE  PAYMENTS_ID  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 NOCACHE  NOORDER  NOCYCLE  NOKEEP  NOSCALE  GLOBAL ;

CREATE SEQUENCE  PAYMENT_METHOD_ID MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 NOCACHE  NOORDER  NOCYCLE  NOKEEP  NOSCALE  GLOBAL ;


CREATE SEQUENCE  USER_ID  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 NOCACHE  NOORDER  NOCYCLE  NOKEEP  NOSCALE  GLOBAL ;

---------------- CREATE TRIGGERS AND FUNCTIOINS START --------------------



CREATE OR REPLACE  FUNCTION CARD_STATUS(P_YEAR NUMBER, P_MONTH NUMBER) RETURN NUMBER
AS
BEGIN
     IF P_MONTH <  EXTRACT(MONTH FROM SYSDATE) OR  P_YEAR < EXTRACT(YEAR FROM SYSDATE) THEN
        RETURN 'EXPIRED';
    ELSE
        RETURN 'VALID';
    END IF;
END;
/


CREATE OR REPLACE  TRIGGER AUTO_CART_ID
BEFORE INSERT ON CART 
FOR EACH ROW
WHEN (NEW.ID IS NULL)
BEGIN
    :NEW.ID := cart_ID.NEXTVAL;
END;
/


CREATE OR REPLACE  TRIGGER AUTO_CATEGORY_ID
BEFORE INSERT ON CATEGORY 
FOR EACH ROW
WHEN (NEW.ID IS NULL)
BEGIN
    :NEW.ID := CATEGORY_ID.NEXTVAL;
END;
/


CREATE OR REPLACE  TRIGGER AUTO_CLASSIFIED_ID
BEFORE INSERT ON CLASSIFIEDS
FOR EACH ROW
WHEN (NEW.ID IS NULL)
BEGIN
    :NEW.ID := CLASSIFIED_ID.NEXTVAL;
END;
/


CREATE OR REPLACE  TRIGGER AUTO_PAYMENTS_ID
BEFORE INSERT ON PAYMENTS 
FOR EACH ROW
WHEN (NEW.ID IS NULL)
BEGIN
    :NEW.ID := PAYMENTS_ID.NEXTVAL;
END;
/



CREATE OR REPLACE  TRIGGER AUTO_PAYMENT_METHOD_ID
BEFORE INSERT ON PAYMENT_METHODS 
FOR EACH ROW
WHEN (NEW.ID IS NULL)
BEGIN
    :NEW.ID := PAYMENT_METHOD_ID.NEXTVAL;
END;
/



CREATE OR REPLACE  TRIGGER AUTO_USER_ID
BEFORE INSERT ON USERS 
FOR EACH ROW
WHEN (NEW.ID IS NULL)
BEGIN
    :NEW.ID := USER_ID.NEXTVAL;
END;
/




CREATE OR REPLACE  TRIGGER VALIDATE_PAYMENTS
BEFORE INSERT OR UPDATE ON PAYMENTS 
FOR EACH ROW
DECLARE
    V_STATUS  VARCHAR2(50);
BEGIN
    SELECT CARD_STATUS( EXP_YEAR, EXP_MONTH ) INTO V_STATUS
    FROM PAYMENT_METHODS
    WHERE ID = :NEW.PAYMENT_METHOD_ID;

    IF V_STATUS = 'EXPIRED' THEN
        RAISE_APPLICATION_ERROR(-20002,
        'CARD EXPIRED' || chr(13) || chr(10) || 'TRY A DIFFERENT PAYMENT METHOD');
    END IF;
    --ANY OTHER VALIDATION TEST---- 
END;
/





CREATE OR REPLACE  TRIGGER VALIDATE_PAYMENT_METHODS
BEFORE INSERT OR UPDATE ON PAYMENT_METHODS 
FOR EACH ROW
BEGIN
    IF CARD_STATUS(:NEW.EXP_YEAR, :NEW.EXP_MONTH) = 'EXPIRED' THEN
        RAISE_APPLICATION_ERROR(-20001,'CARD EXPIRED');
    END IF;
    --ANY OTHER VALIDATION TEST---- 
END;
/


------------------------- CREATE TRIGGER AND FUNCTION END --------------------------


----patch -----
create or replace view vcart(id, classified_id, bidprice, status, bidder_id, title , expected_price, seller_id)
as
select c.id, c.classified_id, c.bidprice, c.status, c.bidder_id, l.title ,l.price , l.user_id
from cart c join classifieds l on c.classified_id = l.id 
order by c.bidprice desc;

-----view created -------

create or replace TRIGGER VALIDATE_PAYMENT_METHODS
BEFORE INSERT OR UPDATE ON PAYMENT_METHODS 
FOR EACH ROW
WHEN ( NEW.PAYMENT_METHOD IN ('CREDIT CARD', 'DEBIT CARD') )
BEGIN
    IF :NEW.NAME_ON_CARD IS NULL THEN
     RAISE_APPLICATION_ERROR(-20002,'NAME ON CAD CAN''T BE NULL');
    END IF;
    IF NOT ( LENGTH(:NEW.CARD_NUMBER) BETWEEN 13 AND 16 ) THEN
        RAISE_APPLICATION_ERROR(-20003,'CARD NUMBER NOT VALID');
    END IF;
    
    IF CARD_STATUS(:NEW.EXP_YEAR, :NEW.EXP_MONTH) = 'EXPIRED' THEN
        RAISE_APPLICATION_ERROR(-20001,'CARD EXPIRED');
    END IF;
    --ANY OTHER VALIDATION TEST---- 
END;
/
------------------

create or replace TRIGGER AUTO_COD_FOR_USER
BEFORE INSERT ON USERS 
FOR EACH ROW
WHEN (NEW.ID IS NULL)
BEGIN
    insert into payment_methods(USER_ID, PAYMENT_METHOD)
    values(:NEW.ID,'CASH ON DELIVERY');
END;

-----------------

/* Adding default rows */

insert into users(EMAIL,PASSWORD,IS_ADMIN,FIRST_NAME,LAST_NAME,ADDRESS,PHONE,STATUS) values 
('ana@amazon.com','ana','N','Ana','A','Sec-bad','9976541902','ACTIVE');


INSERT INTO CATEGORY (PRIMARY_CATEGORY, SUB_CATEGORY) VALUES ('Electronics','Mobiles');
INSERT INTO CATEGORY (PRIMARY_CATEGORY, SUB_CATEGORY) VALUES ('Electronics','Laptops');
INSERT INTO CATEGORY (PRIMARY_CATEGORY, SUB_CATEGORY) VALUES ('Electronics','Speakers');

INSERT INTO CATEGORY (PRIMARY_CATEGORY, SUB_CATEGORY) VALUES ('Vehicles','Cars');
INSERT INTO CATEGORY (PRIMARY_CATEGORY, SUB_CATEGORY) VALUES ('Vehicles','"Motor-cycles');
INSERT INTO CATEGORY (PRIMARY_CATEGORY, SUB_CATEGORY) VALUES ('Vehicles','Bi-cycles');


INSERT INTO CATEGORY (PRIMARY_CATEGORY, SUB_CATEGORY) VALUES ('Music','Instruments');
INSERT INTO CATEGORY (PRIMARY_CATEGORY, SUB_CATEGORY) VALUES ('Music','CD');

INSERT INTO CATEGORY (PRIMARY_CATEGORY, SUB_CATEGORY) VALUES ('Books','Books');
INSERT INTO CATEGORY (PRIMARY_CATEGORY, SUB_CATEGORY) VALUES ('Lost and Unclaimed','Misc.');


commit;