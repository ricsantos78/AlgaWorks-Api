create table tb_city
(
    id_city  uuid         not null
        primary key,
    nm_city  varchar(255) not null,
    id_state uuid
        constraint fk_city_state
            references tb_state
);

alter table tb_city
    owner to postgres;