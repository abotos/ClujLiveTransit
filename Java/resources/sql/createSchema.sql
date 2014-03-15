--##################################################################### CREATE_SEQUENCES ################################################

CREATE SEQUENCE S_TRIP MINVALUE 1 MAXVALUE 999999999999999999999999999 INCREMENT BY 1 START WITH 1
/

CREATE SEQUENCE S_BUS MINVALUE 1 MAXVALUE 999999999999999999999999999 INCREMENT BY 1 START WITH 1
/

CREATE SEQUENCE S_BUS_LOCATION_UPDATE MINVALUE 1 MAXVALUE 999999999999999999999999999 INCREMENT BY 1 START WITH 1
/

CREATE SEQUENCE S_STATION MINVALUE 1 MAXVALUE 999999999999999999999999999 INCREMENT BY 1 START WITH 1
/

CREATE SEQUENCE S_STATION_BUS MINVALUE 1 MAXVALUE 999999999999999999999999999 INCREMENT BY 1 START WITH 1
/
--##################################################################### DEFINITION_TYPE ################################################

CREATE TABLE "TRIP" (
  "ID"      NUMBER            NOT NULL ENABLE,
  "BUS_ID"  VARCHAR2(75 CHAR) NOT NULL ENABLE,
  "TRIP_ID" VARCHAR2(75 CHAR) NOT NULL ENABLE,
  "IS_ACTIVE" INTEGER,
  CONSTRAINT "TRIP_PK" PRIMARY KEY ("ID") ENABLE,
  CONSTRAINT "UNIQUE_TRIP_BUS_TRIP_ID" UNIQUE ("BUS_ID", "TRIP_ID") ENABLE
)
/

CREATE TABLE "BUS" (
  "ID"            NUMBER            NOT NULL ENABLE,
  "BUSINESS_ID"   VARCHAR2(75 CHAR) NOT NULL ENABLE,
  "NAME"          VARCHAR2(75 CHAR) NOT NULL ENABLE,
  "DISPLAY_IMAGE" VARCHAR2(75 CHAR) NOT NULL ENABLE,
  CONSTRAINT "BUS_PK" PRIMARY KEY ("ID") ENABLE,
  CONSTRAINT "UNIQUE_BUS_BUSINESS_ID" UNIQUE ("BUSINESS_ID") ENABLE,
  CONSTRAINT "UNIQUE_BUS_NAME" UNIQUE ("NAME") ENABLE
)
/

CREATE TABLE "BUS_LOCATION_UPDATE" (
  "ID"          NUMBER                              NOT NULL ENABLE,
  "LAST_UPDATE" TIMESTAMP(6) DEFAULT systimestamp   NOT NULL ENABLE,
  "TRIP_ID"     NUMBER                              NOT NULL ENABLE,
  "LATITUDE"    VARCHAR2(75 CHAR)                   NOT NULL ENABLE,
  "LONGITUDE"   VARCHAR2(75 CHAR)                   NOT NULL ENABLE,
  CONSTRAINT "BUS_LOCATION_UPDATE_PK" PRIMARY KEY ("ID") ENABLE,
  CONSTRAINT "BUS_LOCATION_UPDATE_TRIP_ID_FK" FOREIGN KEY ("TRIP_ID") REFERENCES "TRIP" ("ID") ENABLE
)
/

CREATE TABLE "STATION" (
  "ID"          NUMBER            NOT NULL ENABLE,
  "BUSINESS_ID" NUMBER            NOT NULL ENABLE,
  "NAME"        VARCHAR2(75 CHAR) NOT NULL ENABLE,
  "LATITUDE"    VARCHAR2(75 CHAR) NOT NULL ENABLE,
  "LONGITUDE"   VARCHAR2(75 CHAR) NOT NULL ENABLE,
  CONSTRAINT "STATION_PK" PRIMARY KEY ("ID") ENABLE,
  CONSTRAINT "UNIQUE_STATION_BUSINESS_ID" UNIQUE ("BUSINESS_ID") ENABLE,
  CONSTRAINT "UNIQUE_STATION_NAME" UNIQUE ("NAME") ENABLE
)
/

CREATE TABLE "STATION_BUS" (
  "ID"            NUMBER NOT NULL ENABLE,
  "STATION_ID"    NUMBER NOT NULL ENABLE,
  "BUS_ID"        VARCHAR2(75 CHAR) NOT NULL ENABLE,
  "STATION_ORDER" NUMBER NOT NULL ENABLE,
  CONSTRAINT PK_STATION_BUS PRIMARY KEY ("ID") ENABLE,
  CONSTRAINT STATION_BUS_BUS_ID_FK FOREIGN KEY ("BUS_ID") REFERENCES "BUS" ("BUSINESS_ID") ON DELETE CASCADE ENABLE,
  CONSTRAINT STATION_BUS_STATION_ID_FK FOREIGN KEY ("STATION_ID") REFERENCES "STATION" ("BUSINESS_ID") ON DELETE CASCADE ENABLE
)
/

CREATE OR REPLACE FORCE VIEW "STATION_BUS_VIEW"(
  "ID",
  "STATION_ID",
  "BUS_ID",
  "STATION_ORDER")
AS
  SELECT
    STATION_BUS.ID AS ID,
    STATION.ID AS STATION_ID,
    BUS.ID AS BUS_ID,
    STATION_BUS.STATION_ORDER AS STATION_ORDER
  FROM STATION_BUS
    INNER JOIN STATION
      ON STATION.BUSINESS_ID = STATION_BUS.STATION_ID
    INNER JOIN BUS
      ON BUS.BUSINESS_ID = STATION_BUS.BUS_ID
/