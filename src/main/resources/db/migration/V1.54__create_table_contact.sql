drop table if exists contact;
create table contact
(
    contact_id bigint       not null auto_increment,
    name       varchar(255) not null,
    phone integer not null,
    email varchar(255) not null,
    message text not null,
    created_at datetime(6)  not null,
    is_active bit not null,
    updated_at datetime(6)  null,
    deleted_at  datetime(6)          null,
    primary key (contact_id)
) engine = InnoDB;