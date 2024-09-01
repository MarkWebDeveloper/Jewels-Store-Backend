/*Login roles*/
INSERT INTO roles (id_role, name) VALUES (default, 'ROLE_ADMIN');
INSERT INTO roles (id_role, name) VALUES (default, 'ROLE_USER');

/*Users*/
INSERT INTO users(id_user, username, password) VALUES (default, 'admin@gmail.com', '$2a$12$xOx5K0CaHRWkRgaZBRHvZ.tcrVC/AeA3sIjCySnHKk6ZEM9kmuIyO');

INSERT INTO users(id_user, username, password) VALUES (default, 'user1@gmail.com', '$2a$12$xOx5K0CaHRWkRgaZBRHvZ.tcrVC/AeA3sIjCySnHKk6ZEM9kmuIyO');

INSERT INTO users(id_user, username, password) VALUES (default, 'user2@gmail.com', '$2a$12$xOx5K0CaHRWkRgaZBRHvZ.tcrVC/AeA3sIjCySnHKk6ZEM9kmuIyO');

/*Roles-users*/
INSERT INTO role_users (role_id, user_id) VALUES (1, 1);
INSERT INTO role_users (role_id, user_id) VALUES (2, 2);
INSERT INTO role_users (role_id, user_id) VALUES (2, 3);

/*Profiles*/

INSERT INTO profiles(id_profile, user_id, email, first_Name, last_Name, address, postal_Code, city, province, number_Phone) VALUES(1, 1, 'admin', 'Juan', 'apellidos1', 'direccion1', 'city1', 'province1', '88888', '12312323');

INSERT INTO profiles(id_profile, user_id, email, first_Name, last_Name, address, postal_Code, city, province, number_Phone) VALUES(2, 2, 'user1', 'Mark', 'apellidos2', 'direccion2', 'city2', 'province2', '11111', '32132132');

INSERT INTO profiles(id_profile, user_id, email, first_Name, last_Name, address, postal_Code, city, province, number_Phone) VALUES(3, 3, 'user2', 'Nico', 'apellidos3', 'direccion3', 'city3', 'province3', '22222', '21212111');

/*Categories*/

INSERT INTO categories (id_category, category_name) VALUES (default, 'Earrings');
INSERT INTO categories (id_category, category_name) VALUES (default, 'Necklaces');
INSERT INTO categories (id_category, category_name) VALUES (default, 'Necklace & Earrings Set');
INSERT INTO categories (id_category, category_name) VALUES (default, 'Ring & Earrings Set');
INSERT INTO categories (id_category, category_name) VALUES (default, 'Studs');

/*Products*/

/*Category 1*/
INSERT INTO products (id_product, product_name, product_description, price) VALUES (default, 'Flamenco Abanico Earrings', 'Passionate design inspired by flamenco dancers fans', 1999);
INSERT INTO products (id_product, product_name, product_description, price) VALUES (default, 'Scheherazade Blue Earrings', 'Magical earrings for your evening dress', 2999);
INSERT INTO products (id_product, product_name, product_description, price) VALUES (default, 'Esmeralda Earrings', 'Beatiful green earrings Esmeralda', 2999);
INSERT INTO products (id_product, product_name, product_description, price) VALUES (default, 'Flamenco Abanico Earrings', 'Passionate design inspired by flamenco dancers fans', 1999);
INSERT INTO products (id_product, product_name, product_description, price) VALUES (default, 'Scheherazade Blue Earrings', 'Magical earrings for your evening dress', 2999);
INSERT INTO products (id_product, product_name, product_description, price) VALUES (default, 'Esmeralda Earrings', 'Beatiful green earrings Esmeralda', 2999);
INSERT INTO products (id_product, product_name, product_description, price) VALUES (default, 'Flamenco Abanico Earrings', 'Passionate design inspired by flamenco dancers fans', 1999);
INSERT INTO products (id_product, product_name, product_description, price) VALUES (default, 'Scheherazade Blue Earrings', 'Magical earrings for your evening dress', 2999);
INSERT INTO products (id_product, product_name, product_description, price) VALUES (default, 'Esmeralda Earrings', 'Beatiful green earrings Esmeralda', 2999);
INSERT INTO products (id_product, product_name, product_description, price) VALUES (default, 'Flamenco Abanico Earrings', 'Passionate design inspired by flamenco dancers fans', 1999);

/*Images*/

/*Placeholder image*/

INSERT INTO images (id_image, image_name, is_main_image) VALUES (default, 'placeholder-image.jpg', true);

/*Proucts images*/
INSERT INTO images (id_image, image_name, is_main_image, product_id) VALUES (default, 'flamenco-fan-earrings.png', true, 1);
INSERT INTO images (id_image, image_name, is_main_image, product_id) VALUES (default, 'scheherazade-blue-earrings.png', true, 2);
INSERT INTO images (id_image, image_name, is_main_image, product_id, category_id) VALUES (default, 'esmeralda-earrings.png', true, 3, 1);
INSERT INTO images (id_image, image_name, is_main_image, product_id) VALUES (default, 'flamenco-fan-earrings.png', true, 4);
INSERT INTO images (id_image, image_name, is_main_image, product_id) VALUES (default, 'scheherazade-blue-earrings.png', true, 5);
INSERT INTO images (id_image, image_name, is_main_image, product_id) VALUES (default, 'esmeralda-earrings.png', true, 6);
INSERT INTO images (id_image, image_name, is_main_image, product_id) VALUES (default, 'flamenco-fan-earrings.png', true, 7);
INSERT INTO images (id_image, image_name, is_main_image, product_id) VALUES (default, 'scheherazade-blue-earrings.png', true, 8);
INSERT INTO images (id_image, image_name, is_main_image, product_id) VALUES (default, 'esmeralda-earrings.png', true, 9);
INSERT INTO images (id_image, image_name, is_main_image, product_id) VALUES (default, 'flamenco-fan-earrings.png', true, 10);


/*Categories images*/

INSERT INTO images (id_image, image_name, is_main_image, category_id) VALUES (default, 'necklace-and-earrings-category.png', true, 3);
INSERT INTO images (id_image, image_name, is_main_image, category_id) VALUES (default, 'studs-category.png', true, 5);
INSERT INTO images (id_image, image_name, is_main_image, category_id) VALUES (default, 'blue-necklace.png', true, 2);
INSERT INTO images (id_image, image_name, is_main_image, category_id) VALUES (default, 'ring-and-earrings-category.png', true, 4);

/*Categories-Products*/

INSERT INTO categories_products (product_id, category_id) VALUES (1, 1);
INSERT INTO categories_products (product_id, category_id) VALUES (2, 1);
INSERT INTO categories_products (product_id, category_id) VALUES (3, 1);
INSERT INTO categories_products (product_id, category_id) VALUES (4, 1);
INSERT INTO categories_products (product_id, category_id) VALUES (5, 1);
INSERT INTO categories_products (product_id, category_id) VALUES (6, 1);
INSERT INTO categories_products (product_id, category_id) VALUES (7, 1);
INSERT INTO categories_products (product_id, category_id) VALUES (8, 1);
INSERT INTO categories_products (product_id, category_id) VALUES (9, 1);
INSERT INTO categories_products (product_id, category_id) VALUES (10, 1);