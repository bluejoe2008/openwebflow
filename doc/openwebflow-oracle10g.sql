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


drop trigger "openwebflow"."tib_owf_activity_creation"
/

drop trigger "openwebflow"."tib_owf_activity_permission"
/

drop trigger "openwebflow"."tib_owf_delegation"
/

drop trigger "openwebflow"."tib_owf_membership"
/

drop trigger "openwebflow"."tib_owf_notification"
/

drop table "openwebflow".OWF_ACTIVITY_CREATION cascade constraints
/

drop table "openwebflow".OWF_ACTIVITY_PERMISSION cascade constraints
/

drop table "openwebflow".OWF_DELEGATION cascade constraints
/

drop table "openwebflow".OWF_MEMBERSHIP cascade constraints
/

drop table "openwebflow".OWF_NOTIFICATION cascade constraints
/

drop table "openwebflow".OWF_USER cascade constraints
/

drop user "openwebflow"
/

drop sequence S_OWF_ACTIVITY_CREATION
/

drop sequence S_OWF_ACTIVITY_PERMISSION
/

drop sequence S_OWF_DELEGATION
/

drop sequence S_OWF_MEMBERSHIP
/

drop sequence S_OWF_NOTIFICATION
/

create sequence S_OWF_ACTIVITY_CREATION
/

create sequence S_OWF_ACTIVITY_PERMISSION
/

create sequence S_OWF_DELEGATION
/

create sequence S_OWF_MEMBERSHIP
/

create sequence S_OWF_NOTIFICATION
/

/*==============================================================*/
/* User: "openwebflow"                                          */
/*==============================================================*/
create user "openwebflow" identified by ''
/

/*==============================================================*/
/* Table: OWF_ACTIVITY_CREATION                                 */
/*==============================================================*/
create table "openwebflow".OWF_ACTIVITY_CREATION  (
   ID                   NUMBER(6)                       not null,
   FACTORY_NAME         VARCHAR2(255),
   PROCESS_DEFINITION_ID VARCHAR2(255),
   PROCESS_INSTANCE_ID  VARCHAR2(255),
   PROPERTIES_TEXT      VARCHAR2(2000),
   constraint "PK_owf_activity_creation" primary key (ID)
)
/

/*==============================================================*/
/* Table: OWF_ACTIVITY_PERMISSION                               */
/*==============================================================*/
create table "openwebflow".OWF_ACTIVITY_PERMISSION  (
   ID                   NUMBER(6)                       not null,
   ACTIVITY_KEY         VARCHAR2(255),
   ASSIGNED_USER        VARCHAR2(255),
   GRANTED_GROUPS       VARCHAR2(255),
   GRANTED_USERS        VARCHAR2(255),
   PROCESS_DEFINITION_ID VARCHAR2(255),
   OP_TIME              DATE,
   constraint "PK_owf_activity_permission" primary key (ID)
)
/

/*==============================================================*/
/* Table: OWF_DELEGATION                                        */
/*==============================================================*/
create table "openwebflow".OWF_DELEGATION  (
   ID                   NUMBER(6)                       not null,
   DELEGATED            VARCHAR2(255),
   DELEGATE             VARCHAR2(255),
   OP_TIME              DATE,
   constraint "PK_owf_delegation" primary key (ID)
)
/

/*==============================================================*/
/* Table: OWF_MEMBERSHIP                                        */
/*==============================================================*/
create table "openwebflow".OWF_MEMBERSHIP  (
   ID                   NUMBER(6)                       not null,
   GROUP_ID             VARCHAR2(255),
   USER_ID              VARCHAR2(255),
   constraint "PK_owf_membership" primary key (ID)
)
/

/*==============================================================*/
/* Table: OWF_NOTIFICATION                                      */
/*==============================================================*/
create table "openwebflow".OWF_NOTIFICATION  (
   ID                   NUMBER(6)                       not null,
   TASK_ID              VARCHAR2(255),
   OP_TIME              DATE,
   constraint "PK_owf_notification" primary key (ID)
)
/

/*==============================================================*/
/* Table: OWF_USER                                              */
/*==============================================================*/
create table "openwebflow".OWF_USER  (
   USER_ID              VARCHAR2(255)                   not null,
   EMAIL                VARCHAR2(255),
   NICK_NAME            VARCHAR2(255),
   MOBILE_PHONE_NUMBER  VARCHAR2(255),
   constraint "PK_owf_user" primary key (USER_ID)
)
/


create trigger "openwebflow"."tib_owf_activity_creation" before insert
on "openwebflow".OWF_ACTIVITY_CREATION for each row
declare
    integrity_error  exception;
    errno            integer;
    errmsg           char(200);
    dummy            integer;
    found            boolean;

begin
    --  Column "ID" uses sequence S_OWF_ACTIVITY_CREATION
    select S_OWF_ACTIVITY_CREATION.NEXTVAL INTO :new.ID from dual;

--  Errors handling
exception
    when integrity_error then
       raise_application_error(errno, errmsg);
end;
/


create trigger "openwebflow"."tib_owf_activity_permission" before insert
on "openwebflow".OWF_ACTIVITY_PERMISSION for each row
declare
    integrity_error  exception;
    errno            integer;
    errmsg           char(200);
    dummy            integer;
    found            boolean;

begin
    --  Column "ID" uses sequence S_OWF_ACTIVITY_PERMISSION
    select S_OWF_ACTIVITY_PERMISSION.NEXTVAL INTO :new.ID from dual;

--  Errors handling
exception
    when integrity_error then
       raise_application_error(errno, errmsg);
end;
/


create trigger "openwebflow"."tib_owf_delegation" before insert
on "openwebflow".OWF_DELEGATION for each row
declare
    integrity_error  exception;
    errno            integer;
    errmsg           char(200);
    dummy            integer;
    found            boolean;

begin
    --  Column "ID" uses sequence S_OWF_DELEGATION
    select S_OWF_DELEGATION.NEXTVAL INTO :new.ID from dual;

--  Errors handling
exception
    when integrity_error then
       raise_application_error(errno, errmsg);
end;
/


create trigger "openwebflow"."tib_owf_membership" before insert
on "openwebflow".OWF_MEMBERSHIP for each row
declare
    integrity_error  exception;
    errno            integer;
    errmsg           char(200);
    dummy            integer;
    found            boolean;

begin
    --  Column "ID" uses sequence S_OWF_MEMBERSHIP
    select S_OWF_MEMBERSHIP.NEXTVAL INTO :new.ID from dual;

--  Errors handling
exception
    when integrity_error then
       raise_application_error(errno, errmsg);
end;
/


create trigger "openwebflow"."tib_owf_notification" before insert
on "openwebflow".OWF_NOTIFICATION for each row
declare
    integrity_error  exception;
    errno            integer;
    errmsg           char(200);
    dummy            integer;
    found            boolean;

begin
    --  Column "ID" uses sequence S_OWF_NOTIFICATION
    select S_OWF_NOTIFICATION.NEXTVAL INTO :new.ID from dual;

--  Errors handling
exception
    when integrity_error then
       raise_application_error(errno, errmsg);
end;
/
