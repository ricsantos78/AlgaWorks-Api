
ALTER TABLE tb_city ADD COLUMN dt_registration timestamp not null;
ALTER TABLE tb_city ADD COLUMN dt_update timestamp not null;

ALTER TABLE tb_group ADD COLUMN dt_registration timestamp not null;
ALTER TABLE tb_group ADD COLUMN dt_update timestamp not null;

ALTER TABLE tb_group_permission ADD COLUMN dt_registration timestamp not null;
ALTER TABLE tb_group_permission ADD COLUMN dt_update timestamp not null;

ALTER TABLE tb_kitchen ADD COLUMN dt_registration timestamp not null;
ALTER TABLE tb_kitchen ADD COLUMN dt_update timestamp not null;

ALTER TABLE tb_order DROP COLUMN dt_criation;
ALTER TABLE tb_order ADD COLUMN dt_registration timestamp not null;
ALTER TABLE tb_order ADD COLUMN dt_update timestamp not null;

ALTER TABLE tb_order_item ADD COLUMN dt_registration timestamp not null;
ALTER TABLE tb_order_item ADD COLUMN dt_update timestamp not null;

ALTER TABLE tb_payment ADD COLUMN dt_registration timestamp not null;
ALTER TABLE tb_payment ADD COLUMN dt_update timestamp not null;

ALTER TABLE tb_permission ADD COLUMN dt_registration timestamp not null;
ALTER TABLE tb_permission ADD COLUMN dt_update timestamp not null;

ALTER TABLE tb_product ADD COLUMN dt_registration timestamp not null;
ALTER TABLE tb_product ADD COLUMN dt_update timestamp not null;

ALTER TABLE tb_restaurant_payment ADD COLUMN dt_registration timestamp not null;
ALTER TABLE tb_restaurant_payment ADD COLUMN dt_update timestamp not null;

ALTER TABLE tb_state ADD COLUMN dt_registration timestamp not null;
ALTER TABLE tb_state ADD COLUMN dt_update timestamp not null;

ALTER TABLE tb_user ADD COLUMN dt_update timestamp not null;

ALTER TABLE tb_user_group ADD COLUMN dt_registration timestamp not null;
ALTER TABLE tb_user_group ADD COLUMN dt_update timestamp not null;