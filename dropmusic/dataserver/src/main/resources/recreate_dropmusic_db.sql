-- Type package declaration
create or replace package PDTypes
as
    TYPE ref_cursor IS REF CURSOR;
end;

-- Integrity package declaration
create or replace package IntegrityPackage AS
 procedure InitNestLevel;
 function GetNestLevel return number;
 procedure NextNestLevel;
 procedure PreviousNestLevel;
 end IntegrityPackage;
/

-- Integrity package definition
create or replace package body IntegrityPackage AS
 NestLevel number;

-- Procedure to initialize the trigger nest level
 procedure InitNestLevel is
 begin
 NestLevel := 0;
 end;


-- Function to return the trigger nest level
 function GetNestLevel return number is
 begin
 if NestLevel is null then
     NestLevel := 0;
 end if;
 return(NestLevel);
 end;

-- Procedure to increase the trigger nest level
 procedure NextNestLevel is
 begin
 if NestLevel is null then
     NestLevel := 0;
 end if;
 NestLevel := NestLevel + 1;
 end;

-- Procedure to decrease the trigger nest level
 procedure PreviousNestLevel is
 begin
 NestLevel := NestLevel - 1;
 end;

 end IntegrityPackage;
/


drop trigger TIB_ALBUM
/

drop trigger TIB_ARTIST
/

drop trigger TIB_FILE
/

drop trigger TIB_MUSIC
/

drop trigger TIB_USER
/

alter table ARTIST_ALBUM
   drop constraint FK_ARTIST_A_ARTIST_AL_ARTIST
/

alter table ARTIST_ALBUM
   drop constraint FK_ARTIST_A_ARTIST_AL_ALBUM
/

alter table "FILE"
   drop constraint FK_FILE_FILE_MUSI_MUSIC
/

alter table MUSIC
   drop constraint FK_MUSIC_ALBUM_MUS_ALBUM
/

alter table USER_FILES
   drop constraint FK_USER_FIL_USER_FILE_USER
/

alter table USER_FILES
   drop constraint FK_USER_FIL_USER_FILE_FILE
/

drop table ALBUM cascade constraints
/

drop table ARTIST cascade constraints
/

drop index ARTIST_ALBUM2_FK
/

drop index ARTIST_ALBUM_FK
/

drop table ARTIST_ALBUM cascade constraints
/

drop index FILE_MUSIC_FK
/

drop table "FILE" cascade constraints
/

drop index ALBUM_MUSIC_FK
/

drop table MUSIC cascade constraints
/

drop table "USER" cascade constraints
/

drop index USER_FILES2_FK
/

drop index USER_FILES_FK
/

drop table USER_FILES cascade constraints
/

drop sequence S_ALBUM
/

drop sequence S_ARTIST
/

drop sequence S_FILE
/

drop sequence S_MUSIC
/

drop sequence S_USER
/

create sequence S_ALBUM
/

create sequence S_ARTIST
/

create sequence S_FILE
/

create sequence S_MUSIC
/

create sequence S_USER
/

/*==============================================================*/
/* Table: ALBUM                                                 */
/*==============================================================*/
create table ALBUM
(
   ID                   NUMBER(6)            not null,
   NAME                 VARCHAR2(64)         not null,
   DESCRIPTION          VARCHAR2(1024)       not null,
   SCORE                FLOAT,
   constraint PK_ALBUM primary key (ID)
)
/

/*==============================================================*/
/* Table: ARTIST                                                */
/*==============================================================*/
create table ARTIST
(
   ID                   NUMBER(6)            not null,
   NAME                 VARCHAR2(32)         not null,
   constraint PK_ARTIST primary key (ID)
)
/

/*==============================================================*/
/* Table: ARTIST_ALBUM                                          */
/*==============================================================*/
create table ARTIST_ALBUM
(
   ID                   NUMBER(6)            not null,
   ALB_ID               NUMBER(6)            not null,
   constraint PK_ARTIST_ALBUM primary key (ID, ALB_ID)
)
/

/*==============================================================*/
/* Index: ARTIST_ALBUM_FK                                       */
/*==============================================================*/
create index ARTIST_ALBUM_FK on ARTIST_ALBUM (
   ID ASC
)
/

/*==============================================================*/
/* Index: ARTIST_ALBUM2_FK                                      */
/*==============================================================*/
create index ARTIST_ALBUM2_FK on ARTIST_ALBUM (
   ALB_ID ASC
)
/

/*==============================================================*/
/* Table: "FILE"                                                */
/*==============================================================*/
create table "FILE"
(
   ID                   NUMBER(6)            not null,
   MUS_ID               NUMBER(6)            not null,
   BIN                  SMALLINT             not null,
   constraint PK_FILE primary key (ID)
)
/

