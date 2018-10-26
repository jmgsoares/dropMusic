create sequence S_ALBUM;

create sequence S_ARTIST;

create sequence S_FILE;

create sequence S_MUSIC;

create sequence S_USER;

/*==============================================================*/
/* Table: ALBUM                                                 */
/*==============================================================*/
create table ALBUM (
   ID                   SERIAL not null,
   NAME                 VARCHAR(64)          not null,
   DESCRIPTION          VARCHAR(1024)        not null,
   SCORE                FLOAT8               null
);

alter table ALBUM
   add constraint PK_ALBUM primary key (ID);

/*==============================================================*/
/* Table: ARTIST                                                */
/*==============================================================*/
create table ARTIST (
   ID                   SERIAL not null,
   NAME                 VARCHAR(32)          not null
);

alter table ARTIST
   add constraint PK_ARTIST primary key (ID);

/*==============================================================*/
/* Table: ARTIST_ALBUM                                          */
/*==============================================================*/
create table ARTIST_ALBUM (
   ID                   INTEGER           not null,
   ALB_ID               INTEGER           not null
);

alter table ARTIST_ALBUM
   add constraint PK_ARTIST_ALBUM primary key (ID, ALB_ID);

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
   ID                   SERIAL not null,
   MUS_ID               INTEGER           not null,
   BIN                  INT2                 not null
);

alter table FILE
   add constraint PK_FILE primary key (ID);

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
   ID                   SERIAL not null,
   ALB_ID               INTEGER           not null,
   NAME                 VARCHAR(32)          not null
);

alter table MUSIC
   add constraint PK_MUSIC primary key (ID);

/*==============================================================*/
/* Index: ALBUM_MUSIC_FK                                        */
/*==============================================================*/
create  index ALBUM_MUSIC_FK on MUSIC (
ALB_ID
);

/*==============================================================*/
/* Table: "USER"                                                */
/*==============================================================*/
create table "USER" (
   ID                   SERIAL not null,
   NAME                 VARCHAR(32)          not null,
   PASSWORD             VARCHAR(32)          not null,
   EDITOR               BOOLEAN                 not null
);

alter table "USER"
   add constraint PK_USER primary key (ID);

/*==============================================================*/
/* Table: USER_FILES                                            */
/*==============================================================*/
create table USER_FILES (
   ID                   INTEGER           not null,
   FIL_ID               INTEGER           not null
);

alter table USER_FILES
   add constraint PK_USER_FILES primary key (ID, FIL_ID);

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

alter table USER_FILES
   add constraint FK_USER_FIL_USER_FILE_USER foreign key (ID)
      references "USER" (ID)
      on delete restrict on update restrict;

alter table USER_FILES
   add constraint FK_USER_FIL_USER_FILE_FILE foreign key (FIL_ID)
      references FILE (ID)
      on delete restrict on update restrict;

