CREATE TABLE category (
    id INT AUTO_INCREMENT PRIMARY KEY,
    category_name VARCHAR(100)
);

INSERT INTO category(category_name) values
('electronics'), ('kids'), ('cosmetics'), ('books'), ('sports'), ('shoes'), ('clothes'), ('home_decor'),('kitchen'), ('furniture');

CREATE TABLE product (
    product_id INT AUTO_INCREMENT PRIMARY KEY,
    product_name VARCHAR(255) NOT NULL,
    product_description VARCHAR(255) NOT NULL,
    price INT NOT NULL,
    image_path VARCHAR(255),
    category VARCHAR(255),
    USER_ID INT NOT NULL,
    USER_NAME VARCHAR(255),
    category_id int,
    foreign key (category_id) references category(id)
);


SELECT U.ID , U.EMAIL , U.NAME , U.PHONE_NUMBER , U.ROLE, P.OK_TO_MAIL , P.OK_TO_PUSH , P.OK_TO_SMS , P.TIME_STAMP  FROM USER_TABLE U JOIN USER_PREFERENCE P ON U.ID = P.USER_ID WHERE P.OK_TO_PUSH =TRUE