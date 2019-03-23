create database customerSupport default character set utf8
default collate utf8_unicode_ci ;

--- table userPricipal ----
create table  userPrincipal (
    user_id int unsigned not null auto_increment primary key ,
    username varchar(35) not null ,
    password varchar(35) not null ,
    unique key userPrincipal_username (username)
) engine=InnoDB default charset=utf8;

--- table ticket ----
create table ticket (
ticket_id int unsigned  not null auto_increment primary key ,
user_id int unsigned not null ,
subject varchar(255) not null ,
body text not null ,
date_created datetime not null ,
constraint ticket_userId foreign key (user_id)
references userPrincipal (user_id) on delete cascade
) engine=InnoDB default charset=utf8 ;

--- alter table ticket fulltext index ----
alter table ticket add fulltext index ticket_search (subject,body) ;

--- table ticketComment ---
create table ticketComment (
comment_id int unsigned not null auto_increment primary key ,
ticket_id int unsigned not null ,
user_id int unsigned not null ,
body text not null ,
date_created datetime not null ,
constraint ticketComment_userId foreign key (user_id)
references userPrincipal (user_id) on delete cascade ,
constraint ticketComment_ticketId  foreign key (ticket_id )
references ticket (ticket_id) on delete cascade
) engine=InnoDB default charset=utf8;

--alter table ticletComment add Fulltext ---
alter table ticketComment add fulltext index ticletComment_search (body) ;

--table attachment ---
create table attachment (
attachment_id int unsigned not null auto_increment primary key ,
attachment_name varchar(255) not null ,
mimContent_type varchar(255) not null ,
contents longblob not null
) engine=InnoDB default charset=utf8;

--- table ticket_attachment ---
create table ticket_attachment (
    sortkey smallint not null   ,
    ticket_id int unsigned not null ,
    attachment_id int unsigned not null ,
    constraint ticket_attachment_ticket foreign key (ticket_id )
    references ticket (ticket_id) on delete cascade ,

    constraint ticket_attachment_attachment foreign key (attachment_id)
    references attachment (attachment_id) on delete cascade ,
    index ticket_orderedAttachments (ticket_id,sortkey,attachment_id)
) engine=InnoDB default charset=utf8;

--table ticketComment_attachment ---
create table ticketComment_attachment (
    sortkey smallint not null   ,
    comment_id int unsigned not null ,
    attachment_id int unsigned not null ,
    constraint ticketComment_attachment_comment foreign key (comment_id)
    references ticketComment (comment_id) on delete cascade ,
    constraint ticketComment_attachment_attachment foreign key (attachment_id)
    references attachment (attachment_id) on delete cascade ,
    index ticketComment_orderedAttachments (comment_id , sortkey ,attachment_id)
) engine=InnoDB default charset=utf8;

