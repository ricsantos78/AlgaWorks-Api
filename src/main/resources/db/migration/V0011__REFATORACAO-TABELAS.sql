-- Altera a tabela tb_group_permission para substituir os campos id_group e id_permission por cd_group e cd_permission
alter table tb_group_permission
rename column id_group to cd_group;

alter table tb_group_permission
rename column id_permission to cd_permission;

-- Altera o tipo dos campos cd_group e cd_permission para bigint
alter table tb_group_permission
alter column cd_group type bigint using (cd_group::text::bigint),
alter column cd_permission type bigint using (cd_permission::text::bigint);

-- tb_city
alter table tb_city
rename column id_state to cd_state;

alter table tb_city
alter column cd_state type bigint using (cd_state::text::bigint); 

-- tb_order
alter table tb_order
rename column id_payment to cd_payment;
alter table tb_order
rename column id_restaurant to cd_restaurant;
alter table tb_order
rename column id_user to cd_user;

-- tb_order
alter table tb_order
alter column cd_payment type bigint using (cd_payment::text::bigint),
alter column cd_restaurant type bigint using (cd_restaurant::text::bigint),
alter column cd_user type bigint using (cd_user::text::bigint);

-- tb_order_item
alter table tb_order_item
rename column id_order to cd_order;

alter table tb_order_item
alter column cd_order type bigint using (cd_order::text::bigint);

-- tb_product
alter table tb_product
rename column id_restaurant to cd_restaurant;

alter table tb_product
alter column cd_restaurant type bigint using (cd_restaurant::text::bigint);

-- tb_restaurant
alter table tb_restaurant
rename column id_kitchen to cd_kitchen;

alter table tb_restaurant
alter column cd_kitchen type bigint using (cd_kitchen::text::bigint);

-- tb_restaurant_payment
alter table tb_restaurant_payment
rename column id_payment to cd_payment;

alter table tb_restaurant_payment
rename column id_restaurant to cd_restaurant;

alter table tb_restaurant_payment
alter column cd_payment type bigint using (cd_payment::text::bigint),
alter column cd_restaurant type bigint using (cd_restaurant::text::bigint);

-- tb_restaurant_payment
alter table tb_user_group
rename column id_user to cd_user;

alter table tb_user_group
rename column id_group to cd_group;

alter table tb_user_group
alter column cd_user type bigint using (cd_user::text::bigint),
alter column cd_group type bigint using (cd_group::text::bigint);

