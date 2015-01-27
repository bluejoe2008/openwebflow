drop table if exists OWF_ACTIVITY_CREATION;

drop table if exists OWF_ACTIVITY_PERMISSION;

drop table if exists OWF_DELEGATION;

drop table if exists OWF_MEMBERSHIP;

drop table if exists OWF_NOTIFICATION;

drop table if exists OWF_USER;

/*==============================================================*/
/* Table: OWF_ACTIVITY_CREATION                                 */
/*==============================================================*/
create table OWF_ACTIVITY_CREATION
(
   ID                             numeric(6,0)                   not null AUTO_INCREMENT,
   FACTORY_NAME                   varchar(255),
   PROCESS_DEFINITION_ID          varchar(255),
   PROCESS_INSTANCE_ID            varchar(255),
   PROPERTIES_TEXT                varchar(2000),
   primary key (ID)
)
type = InnoDB;

/*==============================================================*/
/* Table: OWF_ACTIVITY_PERMISSION                               */
/*==============================================================*/
create table OWF_ACTIVITY_PERMISSION
(
   ID                             numeric(6,0)                   not null AUTO_INCREMENT,
   ACTIVITY_KEY                   varchar(255),
   ASSIGNED_USER                  varchar(255),
   GRANTED_GROUPS                 varchar(255),
   GRANTED_USERS                  varchar(255),
   PROCESS_DEFINITION_ID          varchar(255),
   OP_TIME                        datetime,
   primary key (ID)
)
type = InnoDB;

/*==============================================================*/
/* Table: OWF_DELEGATION                                        */
/*==============================================================*/
create table OWF_DELEGATION
(
   ID                             numeric(6,0)                   not null AUTO_INCREMENT,
   DELEGATED                      varchar(255),
   DELEGATE                       varchar(255),
   OP_TIME                        datetime,
   primary key (ID)
)
type = InnoDB;

/*==============================================================*/
/* Table: OWF_MEMBERSHIP                                        */
/*==============================================================*/
create table OWF_MEMBERSHIP
(
   ID                             numeric(6,0)                   not null AUTO_INCREMENT,
   GROUP_ID                       varchar(255),
   USER_ID                        varchar(255),
   primary key (ID)
)
type = InnoDB;

/*==============================================================*/
/* Table: OWF_NOTIFICATION                                      */
/*==============================================================*/
create table OWF_NOTIFICATION
(
   ID                             numeric(6,0)                   not null AUTO_INCREMENT,
   TASK_ID                        varchar(255),
   OP_TIME                        datetime,
   primary key (ID)
)
type = InnoDB;

/*==============================================================*/
/* Table: OWF_USER                                              */
/*==============================================================*/
create table OWF_USER
(
   USER_ID                        varchar(255)                   not null,
   EMAIL                          varchar(255),
   NICK_NAME                      varchar(255),
   MOBILE_PHONE_NUMBER            varchar(255),
   primary key (USER_ID)
)
type = InnoDB;
