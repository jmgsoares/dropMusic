/*==============================================================*/
/* DBMS name:      ORACLE Version 11g                           */
/* Created on:     24/10/2018 18:55:36                          */
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

drop table ARTIST_ALBUM cascade constraints;

drop table "FILE" cascade constraints;

drop table MUSIC cascade constraints;

drop table "USER" cascade constraints;

drop table USER_FILES cascade constraints;

drop sequence S_ALBUM;

drop sequence S_ARTIST;

drop sequence S_FILE;

drop sequence S_MUSIC;

drop sequence S_USER;

create sequence S_ALBUM;

create sequence S_ARTIST;

create sequence S_FILE;

create sequence S_MUSIC;

create sequence S_USER;

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
);

/*==============================================================*/
/* Table: ARTIST                                                */
/*==============================================================*/
create table ARTIST 
(
   ID                   NUMBER(6)            not null,
   NAME                 VARCHAR2(32)         not null,
   constraint PK_ARTIST primary key (ID)
);

/*==============================================================*/
/* Table: ARTIST_ALBUM                                          */
/*==============================================================*/
create table ARTIST_ALBUM 
(
   ID                   NUMBER(6)            not null,
   ALB_ID               NUMBER(6)            not null,
   constraint PK_ARTIST_ALBUM primary key (ID, ALB_ID),
   constraint FK_ARTIST_A_ARTIST_AL_ARTIST foreign key (ID)
         references ARTIST (ID),
   constraint FK_ARTIST_A_ARTIST_AL_ALBUM foreign key (ALB_ID)
         references ALBUM (ID)
);

/*==============================================================*/
/* Table: MUSIC                                                 */
/*==============================================================*/
create table MUSIC 
(
   ID                   NUMBER(6)            not null,
   ALB_ID               NUMBER(6)            not null,
   NAME                 VARCHAR2(32)         not null,
   constraint PK_MUSIC primary key (ID),
   constraint FK_MUSIC_ALBUM_MUS_ALBUM foreign key (ALB_ID)
         references ALBUM (ID)
);

/*==============================================================*/
/* Table: "FILE"                                                */
/*==============================================================*/
create table "FILE" 
(
   ID                   NUMBER(6)            not null,
   MUS_ID               NUMBER(6)            not null,
   BIN                  SMALLINT             not null,
   constraint PK_FILE primary key (ID),
   constraint FK_FILE_FILE_MUSI_MUSIC foreign key (MUS_ID)
         references MUSIC (ID)
);

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
);

/*==============================================================*/
/* Table: USER_FILES                                            */
/*==============================================================*/
create table USER_FILES 
(
   ID                   NUMBER(6)            not null,
   FIL_ID               NUMBER(6)            not null,
   constraint PK_USER_FILES primary key (ID, FIL_ID),
   constraint FK_USER_FIL_USER_FILE_USER foreign key (ID)
         references "USER" (ID),
   constraint FK_USER_FIL_USER_FILE_FILE foreign key (FIL_ID)
         references "FILE" (ID)
);

