USE demo_webshop;

DROP TABLE IF EXISTS gustafs_pingla;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS customer;

CREATE TABLE customer (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          name VARCHAR(100) NOT NULL,
                          email VARCHAR(150) NOT NULL UNIQUE
);

CREATE TABLE orders (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        customer_id BIGINT NOT NULL,
                        created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        status VARCHAR(30) NOT NULL,
                        CONSTRAINT fk_orders_customer FOREIGN KEY (customer_id) REFERENCES customer(id)
);

CREATE TABLE gustafs_pingla (
                                id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                order_id BIGINT NOT NULL,
                                pingla_name VARCHAR(120) NOT NULL,
                                unit_price DECIMAL(10,2) NOT NULL,
                                quantity INT NOT NULL,
                                CONSTRAINT fk_pingla_order FOREIGN KEY (order_id) REFERENCES orders(id)
);
