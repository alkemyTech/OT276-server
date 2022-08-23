drop table if exists new;


create table new(

    new_id  bigint          not null auto_increment,
    name    varchar(255)    not null,
    content text    not null,
    image   varchar(255)    not null,
    category_id bigint,
    foreign key (category_id) references category(category_id),
    created_at  datetime(6)  not null,
    is_active   bit          not null,
    updated_at  datetime(6)  null,
    primary key (new_id)
) engine = InnoDB;