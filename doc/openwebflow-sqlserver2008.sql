if exists (select 1
            from  sysobjects
           where  id = object_id('OWF_ACTIVITY_CREATION')
            and   type = 'U')
   drop table OWF_ACTIVITY_CREATION
go

if exists (select 1
            from  sysobjects
           where  id = object_id('OWF_ACTIVITY_PERMISSION')
            and   type = 'U')
   drop table OWF_ACTIVITY_PERMISSION
go

if exists (select 1
            from  sysobjects
           where  id = object_id('OWF_DELEGATION')
            and   type = 'U')
   drop table OWF_DELEGATION
go

if exists (select 1
            from  sysobjects
           where  id = object_id('OWF_MEMBERSHIP')
            and   type = 'U')
   drop table OWF_MEMBERSHIP
go

if exists (select 1
            from  sysobjects
           where  id = object_id('OWF_NOTIFICATION')
            and   type = 'U')
   drop table OWF_NOTIFICATION
go

if exists (select 1
            from  sysobjects
           where  id = object_id('OWF_USER')
            and   type = 'U')
   drop table OWF_USER
go

/*==============================================================*/
/* Table: OWF_ACTIVITY_CREATION                                 */
/*==============================================================*/
create table OWF_ACTIVITY_CREATION (
   ID                   numeric(6,0)         identity,
   FACTORY_NAME         varchar(255)         null,
   PROCESS_DEFINITION_ID varchar(255)         null,
   PROCESS_INSTANCE_ID  varchar(255)         null,
   PROPERTIES_TEXT      varchar(2000)        null,
   constraint PK_owf_activity_creation primary key nonclustered (ID)
)
go

/*==============================================================*/
/* Table: OWF_ACTIVITY_PERMISSION                               */
/*==============================================================*/
create table OWF_ACTIVITY_PERMISSION (
   ID                   numeric(6,0)         identity,
   ACTIVITY_KEY         varchar(255)         null,
   ASSIGNED_USER        varchar(255)         null,
   GRANTED_GROUPS       varchar(255)         null,
   GRANTED_USERS        varchar(255)         null,
   PROCESS_DEFINITION_ID varchar(255)         null,
   OP_TIME              datetime             null,
   constraint PK_owf_activity_permission primary key nonclustered (ID)
)
go

/*==============================================================*/
/* Table: OWF_DELEGATION                                        */
/*==============================================================*/
create table OWF_DELEGATION (
   ID                   numeric(6,0)         identity,
   DELEGATED            varchar(255)         null,
   DELEGATE             varchar(255)         null,
   OP_TIME              datetime             null,
   constraint PK_owf_delegation primary key nonclustered (ID)
)
go

/*==============================================================*/
/* Table: OWF_MEMBERSHIP                                        */
/*==============================================================*/
create table OWF_MEMBERSHIP (
   ID                   numeric(6,0)         identity,
   GROUP_ID             varchar(255)         null,
   USER_ID              varchar(255)         null,
   constraint PK_owf_membership primary key nonclustered (ID)
)
go

/*==============================================================*/
/* Table: OWF_NOTIFICATION                                      */
/*==============================================================*/
create table OWF_NOTIFICATION (
   ID                   numeric(6,0)         identity,
   TASK_ID              varchar(255)         null,
   OP_TIME              datetime             null,
   constraint PK_owf_notification primary key nonclustered (ID)
)
go

/*==============================================================*/
/* Table: OWF_USER                                              */
/*==============================================================*/
create table OWF_USER (
   USER_ID              varchar(255)         not null,
   EMAIL                varchar(255)         null,
   NICK_NAME            varchar(255)         null,
   MOBILE_PHONE_NUMBER  varchar(255)         null,
   constraint PK_owf_user primary key nonclustered (USER_ID)
)
go
