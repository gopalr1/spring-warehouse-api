DROP sequence IF EXISTS rack_seq;
ALTER TABLE IF EXISTS item_locator DROP constraint rack_id_fk;
ALTER TABLE IF EXISTS item_locator DROP constraint item_id_fk;
DROP TABLE IF EXISTS rack;
DROP TABLE IF EXISTS item;
DROP TABLE IF EXISTS item_locator;


CREATE TABLE IF NOT EXISTS rack
(
    id  BIGINT AUTO_INCREMENT PRIMARY KEY,
    rack_type VARCHAR(12)  NOT NULL,
    is_allocated boolean NOT NULL,
    warehouse_name VARCHAR(30)

);

create TABLE IF NOT EXISTS item
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_number BIGINT NOT NULL,
    product_name VARCHAR(50)NOT NULL,
    quantity BIGINT NOT NULL,
    package_size VARCHAR(12) NOT NULL,
    notes VARCHAR(100) NOT NULL,
    in_timestamp timestamp,
    out_timestamp timestamp

);

create TABLE IF NOT EXISTS item_locator
(
    id  BIGINT AUTO_INCREMENT PRIMARY KEY,
    rack_id  BIGINT,
    item_id BIGINT NOT NULL,
    CONSTRAINT item_id_fk FOREIGN KEY (item_id)
    references item (id),
    CONSTRAINT rack_id_fk FOREIGN KEY (rack_id)
    references rack (id)
);



INSERT INTO rack (rack_type, is_allocated, warehouse_name)
VALUES ('SMALL', true, 'Utrecht01'),('SMALL', true, 'Utrecht01');

INSERT INTO item (order_number, product_name, quantity, package_size, notes, in_timestamp)
VALUES (2001, 'Kids dress',  1, 'SMALL', '', {ts '2022-07-23 09:15:21.001'}),
       (3028, 'Glass Window', 2, 'EXTRA_LARGE', 'Handle with care', {ts '2022-10-07 21:15:21.001'});

INSERT INTO item_locator (rack_id, item_id)
VALUES (1, 1),(2,2);


