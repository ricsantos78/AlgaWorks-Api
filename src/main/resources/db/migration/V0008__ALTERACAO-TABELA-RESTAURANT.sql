alter table tb_restaurant drop column ativo;

alter table tb_restaurant add column ativo boolean not null default true;