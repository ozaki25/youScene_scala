# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table blogs (
  id                        bigint not null AUTO_INCREMENT,
  title                     varchar(255),
  body                      varchar(255),
  author_id                 bigint,
  created_date              timestamp DEFAULT CURRENT_TIMESTAMP,
  updated_date              timestamp DEFAULT CURRENT_TIMESTAMP,
  constraint pk_blogs primary key (id))
;

create table users (
  id                        bigint not null AUTO_INCREMEN,
  username                  varchar(255),
  name                      varchar(255),
  section_name              varchar(255),
  constraint pk_users primary key (id))
;


create sequence blogs_seq;

create sequence users_seq;

alter table blogs add constraint fk_blogs_author_1 foreign key (author_id) references users (id) on delete restrict on update restrict;
create index ix_blogs_author_1 on blogs (author_id);

# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists blogs;

drop table if exists users;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists blogs_seq;

drop sequence if exists users_seq;
