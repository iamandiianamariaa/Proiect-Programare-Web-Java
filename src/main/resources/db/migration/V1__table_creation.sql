CREATE TABLE users
(
    id              BIGINT      NOT NULL AUTO_INCREMENT,
    username        VARCHAR(50) NOT NULL,
    name            VARCHAR(50) NOT NULL,
    user_type       VARCHAR(50) NOT NULL,
    account_created datetime    NOT NULL,

    PRIMARY KEY (id)
);

CREATE TABLE products
(
    id           BIGINT        NOT NULL AUTO_INCREMENT,
    name         VARCHAR(255)  NOT NULL,
    brand        VARCHAR(50)   NOT NULL,
    price        DECIMAL(5, 2) NOT NULL,
    size         VARCHAR(50)   NOT NULL,
    description  VARCHAR(255)  NOT NULL,
    ingredients  VARCHAR(255)  NOT NULL,
    instructions VARCHAR(255)  NOT NULL,
    review_score DECIMAL(3, 2) NOT NULL,

    PRIMARY KEY (id)
);

CREATE TABLE reviews
(
    id            BIGINT        NOT NULL AUTO_INCREMENT,
    customer_name VARCHAR(50)   NOT NULL,
    review        VARCHAR(255)  NOT NULL,
    rating        DECIMAL(3, 2) NOT NULL,
    review_date   datetime      NOT NULL,
    fk_product    BIGINT        NOT NULL,

    PRIMARY KEY (id),
    FOREIGN KEY (fk_product) REFERENCES products (id)
);

CREATE TABLE order_details
(
    id            BIGINT      NOT NULL AUTO_INCREMENT,
    customer_name VARCHAR(50) NOT NULL,
    phone         VARCHAR(50) NOT NULL,
    city          VARCHAR(50) NOT NULL,
    country       VARCHAR(50) NOT NULL,
    street        VARCHAR(50) NOT NULL,
    payment_mode  VARCHAR(50) NOT NULL,

    PRIMARY KEY (id)
);

CREATE TABLE orders
(
    id               BIGINT        NOT NULL AUTO_INCREMENT,
    price            DECIMAL(6, 2) NOT NULL,
    status           VARCHAR(50)   NOT NULL,
    no_products      INT           NOT NULL,
    order_date       datetime      NOT NULL,
    fk_order_details BIGINT        NOT NULL,
    fk_user          BIGINT        NOT NULL,

    PRIMARY KEY (id),
    FOREIGN KEY (fk_order_details) REFERENCES order_details (id) ON DELETE CASCADE,
    FOREIGN KEY (fk_user) REFERENCES users (id)
);

CREATE TABLE categories
(
    id   BIGINT       NOT NULL AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,

    PRIMARY KEY (id)
);

CREATE TABLE product_categories
(
    id          BIGINT NOT NULL AUTO_INCREMENT,
    product_id  BIGINT NOT NULL,
    category_id BIGINT NOT NULL,

    PRIMARY KEY (id),
    FOREIGN KEY (product_id) REFERENCES products (id),
    FOREIGN KEY (category_id) REFERENCES categories (id)
);

CREATE TABLE ordered_products
(
    id         BIGINT NOT NULL AUTO_INCREMENT,
    product_id BIGINT NOT NULL,
    order_id   BIGINT NOT NULL,

    PRIMARY KEY (id),
    FOREIGN KEY (product_id) REFERENCES products (id),
    FOREIGN KEY (order_id) REFERENCES orders (id)
);