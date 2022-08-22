drop table if exists slide;

create table slide
(
    slide_id        bigint         not null auto_increment,
    image_url       varchar(255)   not null,
    text            text           not null,
    slide_order     int            not null,
    organization_id bigint         not null,
    is_active       bit            not null,
    created_at      datetime(6)    not null,
    updated_at      datetime(6)    null,
    primary key (slide_id),
    constraint fk_organization_id foreign key (organization_id)
references organization (organization_id)
) engine = InnoDB
