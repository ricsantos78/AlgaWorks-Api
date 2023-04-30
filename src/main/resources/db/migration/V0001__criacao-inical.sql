create table tb_kitchen
(
    id_kitchen uuid         not null
        primary key,
    nm_kitchen varchar(255) not null
        constraint uk_mem034lomuccpijn0hkg1vigd
            unique
);

alter table tb_kitchen
    owner to postgres;