drop table if exists user;

create table user
(
    user_id bigint                  not null auto_increment,
    first_Name       varchar(20)   not null,
    last_Name       varchar(20) not null,
    email       varchar(120)  not null unique,
    password       varchar(60) not null,
    photo       varchar(255) not null,
    created_at datetime(6)  not null,
    is_active  bit          not null,
    updated_at datetime(6)  null,
    end_date   datetime(6)  null,
    role_id       bigint not null,
    foreign key (role_id) references role(role_id),
    primary key (user_id)
) engine = InnoDB;

