-- auto-generated definition
create table tb_group
(
    id_group uuid         not null
        primary key,
    nm_group varchar(255) not null
);

alter table tb_group
    owner to postgres;

-- auto-generated definition
create table tb_permission
(
    id_permission  uuid         not null
        primary key,
    nm_description varchar(255),
    nm_permisssion varchar(255) not null
);

create table tb_group_permission
(
    id_group      uuid not null
        constraint fk_group
            references tb_group,
    id_permission uuid not null
        constraint fk_permission
            references tb_permission
);

alter table tb_group_permission
    owner to postgres;

-- auto-generated definition
create table tb_payment
(
    id_payment uuid         not null
        primary key,
    nm_payment varchar(255) not null
);

alter table tb_payment
    owner to postgres;

create table tb_restaurant
(
    id_restaurant        uuid           not null
        primary key,
    address_cep          varchar(255),
    address_complement   varchar(255),
    address_district     varchar(255),
    address_number       varchar(255),
    address_public_place varchar(255),
    vl_freight           numeric(19, 2) not null,
    nm_restaurant        varchar(255)   not null,
    dt_registration      timestamp      not null,
    dt_update            timestamp      not null,
    address_city_id      uuid
        constraint fk_restaurant_city
            references tb_city,
    id_kitchen           uuid           not null
        constraint fk_restaurant_kitchen
            references tb_kitchen
);

alter table tb_restaurant
    owner to postgres;

create table tb_product
(
    id_product     uuid           not null
        primary key,
    nm_description varchar(255)   not null,
    nm_product     varchar(255)   not null,
    vl_price       numeric(19, 2) not null,
    vl_status      boolean        not null,
    id_restaurant  uuid           not null
        constraint fk_product_restaurant
            references tb_restaurant
);

alter table tb_product
    owner to postgres;

-- auto-generated definition
create table tb_restaurant_payment
(
    id_restaurant uuid not null
        constraint fk_restaurant
            references tb_restaurant,
    id_payment    uuid not null
        constraint fk_payment
            references tb_payment
);

alter table tb_restaurant_payment
    owner to postgres;

-- auto-generated definition
create table tb_user
(
    id_user         uuid         not null
        primary key,
    ds_email        varchar(255) not null,
    nm_user         varchar(255) not null,
    ds_password     varchar(255) not null,
    dt_registration timestamp    not null
);

alter table tb_user
    owner to postgres;

-- auto-generated definition
create table tb_user_group
(
    id_user  uuid not null
        constraint fk_user
            references tb_user,
    id_group uuid not null
        constraint fk_group
            references tb_group
);

alter table tb_user_group
    owner to postgres;