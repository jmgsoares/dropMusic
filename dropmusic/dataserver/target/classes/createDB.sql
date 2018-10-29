/*==============================================================*/
/* DBMS name:      PostgreSQL 8                                 */
/* Created on:     27/10/2018 14:59:25                          */
/*==============================================================*/


drop index if exists ALBUM_PK;

drop table if exists ALBUM cascade;

drop index if exists ARTIST_PK;

drop table if exists ARTIST cascade;

drop index if exists ARTIST_ALBUM2_FK;

drop index if exists ARTIST_ALBUM_FK;

drop index if exists ARTIST_ALBUM_PK;

drop table if exists ARTIST_ALBUM cascade;

drop index if exists UPLOAD_MUSIC_FK;

drop index if exists UPLOAD_PK;

drop table if exists UPLOAD cascade;

drop index if exists ALBUM_MUSIC_FK;

drop index if exists MUSIC_PK;

drop table if exists MUSIC cascade;

drop index if exists ACCOUNT_NOTIFICATION_FK;

drop index if exists NOTIFICATION_PK;

drop table if exists NOTIFICATION cascade;

drop index if exists ALBUM_REVIEW_FK;

drop index if exists review_PK;

drop table if exists review cascade;

drop index if exists ACCOUNT_PK;

drop table if exists ACCOUNT cascade;

drop index if exists ACCOUNT_UPLOADS2_FK;

drop index if exists ACCOUNT_UPLOADS_FK;

drop index if exists ACCOUNT_UPLOADS_PK;

drop table if exists ACCOUNT_UPLOADS cascade;

drop sequence if exists s_album;

drop sequence  if exists s_artist;

drop sequence if exists s_UPLOAD;

drop sequence if exists s_music;

drop sequence if exists s_ACCOUNT;

