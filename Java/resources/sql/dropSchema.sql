--##################################################################### DROP_CONSTRAINTS ################################################

begin execute immediate 'ALTER TABLE STATION_BUS DROP CONSTRAINT STATION_BUS_BUS_ID_FK'; exception when others then if sqlcode !=-2443 and sqlcode != -942 then raise; end if; end;
/

begin execute immediate 'ALTER TABLE STATION_BUS DROP CONSTRAINT STATION_BUS_STATION_ID_FK'; exception when others then if sqlcode !=-2443 and sqlcode != -942 then raise; end if; end;
/

begin execute immediate 'ALTER TABLE STATION_BUS DROP CONSTRAINT PK_STATION_BUS'; exception when others then if sqlcode !=-2443 and sqlcode != -942 then raise; end if; end;
/

begin execute immediate 'ALTER TABLE BUS DROP CONSTRAINT BUS_PK'; exception when others then if sqlcode !=-2443 and sqlcode != -942 then raise; end if; end;
/

begin execute immediate 'ALTER TABLE BUS DROP CONSTRAINT UNIQUE_BUSINESS_ID'; exception when others then if sqlcode !=-2443 and sqlcode != -942 then raise; end if; end;
/

begin execute immediate 'ALTER TABLE BUS DROP CONSTRAINT UNIQUE_NAME'; exception when others then if sqlcode !=-2443 and sqlcode != -942 then raise; end if; end;
/

begin execute immediate 'ALTER TABLE BUS_LOCATION_UPDATE DROP CONSTRAINT UNIQUE_BUS_LOCATION_UPDATE'; exception when others then if sqlcode !=-2443 and sqlcode != -942 then raise; end if; end;
/

begin execute immediate 'ALTER TABLE BUS_LOCATION_UPDATE DROP CONSTRAINT BUS_LOCATION_UPDATE_PK'; exception when others then if sqlcode !=-2443 and sqlcode != -942 then raise; end if; end;
/

begin execute immediate 'ALTER TABLE BUS_LOCATION_UPDATE DROP CONSTRAINT TRIP_ID_FK'; exception when others then if sqlcode !=-2443 and sqlcode != -942 then raise; end if; end;
/

begin execute immediate 'ALTER TABLE TRIP DROP CONSTRAINT TRIP_PK'; exception when others then if sqlcode !=-2443 and sqlcode != -942 then raise; end if; end;
/

begin execute immediate 'ALTER TABLE TRIP DROP CONSTRAINT UNIQUE_TRIP_BUS_TRIP_ID'; exception when others then if sqlcode !=-2443 and sqlcode != -942 then raise; end if; end;
/

begin execute immediate 'ALTER TABLE STATION DROP CONSTRAINT STATION_PK'; exception when others then if sqlcode !=-2443 and sqlcode != -942 then raise; end if; end;
/

begin execute immediate 'ALTER TABLE STATION DROP CONSTRAINT UNIQUE_BUSINESS_ID'; exception when others then if sqlcode !=-2443 and sqlcode != -942 then raise; end if; end;
/

begin execute immediate 'ALTER TABLE STATION DROP CONSTRAINT UNIQUE_NAME'; exception when others then if sqlcode !=-2443 and sqlcode != -942 then raise; end if; end;
/

--##################################################################### DROP_TABLES ################################################

begin execute immediate 'DROP TABLE TRIP'; exception when others then if sqlcode != -942 then raise; end if; end;
/
begin execute immediate 'DROP TABLE BUS'; exception when others then if sqlcode != -942 then raise; end if; end;
/
begin execute immediate 'DROP TABLE BUS_LOCATION_UPDATE'; exception when others then if sqlcode != -942 then raise; end if; end;
/
begin execute immediate 'DROP TABLE STATION'; exception when others then if sqlcode != -942 then raise; end if; end;
/
begin execute immediate 'DROP TABLE STATION_BUS'; exception when others then if sqlcode != -942 then raise; end if; end;
/

--##################################################################### DROP_VIEWS ################################################

begin execute immediate 'DROP VIEW STATION_BUS_VIEW'; exception when others then if sqlcode != -942 then raise; end if; end;
/

--##################################################################### DROP_SEQUENCES ################################################

begin execute immediate 'DROP SEQUENCE S_TRIP'; exception when others then if sqlcode != -2289 then raise; end if; end;
/
begin execute immediate 'DROP SEQUENCE S_BUS'; exception when others then if sqlcode != -2289 then raise; end if; end;
/
begin execute immediate 'DROP SEQUENCE S_BUS_LOCATION_UPDATE'; exception when others then if sqlcode != -2289 then raise; end if; end;
/
begin execute immediate 'DROP SEQUENCE S_STATION'; exception when others then if sqlcode != -2289 then raise; end if; end;
/
begin execute immediate 'DROP SEQUENCE S_STATION_BUS'; exception when others then if sqlcode != -2289 then raise; end if; end;
/
