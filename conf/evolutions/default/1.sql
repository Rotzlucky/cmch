# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table character_appearances (
  id                            bigint not null,
  description                   varchar(255),
  character_id                  bigint,
  issue_id                      bigint,
  created_at                    timestamp,
  modified_at                   timestamp,
  constraint pk_character_appearances primary key (id)
);
create sequence character_appearances_seq;

create table characters (
  id                            bigint not null,
  character_name                varchar(255),
  character_real_name           varchar(255),
  image_name                    varchar(255),
  created_at                    timestamp,
  modified_at                   timestamp,
  constraint uq_characters_character_name_character_real_name unique (character_name,character_real_name),
  constraint pk_characters primary key (id)
);
create sequence characters_seq;

create table characters_teams (
  characters_id                 bigint not null,
  teams_id                      bigint not null,
  constraint pk_characters_teams primary key (characters_id,teams_id)
);

create table issues (
  id                            bigint not null,
  issue_name                    varchar(255),
  issue_number                  integer,
  issue_number_suffix           varchar(255),
  publish_month                 varchar(9),
  publish_year                  integer,
  title_id                      bigint,
  created_at                    timestamp,
  modified_at                   timestamp,
  constraint ck_issues_publish_month check (publish_month in ('JANUARY','FEBRUARY','MARCH','APRIL','MAY','JUNE','JULY','AUGUST','SEPTEMBER','OCTOBER','NOVEMBER','DECEMBER')),
  constraint uq_issues_title_id_issue_number_issue_number_suffix unique (title_id,issue_number,issue_number_suffix),
  constraint pk_issues primary key (id)
);
create sequence issues_seq;

create table issues_teams (
  issues_id                     bigint not null,
  teams_id                      bigint not null,
  constraint pk_issues_teams primary key (issues_id,teams_id)
);

create table orders (
  id                            bigint not null,
  order_number                  integer,
  order_type                    varchar(9),
  issue_id                      bigint,
  created_at                    timestamp,
  modified_at                   timestamp,
  constraint ck_orders_order_type check (order_type in ('MAIN','CORE','ESSENTIAL','EXTENDED','WW2','ROMANCE','HYBOREAN','ULTIMATE')),
  constraint uq_orders_order_type_order_number unique (order_type,order_number),
  constraint pk_orders primary key (id)
);
create sequence orders_seq;

create table teams (
  id                            bigint not null,
  team_name                     varchar(255),
  created_at                    timestamp,
  modified_at                   timestamp,
  constraint uq_teams_team_name unique (team_name),
  constraint pk_teams primary key (id)
);
create sequence teams_seq;

create table titles (
  id                            bigint not null,
  title_name                    varchar(255),
  title_number                  varchar(255),
  created_at                    timestamp,
  modified_at                   timestamp,
  constraint uq_titles_title_name_title_number unique (title_name,title_number),
  constraint pk_titles primary key (id)
);
create sequence titles_seq;

alter table character_appearances add constraint fk_character_appearances_character_id foreign key (character_id) references characters (id) on delete restrict on update restrict;
create index ix_character_appearances_character_id on character_appearances (character_id);

alter table character_appearances add constraint fk_character_appearances_issue_id foreign key (issue_id) references issues (id) on delete restrict on update restrict;
create index ix_character_appearances_issue_id on character_appearances (issue_id);

alter table characters_teams add constraint fk_characters_teams_characters foreign key (characters_id) references characters (id) on delete restrict on update restrict;
create index ix_characters_teams_characters on characters_teams (characters_id);

alter table characters_teams add constraint fk_characters_teams_teams foreign key (teams_id) references teams (id) on delete restrict on update restrict;
create index ix_characters_teams_teams on characters_teams (teams_id);

alter table issues add constraint fk_issues_title_id foreign key (title_id) references titles (id) on delete restrict on update restrict;
create index ix_issues_title_id on issues (title_id);

alter table issues_teams add constraint fk_issues_teams_issues foreign key (issues_id) references issues (id) on delete restrict on update restrict;
create index ix_issues_teams_issues on issues_teams (issues_id);

alter table issues_teams add constraint fk_issues_teams_teams foreign key (teams_id) references teams (id) on delete restrict on update restrict;
create index ix_issues_teams_teams on issues_teams (teams_id);

alter table orders add constraint fk_orders_issue_id foreign key (issue_id) references issues (id) on delete restrict on update restrict;
create index ix_orders_issue_id on orders (issue_id);


# --- !Downs

alter table character_appearances drop constraint if exists fk_character_appearances_character_id;
drop index if exists ix_character_appearances_character_id;

alter table character_appearances drop constraint if exists fk_character_appearances_issue_id;
drop index if exists ix_character_appearances_issue_id;

alter table characters_teams drop constraint if exists fk_characters_teams_characters;
drop index if exists ix_characters_teams_characters;

alter table characters_teams drop constraint if exists fk_characters_teams_teams;
drop index if exists ix_characters_teams_teams;

alter table issues drop constraint if exists fk_issues_title_id;
drop index if exists ix_issues_title_id;

alter table issues_teams drop constraint if exists fk_issues_teams_issues;
drop index if exists ix_issues_teams_issues;

alter table issues_teams drop constraint if exists fk_issues_teams_teams;
drop index if exists ix_issues_teams_teams;

alter table orders drop constraint if exists fk_orders_issue_id;
drop index if exists ix_orders_issue_id;

drop table if exists character_appearances;
drop sequence if exists character_appearances_seq;

drop table if exists characters;
drop sequence if exists characters_seq;

drop table if exists characters_teams;

drop table if exists issues;
drop sequence if exists issues_seq;

drop table if exists issues_teams;

drop table if exists orders;
drop sequence if exists orders_seq;

drop table if exists teams;
drop sequence if exists teams_seq;

drop table if exists titles;
drop sequence if exists titles_seq;

