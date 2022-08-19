drop table if exists testimonial;

create table testimonial
(
    testimonial_id bigint        not null  auto_increment,
    name           varchar(255)  not null,
    image          varchar(255),
    content        text,
    is_active      bit           not null,
    created_at     datetime(6)   not null,
    updated_at     datetime(6)   null,
    primary key (testimonial_id)
) engine = InnoDB;