create table "subject" (
                        id              int8        not null unique,
                        name            text        not null,
                        description     text,
                        author_id       int8        not null,
                        creation_date   timestamp,
                        deleted         boolean     not null,

                        constraint subject_pk primary key (id)
) with (oids=false);

create sequence "subject_id_seq" start 1;

alter table "subject" add constraint "subject_fk_user" foreign key (author_id) references "users"(id);

create table "message" (
                        id              int8        not null unique,
                        text_message    text,
                        subject_id      int8        not null,
                        author_id       int8        not null,
                        creation_date   timestamp,
                        deleted         boolean     not null,

                       constraint message_pk primary key (id)
)with (oids=false);

create sequence "message_id_seq" start 1;

alter table "message" add constraint "message_fk_user" foreign key (author_id) references "users"(id);
alter table "message" add constraint "message_fk_subject" foreign key (subject_id) references "subject"(id)