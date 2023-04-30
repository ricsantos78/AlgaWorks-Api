create table tb_state
(
    id_state uuid         not null
        primary key,
    nm_state varchar(255) not null
);

alter table tb_state
    owner to postgres;