/*==============================================================*/
/* Index: FILE_MUSIC_FK                                         */
/*==============================================================*/
create index FILE_MUSIC_FK on "FILE" (
   MUS_ID ASC
)
/

/*==============================================================*/
/* Table: MUSIC                                                 */
/*==============================================================*/
create table MUSIC
(
   ID                   NUMBER(6)            not null,
   ALB_ID               NUMBER(6)            not null,
   NAME                 VARCHAR2(32)         not null,
   constraint PK_MUSIC primary key (ID)
)
/

/*==============================================================*/
/* Index: ALBUM_MUSIC_FK                                        */
/*==============================================================*/
create index ALBUM_MUSIC_FK on MUSIC (
   ALB_ID ASC
)
/

/*==============================================================*/
/* Table: "USER"                                                */
/*==============================================================*/
create table "USER"
(
   ID                   NUMBER(6)            not null,
   NAME                 VARCHAR2(32)         not null,
   PASSWORD             VARCHAR2(32)         not null,
   EDITOR               SMALLINT             not null,
   constraint PK_USER primary key (ID)
)
/

/*==============================================================*/
/* Table: USER_FILES                                            */
/*==============================================================*/
create table USER_FILES
(
   ID                   NUMBER(6)            not null,
   FIL_ID               NUMBER(6)            not null,
   constraint PK_USER_FILES primary key (ID, FIL_ID)
)
/

/*==============================================================*/
/* Index: USER_FILES_FK                                         */
/*==============================================================*/
create index USER_FILES_FK on USER_FILES (
   ID ASC
)
/

/*==============================================================*/
/* Index: USER_FILES2_FK                                        */
/*==============================================================*/
create index USER_FILES2_FK on USER_FILES (
   FIL_ID ASC
)
/

alter table ARTIST_ALBUM
   add constraint FK_ARTIST_A_ARTIST_AL_ARTIST foreign key (ID)
      references ARTIST (ID)
/

alter table ARTIST_ALBUM
   add constraint FK_ARTIST_A_ARTIST_AL_ALBUM foreign key (ALB_ID)
      references ALBUM (ID)
/

alter table "FILE"
   add constraint FK_FILE_FILE_MUSI_MUSIC foreign key (MUS_ID)
      references MUSIC (ID)
/

alter table MUSIC
   add constraint FK_MUSIC_ALBUM_MUS_ALBUM foreign key (ALB_ID)
      references ALBUM (ID)
/

alter table USER_FILES
   add constraint FK_USER_FIL_USER_FILE_USER foreign key (ID)
      references "USER" (ID)
/

alter table USER_FILES
   add constraint FK_USER_FIL_USER_FILE_FILE foreign key (FIL_ID)
      references "FILE" (ID)
/

create trigger TIB_ALBUM before insert
on ALBUM for each row
declare
    integrity_error  exception;
    errno            integer;
    errmsg           char(200);
    dummy            integer;
    found            boolean;

begin
    --  Column "ID" uses sequence S_ALBUM
    select S_ALBUM.NEXTVAL INTO :new.ID from dual;

--  Errors handling
exception
    when integrity_error then
       raise_application_error(errno, errmsg);
end;
/

create trigger TIB_ARTIST before insert
on ARTIST for each row
declare
    integrity_error  exception;
    errno            integer;
    errmsg           char(200);
    dummy            integer;
    found            boolean;

begin
    --  Column "ID" uses sequence S_ARTIST
    select S_ARTIST.NEXTVAL INTO :new.ID from dual;

--  Errors handling
exception
    when integrity_error then
       raise_application_error(errno, errmsg);
end;
/

create trigger TIB_FILE before insert
on "FILE" for each row
declare
    integrity_error  exception;
    errno            integer;
    errmsg           char(200);
    dummy            integer;
    found            boolean;

begin
    --  Column "ID" uses sequence S_FILE
    select S_FILE.NEXTVAL INTO :new.ID from dual;

--  Errors handling
exception
    when integrity_error then
       raise_application_error(errno, errmsg);
end;
/

create trigger TIB_MUSIC before insert
on MUSIC for each row
declare
    integrity_error  exception;
    errno            integer;
    errmsg           char(200);
    dummy            integer;
    found            boolean;

begin
    --  Column "ID" uses sequence S_MUSIC
    select S_MUSIC.NEXTVAL INTO :new.ID from dual;

--  Errors handling
exception
    when integrity_error then
       raise_application_error(errno, errmsg);
end;
/

create trigger TIB_USER before insert
on "USER" for each row
declare
    integrity_error  exception;
    errno            integer;
    errmsg           char(200);
    dummy            integer;
    found            boolean;

begin
    --  Column "ID" uses sequence S_USER
    select S_USER.NEXTVAL INTO :new.ID from dual;

--  Errors handling
exception
    when integrity_error then
       raise_application_error(errno, errmsg);
end;
/

