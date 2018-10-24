/*==============================================================*/
/* DBMS name:      ORACLE Version 12c                           */
/* Created on:     24/10/2018 15:53:48                          */
/*==============================================================*/


alter table ARTIST_ALBUM
   drop constraint FK_ARTIST_A_ARTIST_AL_ARTIST;

alter table ARTIST_ALBUM
   drop constraint FK_ARTIST_A_ARTIST_AL_ALBUM;

alter table "FILE"
   drop constraint FK_FILE_FILE_MUSI_MUSIC;

alter table MUSIC
   drop constraint FK_MUSIC_ALBUM_MUS_ALBUM;

alter table USER_FILES
   drop constraint FK_USER_FIL_USER_FILE_USER;

alter table USER_FILES
   drop constraint FK_USER_FIL_USER_FILE_FILE;

drop table ALBUM cascade constraints;

drop table ARTIST cascade constraints;

drop index ARTIST_ALBUM2_FK;

drop index ARTIST_ALBUM_FK;

drop table ARTIST_ALBUM cascade constraints;

drop index FILE_MUSIC_FK;

drop table "FILE" cascade constraints;

drop index ALBUM_MUSIC_FK;

drop table MUSIC cascade constraints;

drop table "USER" cascade constraints;

drop index USER_FILES2_FK;

drop index USER_FILES_FK;

drop table USER_FILES cascade constraints;

/*==============================================================*/
/* Table: ALBUM                                                 */
/*==============================================================*/
create table ALBUM (
   ID                   NUMBER(6)           
      generated as identity ( start with 1 nocycle noorder)  not null,
   NAME                 VARCHAR2(64)          not null,
   DESCRIPTION          VARCHAR2(1024)        not null,
   SCORE                FLOAT,
   constraint PK_ALBUM primary key (ID)
);

/*==============================================================*/
/* Table: ARTIST                                                */
/*==============================================================*/
create table ARTIST (
   ID                   NUMBER(6)           
      generated as identity ( start with 1 nocycle noorder)  not null,
   NAME                 VARCHAR2(32)          not null,
   constraint PK_ARTIST primary key (ID)
);

/*==============================================================*/
/* Table: ARTIST_ALBUM                                          */
/*==============================================================*/
create table ARTIST_ALBUM (
   ID                   NUMBER(6)             not null,
   ALB_ID               NUMBER(6)             not null,
   constraint PK_ARTIST_ALBUM primary key (ID, ALB_ID)
);

/*==============================================================*/
/* Index: ARTIST_ALBUM_FK                                       */
/*==============================================================*/
create index ARTIST_ALBUM_FK on ARTIST_ALBUM (
   ID ASC
);

/*==============================================================*/
/* Index: ARTIST_ALBUM2_FK                                      */
/*==============================================================*/
create index ARTIST_ALBUM2_FK on ARTIST_ALBUM (
   ALB_ID ASC
);

/*==============================================================*/
/* Table: "FILE"                                                */
/*==============================================================*/
create table "FILE" (
   ID                   NUMBER(6)           
      generated as identity ( start with 1 nocycle noorder)  not null,
   MUS_ID               NUMBER(6)             not null,
   BIN                  SMALLINT              not null,
   constraint PK_FILE primary key (ID)
);

/*==============================================================*/
/* Index: FILE_MUSIC_FK                                         */
/*==============================================================*/
create index FILE_MUSIC_FK on "FILE" (
   MUS_ID ASC
);

/*==============================================================*/
/* Table: MUSIC                                                 */
/*==============================================================*/
create table MUSIC (
   ID                   NUMBER(6)           
      generated as identity ( start with 1 nocycle noorder)  not null,
   ALB_ID               NUMBER(6)             not null,
   NAME                 VARCHAR2(32)          not null,
   constraint PK_MUSIC primary key (ID)
);

/*==============================================================*/
/* Index: ALBUM_MUSIC_FK                                        */
/*==============================================================*/
create index ALBUM_MUSIC_FK on MUSIC (
   ALB_ID ASC
);

/*==============================================================*/
/* Table: "USER"                                                */
/*==============================================================*/
create table "USER" (
   ID                   NUMBER(6)           
      generated as identity ( start with 1 nocycle noorder)  not null,
   NAME                 VARCHAR2(32)          not null,
   PASSWORD             VARCHAR2(32)          not null,
   EDITOR               SMALLINT              not null,
   constraint PK_USER primary key (ID)
);

/*==============================================================*/
/* Table: USER_FILES                                            */
/*==============================================================*/
create table USER_FILES (
   ID                   NUMBER(6)             not null,
   FIL_ID               NUMBER(6)             not null,
   constraint PK_USER_FILES primary key (ID, FIL_ID)
);

/*==============================================================*/
/* Index: USER_FILES_FK                                         */
/*==============================================================*/
create index USER_FILES_FK on USER_FILES (
   ID ASC
);

/*==============================================================*/
/* Index: USER_FILES2_FK                                        */
/*==============================================================*/
create index USER_FILES2_FK on USER_FILES (
   FIL_ID ASC
);

alter table ARTIST_ALBUM
   add constraint FK_ARTIST_A_ARTIST_AL_ARTIST foreign key (ID)
      references ARTIST (ID);

alter table ARTIST_ALBUM
   add constraint FK_ARTIST_A_ARTIST_AL_ALBUM foreign key (ALB_ID)
      references ALBUM (ID);

alter table "FILE"
   add constraint FK_FILE_FILE_MUSI_MUSIC foreign key (MUS_ID)
      references MUSIC (ID);

alter table MUSIC
   add constraint FK_MUSIC_ALBUM_MUS_ALBUM foreign key (ALB_ID)
      references ALBUM (ID);

alter table USER_FILES
   add constraint FK_USER_FIL_USER_FILE_USER foreign key (ID)
      references "USER" (ID);

alter table USER_FILES
   add constraint FK_USER_FIL_USER_FILE_FILE foreign key (FIL_ID)
      references "FILE" (ID);