/*==============================================================*/
/* Table: ALBUM                                                 */
/*==============================================================*/
create table ALBUM (
   ID                   SERIAL               not null,
   NAME                 VARCHAR(64)          not null,
   DESCRIPTION          VARCHAR(1024)        not null,
   SCORE                FLOAT8               null,
   UNIQUE (NAME, DESCRIPTION),
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
   NAME                 VARCHAR(32)          unique not null,
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
/* Table: UPLOAD                                                  */
/*==============================================================*/
create table UPLOAD (
   ID                   SERIAL               not null,
   MUS_ID               INT4                 not null,
   BIN                  bytea                 null,
   IP_ADDRESS                 VARCHAR(1024)        null,
   NAME                 VARCHAR(1024)        not null,
   constraint PK_UPLOAD primary key (ID)
);

/*==============================================================*/
/* Index: UPLOAD_PK                                               */
/*==============================================================*/
create unique index UPLOAD_PK on UPLOAD (
   ID
);

/*==============================================================*/
/* Index: UPLOAD_MUSIC_FK                                         */
/*==============================================================*/
create  index UPLOAD_MUSIC_FK on UPLOAD (
   MUS_ID
);

/*==============================================================*/
/* Table: MUSIC                                                 */
/*==============================================================*/
create table MUSIC (
   ID                   SERIAL               not null,
   ALB_ID               INT4                 not null,
   NAME                 VARCHAR(32)          not null,
   UNIQUE (ALB_ID, NAME),
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
/* Index: ACCOUNT_NOTIFICATION_FK                                  */
/*==============================================================*/
create  index ACCOUNT_NOTIFICATION_FK on NOTIFICATION (
   USE_ID
);

/*==============================================================*/
/* Table: review                                               */
/*==============================================================*/
create table review (
   ID                   SERIAL               not null,
   ALB_ID               INT4                 not null,
   TEXT                 VARCHAR(1024)        not null,
   SCORE                FLOAT8               not null DEFAULT 0,
   constraint PK_review primary key (ID)
);

/*==============================================================*/
/* Index: review_PK                                            */
/*==============================================================*/
create unique index review_PK on review (
   ID
);

/*==============================================================*/
/* Index: ALBUM_REVIEW_FK                                       */
/*==============================================================*/
create  index ALBUM_REVIEW_FK on review (
   ALB_ID
);

/*==============================================================*/
/* Table: ACCOUNT                                                */
/*==============================================================*/
create table ACCOUNT (
   ID                   SERIAL               not null,
   NAME                 VARCHAR(32)          unique not null,
   PASSWORD             VARCHAR(32)          not null,
   EDITOR               BOOL                 not null default false,
   constraint PK_ACCOUNT primary key (ID)
);

/*==============================================================*/
/* Index: ACCOUNT_PK                                               */
/*==============================================================*/
create unique index ACCOUNT_PK on ACCOUNT (
   ID
);

/*==============================================================*/
/* Table: ACCOUNT_UPLOADS                                            */
/*==============================================================*/
create table ACCOUNT_UPLOADS (
   ID                   INT4                 not null,
   FIL_ID               INT4                 not null,
   constraint PK_ACCOUNT_UPLOADS primary key (ID, FIL_ID)
);

/*==============================================================*/
/* Index: ACCOUNT_UPLOADS_PK                                         */
/*==============================================================*/
create unique index ACCOUNT_UPLOADS_PK on ACCOUNT_UPLOADS (
   ID,
   FIL_ID
);

/*==============================================================*/
/* Index: ACCOUNT_UPLOADS_FK                                         */
/*==============================================================*/
create  index ACCOUNT_UPLOADS_FK on ACCOUNT_UPLOADS (
   ID
);

/*==============================================================*/
/* Index: ACCOUNT_UPLOADS2_FK                                        */
/*==============================================================*/
create  index ACCOUNT_UPLOADS2_FK on ACCOUNT_UPLOADS (
   FIL_ID
);

alter table ARTIST_ALBUM
   add constraint FK_ARTIST_A_ARTIST_AL_ARTIST foreign key (ID)
references ARTIST (ID)
on delete cascade on update restrict;

alter table ARTIST_ALBUM
   add constraint FK_ARTIST_A_ARTIST_AL_ALBUM foreign key (ALB_ID)
references ALBUM (ID)
on delete cascade on update restrict;

alter table UPLOAD
   add constraint FK_UPLOAD_UPLOAD_MUSI_MUSIC foreign key (MUS_ID)
references MUSIC (ID)
on delete cascade on update restrict;

alter table MUSIC
   add constraint FK_MUSIC_ALBUM_MUS_ALBUM foreign key (ALB_ID)
references ALBUM (ID)
on delete cascade on update restrict;

alter table NOTIFICATION
   add constraint FK_NOTIFICA_ACCOUNT_NOTI_ACCOUNT foreign key (USE_ID)
references ACCOUNT (ID)
on delete cascade on update restrict;

alter table review
   add constraint FK_review_ALBUM_REV_ALBUM foreign key (ALB_ID)
references ALBUM (ID)
on delete cascade on update restrict;

alter table ACCOUNT_UPLOADS
   add constraint FK_ACCOUNT_FIL_ACCOUNT_UPLOAD_ACCOUNT foreign key (ID)
references ACCOUNT (ID)
on delete cascade on update restrict;

alter table ACCOUNT_UPLOADS
   add constraint FK_ACCOUNT_FIL_ACCOUNT_UPLOAD_UPLOAD foreign key (FIL_ID)
references UPLOAD (ID)
on delete cascade on update restrict;







DROP FUNCTION IF EXISTS add_album(artist_id SERIAL, name VARCHAR(64), description VARCHAR(1024));
CREATE FUNCTION add_album(artist_id INT4, asname VARCHAR(64), descriptionz VARCHAR(1024)) RETURNS album AS $$
DECLARE
   new_album album %ROWTYPE;
   t1 text;
   t2 text;
   c1 float;

BEGIN
   SELECT * FROM ALBUM A WHERE A.NAME LIKE ('%' || asname || '%')
      INTO t1, t2, c1;
   INSERT INTO album(name, description) VALUES(asname, descriptionz) RETURNING * INTO new_album;
   INSERT INTO artist_album(id, alb_id) VALUES(artist_id, new_album.id);
   RETURN new_album;
END;
$$ LANGUAGE plpgsql STRICT;