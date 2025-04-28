-- Product types
INSERT INTO product_types (product_type_id, name, is_digital)
VALUES (1, 'book', false) ON CONFLICT (product_type_id) DO NOTHING;
INSERT INTO product_types (product_type_id, name, is_digital)
VALUES (2, 'ebook', true) ON CONFLICT (product_type_id) DO NOTHING;


-- Products ─────────────────────────────────────────────────
INSERT INTO products (product_id, title, product_type_id)
VALUES (1, 'Harry Potter and the Philosopher''s Stone', 1) ON CONFLICT (product_id) DO NOTHING;
INSERT INTO products (product_id, title, product_type_id)
VALUES (2, 'Harry Potter and the Chamber of Secrets', 2) ON CONFLICT (product_id) DO NOTHING;
INSERT INTO products (product_id, title, product_type_id)
VALUES (3, 'Harry Potter and the Prisoner of Azkaban', 1) ON CONFLICT (product_id) DO NOTHING;
INSERT INTO products (product_id, title, product_type_id)
VALUES (4, 'Harry Potter and the Goblet of Fire', 2) ON CONFLICT (product_id) DO NOTHING;
INSERT INTO products (product_id, title, product_type_id)
VALUES (5, 'Harry Potter and the Order of the Phoenix', 1) ON CONFLICT (product_id) DO NOTHING;
INSERT INTO products (product_id, title, product_type_id)
VALUES (6, 'Harry Potter and the Half-Blood Prince', 2) ON CONFLICT (product_id) DO NOTHING;
INSERT INTO products (product_id, title, product_type_id)
VALUES (7, 'Harry Potter and the Deathly Hallows', 1) ON CONFLICT (product_id) DO NOTHING;

-- Test different creator roles
INSERT INTO products (product_id, title, product_type_id)
VALUES (10, 'Harry Potter and the Cursed Child', 1) ON CONFLICT (product_id) DO NOTHING;
INSERT INTO products (product_id, title, product_type_id)
VALUES (11, 'Fantastic Beasts and Where to Find Them', 1) ON CONFLICT (product_id) DO NOTHING;

--  Clients ─────────────────────────────────────────────────
INSERT INTO clients (client_id, first_name, last_name, email, password,
                     is_enabled, borrowed_books_count)
VALUES (1, 'Test', 'User', 'test@gmail.com', '1234', true, 0);

-- Client Roles
INSERT INTO client_roles (client_role_id, client_role)
VALUES (1, 'ADMIN'),
       (2, 'CLIENT');

-- Join table (same Client -> multiple Roles)
INSERT INTO client_roles_relationship (client_id, client_role_id)
VALUES (1, 1),
       (1, 2);

-- Creators ─────────────────────────────────────────────────
INSERT INTO creators (creator_id, creator_first_name, creator_last_name)
VALUES (1, 'J. K.', 'Rowling'),
       (2, 'John', 'Tiffany'),
       (3, 'Jack', 'Thorne');


--  Creator Roles
INSERT INTO creator_roles (creator_role_id, creator_role)
VALUES (1, 'author'),
       (2, 'co-author'),
       (3, 'editor'),
       (4, 'director');

-- Join table (same Creator ->  multiple  Roles; same Product -> multiple Creators)
INSERT INTO creator_product_relation (creator_id, product_id, creator_role_id)
VALUES
    -- Rowling on both books, different roles
    (1, 10, 1),   -- AUTHOR on Cursed Child   (already there)
    (1, 11, 2),   -- CO_AUTHOR on Fantastic Beasts

    -- Cursed Child still has two co-authors
    (2, 10, 2),   -- Tiffany  CO_AUTHOR
    (3, 10, 2);   -- Thorne   CO_AUTHOR

