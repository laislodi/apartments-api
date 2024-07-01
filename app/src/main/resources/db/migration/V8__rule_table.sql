
create table "roles" (
    id integer NOT NULL,
    role text NOT NULL,
    CONSTRAINT "rule_pk" primary key ("id")
);

insert into roles values (1, 'USER');
insert into roles values (2, 'ADMIN');
