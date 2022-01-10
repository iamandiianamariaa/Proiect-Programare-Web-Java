INSERT INTO users
VALUES (null, 'anam', 'ana popescu', 'USER', '2021-12-14 11:48:38');
INSERT INTO users
VALUES (null, 'admin', 'admin', 'ADMIN', '2021-12-14 11:48:38');

INSERT INTO products
VALUES (null, 'Face Cream', 'Nivea', 20, '50 ml', 'hydrating face cream for dry skin',
        'Water, Mineral Oil, Petrolatum, Glycerin, Microcrystalline Wax, Lanolin Alcohol, Paraffin', 'Use twice a day',
        4);
INSERT INTO reviews
VALUES (null, 'anam', 'great product', 3.73, '2021-12-14 11:48:38', 1);
INSERT INTO categories
VALUES (null, 'face cream');
INSERT INTO order_details
VALUES (null, 'anam', '0739477556', 'bucharest', 'Romania', 'Str. Zimbrului', 'CARD');
INSERT INTO orders
VALUES (null, 20, 'PLACED', 1, '2021-12-14 11:48:38', 1, 1);
INSERT INTO product_categories
VALUES (null, 1, 1);
INSERT INTO ordered_products
VALUES (null, 1, 1);