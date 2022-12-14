drop table if exists activity;

create table activity
(
    activity_id bigint        not null  auto_increment,
    name           varchar(255)  not null,
    content        text         not null,
    image          varchar(255),
    is_active      bit           not null,
    created_at     datetime(6)   not null,
    updated_at     datetime(6)   null,
    primary key (activity_id)
) engine = InnoDB;


