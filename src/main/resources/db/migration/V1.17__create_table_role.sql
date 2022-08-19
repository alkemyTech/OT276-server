drop table if exists role;

create table role
(
    role_id bigint                   not null auto_increment,
    name                varchar(10) not null,
    description         varchar(50) not null,
    created_at          datetime(6)  not null,
    is_active           bit          not null,
    updated_at          datetime(6)  null,
    primary key (role_id)
) engine = InnoDB;
