/*Categories*/

INSERT INTO categories (id_category, category_name) VALUES (default, 'Earrings');
INSERT INTO categories (id_category, category_name) VALUES (default, 'Necklace & Earrings Set');
INSERT INTO categories (id_category, category_name) VALUES (default, 'Studs');
INSERT INTO categories (id_category, category_name) VALUES (default, 'Necklaces');
INSERT INTO categories (id_category, category_name) VALUES (default, 'Rings');
INSERT INTO categories (id_category, category_name) VALUES (default, 'Ring & Earrings Set');

/*Products*/

/*Category 1*/
INSERT INTO products (id_product, product_name, product_description, price) VALUES (default, 'Flamenco Abanico Earrings', 'Passionate design inspired by flamenco dancers fans', 19.99);
INSERT INTO products (id_product, product_name, product_description, price) VALUES (default, 'Scheherazade Blue Earrings', 'Magical earrings for your evening dress', 29.99);
INSERT INTO products (id_product, product_name, product_description, price) VALUES (default, 'Esmeralda Earrings', 'Beatiful green earrings Esmeralda', 29.99);

/*Images*/

INSERT INTO images (id_image, image_name, is_main_image, product_id) VALUES (default, 'scheherazade-blue-earrings.png', true, 2);
INSERT INTO images (id_image, image_name, is_main_image, product_id) VALUES (default, 'esmeralda-earrings.png', true, 3);

/*Categories-Products*/

INSERT INTO categories_products (product_id, category_id) VALUES (1, 1);
INSERT INTO categories_products (product_id, category_id) VALUES (2, 1);
INSERT INTO categories_products (product_id, category_id) VALUES (3, 1);