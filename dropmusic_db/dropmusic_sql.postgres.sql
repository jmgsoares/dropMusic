/*==============================================================*/
/* DBMS name:      PostgreSQL 8                                 */
/* Created on:     27/10/2018 01:07:54                          */
/*==============================================================*/


drop index ALBUM_PK;

drop table ALBUM;

drop index ARTIST_PK;

drop table ARTIST;

drop index ARTIST_ALBUM2_FK;

drop index ARTIST_ALBUM_FK;

drop index ARTIST_ALBUM_PK;

drop table ARTIST_ALBUM;

drop index FILE_MUSIC_FK;

drop index FILE_PK;

drop table FILE;

drop index ALBUM_MUSIC_FK;

drop index MUSIC_PK;

drop table MUSIC;

drop index USER_NOTIFICATION_FK;

drop index NOTIFICATION_PK;

drop table NOTIFICATION;

drop index USER_PK;

drop table "USER";

drop index USER_FILES2_FK;

drop index USER_FILES_FK;

drop index USER_FILES_PK;

drop table USER_FILES;

/*==============================================================*/
/* Table: ALBUM                                                 */
/*==============================================================*/
create table ALBUM (
   ID                   SERIAL               not null,
   NAME                 VARCHAR(64)          not null,
   DESCRIPTION          VARCHAR(1024)        not null,
   SCORE                FLOAT8               null,
   constraint PK_ALBUM primary key (ID)
);

/*==============================================================*/
/* Index: ALBUM_PK                                              */
/*==============================================================*/
create unique index ALBUM_PK on ALBUM (
ID
);

/*==============================================================*/
/* Table: ARTIST                                                */
/*==============================================================*/
create table ARTIST (
   ID                   SERIAL               not null,
   NAME                 VARCHAR(32)          not null,
   constraint PK_ARTIST primary key (ID)
);

/*==============================================================*/
/* Index: ARTIST_PK                                             */
/*==============================================================*/
create unique index ARTIST_PK on ARTIST (
ID
);

/*==============================================================*/
/* Table: ARTIST_ALBUM                                          */
/*==============================================================*/
create table ARTIST_ALBUM (
   ID                   INT4                 not null,
   ALB_ID               INT4                 not null,
   constraint PK_ARTIST_ALBUM primary key (ID, ALB_ID)
);

/*==============================================================*/
/* Index: ARTIST_ALBUM_PK                                       */
/*==============================================================*/
create unique index ARTIST_ALBUM_PK on ARTIST_ALBUM (
ID,
ALB_ID
);

/*==============================================================*/
/* Index: ARTIST_ALBUM_FK                                       */
/*==============================================================*/
create  index ARTIST_ALBUM_FK on ARTIST_ALBUM (
ID
);

/*==============================================================*/
/* Index: ARTIST_ALBUM2_FK                                      */
/*==============================================================*/
create  index ARTIST_ALBUM2_FK on ARTIST_ALBUM (
ALB_ID
);

/*==============================================================*/
/* Table: FILE                                                  */
/*==============================================================*/
create table FILE (
   ID                   SERIAL               not null,
   MUS_ID               INT4                 not null,
   BIN                  INT2                 null,
   PATH                 VARCHAR(1024)        null,
   NAME                 VARCHAR(1024)        not null,
   constraint PK_FILE primary key (ID)
);

/*==============================================================*/
/* Index: FILE_PK                                               */
/*==============================================================*/
create unique index FILE_PK on FILE (
ID
);

/*==============================================================*/
/* Index: FILE_MUSIC_FK                                         */
/*==============================================================*/
create  index FILE_MUSIC_FK on FILE (
MUS_ID
);

/*==============================================================*/
/* Table: MUSIC                                                 */
/*==============================================================*/
create table MUSIC (
   ID                   SERIAL               not null,
   ALB_ID               INT4                 not null,
   NAME                 VARCHAR(32)          not null,
   constraint PK_MUSIC primary key (ID)
);

/*==============================================================*/
/* Index: MUSIC_PK                                              */
/*==============================================================*/
create unique index MUSIC_PK on MUSIC (
ID
);

/*==============================================================*/
/* Index: ALBUM_MUSIC_FK                                        */
/*==============================================================*/
create  index ALBUM_MUSIC_FK on MUSIC (
ALB_ID
);

/*==============================================================*/
/* Table: NOTIFICATION                                          */
/*==============================================================*/
create table NOTIFICATION (
   ID                   SERIAL               not null,
   USE_ID               INT4                 not null,
   MESSAGE              VARCHAR(1024)        not null,
   constraint PK_NOTIFICATION primary key (ID)
);

/*==============================================================*/
/* Index: NOTIFICATION_PK                                       */
/*==============================================================*/
create unique index NOTIFICATION_PK on NOTIFICATION (
ID
);

/*==============================================================*/
/* Index: USER_NOTIFICATION_FK                                  */
/*==============================================================*/
create  index USER_NOTIFICATION_FK on NOTIFICATION (
USE_ID
);

/*==============================================================*/
/* Table: "USER"                                                */
/*==============================================================*/
create table "USER" (
   ID                   SERIAL               not null,
   NAME                 VARCHAR(32)          not null,
   PASSWORD             VARCHAR(32)          not null,
   EDITOR               BOOL                 not null,
   constraint PK_USER primary key (ID)
);

/*==============================================================*/
/* Index: USER_PK                                               */
/*==============================================================*/
create unique index USER_PK on "USER" (
ID
);

/*==============================================================*/
/* Table: USER_FILES                                            */
/*==============================================================*/
create table USER_FILES (
   ID                   INT4                 not null,
   FIL_ID               INT4                 not null,
   constraint PK_USER_FILES primary key (ID, FIL_ID)
);

/*==============================================================*/
/* Index: USER_FILES_PK                                         */
/*==============================================================*/
create unique index USER_FILES_PK on USER_FILES (
ID,
FIL_ID
);

/*==============================================================*/
/* Index: USER_FILES_FK                                         */
/*==============================================================*/
create  index USER_FILES_FK on USER_FILES (
ID
);

/*==============================================================*/
/* Index: USER_FILES2_FK                                        */
/*==============================================================*/
create  index USER_FILES2_FK on USER_FILES (
FIL_ID
);

alter table ARTIST_ALBUM
   add constraint FK_ARTIST_A_ARTIST_AL_ARTIST foreign key (ID)
      references ARTIST (ID)
      on delete restrict on update restrict;

alter table ARTIST_ALBUM
   add constraint FK_ARTIST_A_ARTIST_AL_ALBUM foreign key (ALB_ID)
      references ALBUM (ID)
      on delete restrict on update restrict;

alter table FILE
   add constraint FK_FILE_FILE_MUSI_MUSIC foreign key (MUS_ID)
      references MUSIC (ID)
      on delete restrict on update restrict;

alter table MUSIC
   add constraint FK_MUSIC_ALBUM_MUS_ALBUM foreign key (ALB_ID)
      references ALBUM (ID)
      on delete restrict on update restrict;

alter table NOTIFICATION
   add constraint FK_NOTIFICA_USER_NOTI_USER foreign key (USE_ID)
      references "USER" (ID)
      on delete restrict on update restrict;

alter table USER_FILES
   add constraint FK_USER_FIL_USER_FILE_USER foreign key (ID)
      references "USER" (ID)
      on delete restrict on update restrict;

alter table USER_FILES
   add constraint FK_USER_FIL_USER_FILE_FILE foreign key (FIL_ID)
      references FILE (ID)
      on delete restrict on update restrict;

