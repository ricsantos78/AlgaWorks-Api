-- tb_order
alter table tb_order
rename column address_city_id to cd_city;

-- tb_order
alter table tb_order
alter column cd_city type bigint using (cd_city::text::bigint);

-- tb_restaurant
alter table tb_restaurant
rename column address_city_id to cd_city;

alter table tb_restaurant
alter column cd_city type bigint using (cd_city::text::bigint);

