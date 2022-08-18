drop table if exists category;

cretae table category
{
    category_id bigint       not null auto_increment,
    name        varchar(255) not null,
    description varchar(255) nullable,
    image       varchar(255) nullable,
    created_at  datetime(6)  not null,
    is_active   bit          not null,
    updated_at  datetime(6)  null
    primary key (category_id)
} engine = InnoDB;