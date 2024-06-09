CREATE TABLE Product (
    product_id INT AUTO_INCREMENT PRIMARY KEY,
    product_name VARCHAR(255) NOT NULL,
    product_description VARCHAR(255) NOT NULL,
    price INT NOT NULL,
    image_path VARCHAR(255),
    category VARCHAR(255),
    USER_ID INT NOT NULL,
    USER_NAME VARCHAR(255)
);