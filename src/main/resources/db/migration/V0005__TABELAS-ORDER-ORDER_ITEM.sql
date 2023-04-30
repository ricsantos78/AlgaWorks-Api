create table tb_order
(
    id_order             uuid           not null
        primary key,
    address_cep          varchar(255),
    address_complement   varchar(255),
    address_district     varchar(255),
    address_number       varchar(255),
    address_public_place varchar(255),
    vl_amount            numeric(19, 2) not null,
    dt_cancel            timestamp,
    dt_confirmation      timestamp,
    dt_criation          timestamp      not null,
    dt_delivery          timestamp,
    vl_freightrate       numeric(19, 2) not null,
    nm_status            integer,
    vl_subtotal          numeric(19, 2) not null,
    address_city_id      uuid
        constraint fk_order_city
            references tb_city,
    id_payment           uuid           not null
        constraint fk_order_payment
            references tb_payment,
    id_restaurant        uuid           not null
        constraint fk_order_restaurant
            references tb_restaurant,
    id_user              uuid           not null
        constraint fk_order_user
            references tb_user
);

alter table tb_order
    owner to postgres;

create table tb_order_item
(
    id_order_item  uuid           not null
        primary key,
    vl_observation varchar(255),
    vl_quantity    integer        not null,
    vl_total       numeric(19, 2) not null,
    vl_unitary     numeric(19, 2) not null,
    id_order       uuid           not null
        constraint fk_order_item_order
            references tb_order
);

alter table tb_order_item
    owner to postgres;
