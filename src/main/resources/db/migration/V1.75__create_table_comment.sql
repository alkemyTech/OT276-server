drop table if exists comment;

create table comment
(
    comment_id     bigint        not null  auto_increment,
    user_id        bigint        not null,
    new_id         bigint        not null,
    body           text          not null,
    is_active      bit           not null,
    created_at     datetime(6)   not null,
    updated_at     datetime(6)   null,
    foreign key (user_id) references user(user_id),
    foreign key (new_id) references new(new_id),
    primary key (comment_id)
) engine = InnoDB;