-- Tabela tb_city
alter table tb_city
drop constraint if exists tb_city_pkey CASCADE;

alter table tb_city
add column cd_city bigint not null
    constraint pk_tb_city_cd_city
    primary key;

-- Tabela tb_group
alter table tb_group
drop constraint if exists tb_group_pkey CASCADE;

alter table tb_group
add column cd_group bigint not null
    constraint pk_tb_group_cd_group
    primary key;
	
-- Tabela tb_kitchen
alter table tb_kitchen
drop constraint if exists tb_kitchen_pkey CASCADE;

alter table tb_kitchen
add column cd_kitchen bigint not null
    constraint pk_tb_kitchen_cd_kitchen
    primary key;

-- Tabela tb_order
alter table tb_order
drop constraint if exists tb_order_pkey CASCADE;

alter table tb_order
add column cd_order bigint not null
    constraint pk_tb_order_cd_order
    primary key;

-- Tabela tb_order_item
alter table tb_order_item
drop constraint if exists tb_order_item_pkey CASCADE;

alter table tb_order_item
add column cd_order_item bigint not null
    constraint pk_tb_order_item_cd_order_item
    primary key;

-- Tabela tb_payment
alter table tb_payment
drop constraint if exists tb_payment_pkey CASCADE;

alter table tb_payment
add column cd_payment bigint not null
    constraint pk_tb_payment_cd_payment
    primary key;

-- Tabela tb_permission
alter table tb_permission
drop constraint if exists tb_permission_pkey CASCADE; 

alter table tb_permission
add column cd_permission bigint not null
    constraint pk_tb_permission_cd_permission
    primary key;

-- Tabela tb_product
alter table tb_product
drop constraint if exists tb_product_pkey CASCADE;


alter table tb_product
add column cd_product bigint not null
    constraint pk_tb_product_cd_product
    primary key;

-- Tabela tb_restaurant

alter table tb_restaurant
drop constraint if exists tb_restaurant_pkey CASCADE;

alter table tb_restaurant
add column cd_restaurant bigint not null
    constraint pk_tb_restaurant_cd_restaurant
    primary key;
	
-- Tabela tb_state
alter table tb_state
drop constraint if exists tb_state_pkey CASCADE;

alter table tb_state
add column cd_state bigint not null
    constraint pk_tb_state_cd_state
    primary key;

-- Tabela tb_user
alter table tb_user
drop constraint if exists tb_user_pkey CASCADE;

alter table tb_user
add column cd_user bigint not null
    constraint pk_tb_user_cd_user
    primary key;








