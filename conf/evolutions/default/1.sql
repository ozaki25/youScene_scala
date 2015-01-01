# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table blogs (
  id                        bigint not null AUTO_INCREMENT,
  title                     varchar(255),
  body                      varchar(255),
  created_date              timestamp DEFAULT CURRENT_TIMESTAMP,
  updated_date              timestamp DEFAULT CURRENT_TIMESTAMP,
  constraint pk_blogs primary key (id))
;

create sequence blogs_seq;



# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists blogs;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists blogs_seq;

