-- Product types
INSERT INTO product_types (id, name, is_digital)
VALUES (1, 'book', false) ON CONFLICT (id) DO NOTHING;
INSERT INTO product_types (id, name, is_digital)
VALUES (2, 'ebook', true) ON CONFLICT (id) DO NOTHING;

-- Products
INSERT INTO products (id, title, product_type_id)
VALUES (1, 'Harry Potter and the Philosopher''s Stone', 1) ON CONFLICT (id) DO NOTHING;
INSERT INTO products (id, title, product_type_id)
VALUES (2, 'Harry Potter and the Chamber of Secrets', 2) ON CONFLICT (id) DO NOTHING;
INSERT INTO products (id, title, product_type_id)
VALUES (3, 'Harry Potter and the Prisoner of Azkaban', 1) ON CONFLICT (id) DO NOTHING;
INSERT INTO products (id, title, product_type_id)
VALUES (4, 'Harry Potter and the Goblet of Fire', 2) ON CONFLICT (id) DO NOTHING;
INSERT INTO products (id, title, product_type_id)
VALUES (5, 'Harry Potter and the Order of the Phoenix', 1) ON CONFLICT (id) DO NOTHING;
INSERT INTO products (id, title, product_type_id)
VALUES (6, 'Harry Potter and the Half-Blood Prince', 2) ON CONFLICT (id) DO NOTHING;
INSERT INTO products (id, title, product_type_id)
VALUES (7, 'Harry Potter and the Deathly Hallows', 1) ON CONFLICT (id) DO NOTHING;

--  Clients
INSERT INTO clients (client_id, first_name, last_name, email, password,
                     is_enabled, borrowed_books_count)
VALUES (1, 'Test', 'User', 'test@gmail.com', '1234', true, 0);

--  Roles
INSERT INTO client_roles (client_role_id, client_role)
VALUES (1, 'ADMIN'),
       (2, 'CLIENT');

-- Join table (same Client -> multiple Roles)
INSERT INTO client_roles_relationship (client_id, client_role_id)
VALUES (1, 1),
       (1, 2);

