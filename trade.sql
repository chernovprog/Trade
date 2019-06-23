CREATE DATABASE trade CHARACTER SET utf8 COLLATE utf8_general_ci;

use trade;

CREATE TABLE stores
(
    id         BIGINT(20) NOT NULL AUTO_INCREMENT,
    PRODUCT_ID BIGINT(20),
    quantity   INT(10)    NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (PRODUCT_ID) REFERENCES products (id)
);

CREATE TABLE orders
(
    id             BIGINT(20) NOT NULL AUTO_INCREMENT,
    order_num      INT(10)    NOT NULL,
    ordered_amount DOUBLE,
    quantity       INT(10)    NOT NULL,
    DATE_CREATED   DATETIME,
    DATE_UPDATED   DATETIME,
    PRIMARY KEY (id)
);

CREATE TABLE order_details
(
    id         BIGINT(20) NOT NULL AUTO_INCREMENT,
    quantity   INT(10)    NOT NULL,
    price      DOUBLE,
    amount     DOUBLE,
    ORDER_ID   BIGINT(20),
    PRODUCT_ID BIGINT(20),
    PRIMARY KEY (id),
    FOREIGN KEY (ORDER_ID) REFERENCES orders (id),
    FOREIGN KEY (PRODUCT_ID) REFERENCES products (id)
);


CREATE TABLE products
(
    id          BIGINT(20)   NOT NULL AUTO_INCREMENT,
    name        VARCHAR(255) NOT NULL,
    price       DOUBLE,
    currency    VARCHAR(5),
    description TEXT,
    image       VARCHAR(255),
    code        VARCHAR(50),
    PRIMARY KEY (id)
);



INSERT INTO products(id, name, price, currency, description, image, code) VALUE (1, "Ноутбук ASUS X512UF-EJ099", 20999,
                                                                                 "UAH",
                                                                                 "ASUS VivoBook 15 это компактный ноутбук, который практически лишен рамки со всех четырех сторон. Благодаря тому, что ее толщина всего 5,7 мм, относительная площадь экрана составляет целых 88%.",
                                                                                 "img/ASUS_X512UF-EJ099.jpg", "code1");
INSERT INTO products(id, name, price, currency, description, image, code) VALUE (2,
                                                                                 "Ноутбук APPLE A1932 MacBook Air 13",
                                                                                 38999, "UAH",
                                                                                 "Это самый любимый Mac, в который можно заново влюбиться. Новый MacBook Air ещё более тонкий и лёгкий, оснащён дисплеем Retina, Touch ID, клавиатурой нового поколения, трекпадом Force Touch.",
                                                                                 "img/APPLE_A1932_MacBook_Air_13.jpg",
                                                                                 "code2");
INSERT INTO products(id, name, price, currency, description, image, code) VALUE (3, "Ноутбук LENOVO IdeaPad 330-15IKBR",
                                                                                 18989, "UAH",
                                                                                 "Благодаря возможности выбора спецификаций в соответствии со своими растущими потребностями, ты можешь быть уверен, что ноутбук Ideapad 330 будет отличным помощником в будущем.",
                                                                                 "img/LENOVO_IdeaPad_330-15IKBR.jpeg",
                                                                                 "code3");

INSERT INTO stores (PRODUCT_ID, quantity) VALUES (1, 25), (2, 12), (3, 40);
