drop table if exists organization;

create table organization
(
    organization_id bigint         not null auto_increment,
    name            varchar(255)   not null,
    image           varchar(255)   not null,
    address         varchar(255)   null,
    phone           integer        null,
    email           varchar(255)   not null,
    welcomeText     text           not null,
    aboutUsText     text           null,
    is_active       bit            not null,
    created_at      datetime(6)    not null,
    updated_at      datetime(6)    null,
    primary key (organization_id)
) engine = InnoDB;