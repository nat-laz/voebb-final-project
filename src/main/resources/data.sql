-- Product types
INSERT INTO product_types (id, name, is_digital) VALUES (1,'book', false) ON CONFLICT (id) DO NOTHING;
INSERT INTO product_types (id, name, is_digital) VALUES (2,'ebook', true) ON CONFLICT (id) DO NOTHING;
-- Products
INSERT INTO products (id, title, product_type_id) VALUES (1,'Harry Potter and the Philosopher''s Stone', 1) ON CONFLICT (id) DO NOTHING;
INSERT INTO products (id, title, product_type_id) VALUES (2,'Harry Potter and the Chamber of Secrets', 2) ON CONFLICT (id) DO NOTHING;
INSERT INTO products (id, title, product_type_id) VALUES (3,'Harry Potter and the Prisoner of Azkaban', 1) ON CONFLICT (id) DO NOTHING;
INSERT INTO products (id, title, product_type_id) VALUES (4,'Harry Potter and the Goblet of Fire', 2) ON CONFLICT (id) DO NOTHING;
INSERT INTO products (id, title, product_type_id) VALUES (5,'Harry Potter and the Order of the Phoenix', 1) ON CONFLICT (id) DO NOTHING;
INSERT INTO products (id, title, product_type_id) VALUES (6,'Harry Potter and the Half-Blood Prince', 2) ON CONFLICT (id) DO NOTHING;
INSERT INTO products (id, title, product_type_id) VALUES (7,'Harry Potter and the Deathly Hallows', 1) ON CONFLICT (id) DO NOTHING;