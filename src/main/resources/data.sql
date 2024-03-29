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

/*Images*/

/*Placeholder image*/

INSERT INTO images (id_image, image_name, is_main_image) VALUES (default, 'placeholder-image.jpg', true);

/*Proucts images*/
INSERT INTO images (id_image, image_name, is_main_image, product_id) VALUES (default, 'flamenco-fan-earrings.png', true, 1);
INSERT INTO images (id_image, image_name, is_main_image, product_id) VALUES (default, 'scheherazade-blue-earrings.png', true, 2);
INSERT INTO images (id_image, image_name, is_main_image, product_id, category_id) VALUES (default, 'esmeralda-earrings.png', true, 3, 1);

/*Categories images*/

INSERT INTO images (id_image, image_name, is_main_image, category_id) VALUES (default, 'necklace-and-earrings-category.png', true, 3);
INSERT INTO images (id_image, image_name, is_main_image, category_id) VALUES (default, 'studs-category.png', true, 5);
INSERT INTO images (id_image, image_name, is_main_image, category_id) VALUES (default, 'blue-necklace.png', true, 2);
INSERT INTO images (id_image, image_name, is_main_image, category_id) VALUES (default, 'ring-and-earrings-category.png', true, 4);

/*Categories-Products*/

INSERT INTO categories_products (product_id, category_id) VALUES (1, 1);
INSERT INTO categories_products (product_id, category_id) VALUES (2, 1);
INSERT INTO categories_products (product_id, category_id) VALUES (3, 1);