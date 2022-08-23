drop table if exists member;

create table member
    (
    member_id     bigint       not null auto_increment,
    name          varchar(255) not null,
    facebook_url  varchar(255),
    instagram_url varchar(255),
    linkedin_url  varchar(255),
    image         varchar(255) not null,
    description   varchar(255),
    created_at    datetime(6) not null,
    is_active     bit          not null,
    updated_at    datetime(6) null,
    primary key (member_id)
    )engine = InnoDB;