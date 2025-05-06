-- Product types
INSERT INTO product_types ( name, is_digital)
VALUES ('book', false)
ON CONFLICT DO NOTHING;
INSERT INTO product_types ( name, is_digital)
VALUES ('ebook', true)
ON CONFLICT DO NOTHING;


-- Products
INSERT INTO products (product_type_id, product_link_to_emedia, title, release_year, photo, description)
VALUES (1, NULL, 'Harry Potter and the Philosopher''s Stone', '1997', 'photo_url_1',
        'First book of Harry Potter series'),
       (2, 'https://ebooks.voebb.de/hp1', 'Harry Potter and the Philosopher''s Stone', '1997', 'photo_url_1',
        'First book of Harry Potter series'),

       (1, NULL, 'Harry Potter and the Chamber of Secrets', '1998', 'photo_url_2', 'Second book in the series'),
       (2, 'https://ebooks.voebb.de/hp2', 'Harry Potter and the Chamber of Secrets', '1998', 'photo_url_2',
        'Second book in the series'),

       (1, NULL, 'Harry Potter and the Prisoner of Azkaban', '1999', 'photo_url_3', 'Third book in the series'),
       (2, 'https://ebooks.voebb.de/hp3', 'Harry Potter and the Goblet of Fire', '2000', 'photo_url_4',
        'Fourth book in the series'),

       (1, NULL, 'Harry Potter and the Order of the Phoenix', '2003', 'photo_url_5', 'Fifth book in the series'),
       (2, 'https://ebooks.voebb.de/hp4', 'Harry Potter and the Half-Blood Prince', '2005', 'photo_url_6',
        'Sixth book in the series')
ON CONFLICT (product_id) DO NOTHING;


-- Book Details (for both physical books and e-books)
INSERT INTO book_details (product_id, book_isbn, book_edition, book_pages)
VALUES (1, '9780747532699', '1st Edition', 223),
       (2, '9780747532699-E', 'Digital Edition', 223),
       (3, '9780747538493', '1st Edition', 251),
       (4, '9780747538493-E', 'Digital Edition', 251)
ON CONFLICT DO NOTHING;

-- Creators ─────────────────────────────────────────────────
INSERT INTO creators (creator_first_name, creator_last_name)
VALUES ('J. K.', 'Rowling'),
       ('John', 'Tiffany'),
       ('Jack', 'Thorne');


--  Creator Roles
INSERT INTO creator_roles (creator_role)
VALUES ('author'),
       ('co-author'),
       ('editor'),
       ('director');

-- Join table (same Creator ->  multiple  Roles; same Product -> multiple Creators)
INSERT INTO creator_product_relation (creator_id, product_id, creator_role_id)
VALUES
    -- Rowling on both books, different roles
    (1, 1, 1), -- AUTHOR on Cursed Child   (already there)
    (1, 2, 2), -- CO_AUTHOR on Fantastic Beasts

    -- Cursed Child still has two co-authors
    (2, 2, 2), -- Tiffany  CO_AUTHOR
    (3, 2, 2);
-- Thorne   CO_AUTHOR

--  Clients ─────────────────────────────────────────────────
INSERT INTO custom_users (first_name, last_name, email, password,
                          is_enabled, borrowed_books_count)
VALUES ('User 1', 'One', 'test@gmail.com', '1234', true, 0),
       ('User 2 ', 'Two', 'test@example.com', '1234', true, 0)
ON CONFLICT (custom_user_id) DO NOTHING;

-- Client Roles
INSERT INTO user_roles (role_id, role_name)
VALUES (1, 'ROLE_ADMIN'),
       (2, 'ROLE_CLIENT'),
       (3, 'ROLE_GUEST');

-- Join table (same Client -> multiple Roles)
INSERT INTO users_roles_relation (custom_user_id, role_id)
VALUES (1, 1),
       (1, 2);

-- Countries ─────────────────────────────────────────────────
INSERT INTO countries (country_name)
VALUES ('United Kingdom')
ON CONFLICT DO NOTHING;

INSERT INTO countries (country_name)
VALUES ('United States')
ON CONFLICT DO NOTHING;

-- Join table (product linked to countries)
INSERT INTO country_relation (product_id, country_id)
VALUES (1, 1), -- Philosopher's Stone published in the UK
       (1, 2), -- ... and in the US
       (2, 1), -- Cursed Child in the UK
       (3, 2)  -- Fantastic Beasts in the US
ON CONFLICT (product_id, country_id) DO NOTHING;

-- Languages  ─────────────────────────────────────────────────
INSERT INTO languages (language_name)
VALUES ('English'),
       ('German')
ON CONFLICT DO NOTHING;

-- Join table (product linked to languages)
INSERT INTO language_relation (product_id, language_id)
VALUES (1, 1),
       (1, 2)
ON CONFLICT (product_id, language_id) DO NOTHING;


-- Libraries ─────────────────────────────────────────────────
INSERT INTO libraries (library_id,
                       library_name, library_description,
                       address_city, address_postcode, address_street, address_house_nr, address_osm_link)
VALUES (1,
        'Central Library', 'Main city branch',
        'Berlin', '10961', 'Alexanderplatz', '1A',
        'https://osm.org/go/0MBK8W');

INSERT INTO item_status (item_status_id, item_status_name)
VALUES (1, 'available'),
       (2, 'reserved'),
       (3, 'borrowed');

-- ─────────── mock: copy #1001 of product 1, available  ───────────
INSERT INTO product_items (item_id, product_id, status_id)
VALUES (1001, 1, 1);

-- ─────────── mock: locate above copy in the library ───────────
INSERT INTO item_location (item_location_id, item_id, library_id, location_in_library)
VALUES (501, 1001, 1, 'Shelf A-12');

--  ─────────── mock: client_id = 1 borrows item_id = 1001 ───────────
INSERT INTO borrows (borrow_id, custom_user_id, item_id,
                     borrow_start_date, borrow_due_date,
                     return_date, extends_count)
VALUES (1, 1, 1001,
        CURRENT_DATE, CURRENT_DATE + INTERVAL '14 day',
        NULL, 0);

--  ─────────── mock: client_id = 1 reserves item_id = 1001 ───────────
INSERT INTO reservations (reservation_id, custom_user_id, item_id, reservation_start, reservation_due)
VALUES (1, 2, 1001, CURRENT_DATE, CURRENT_DATE + INTERVAL '7 day')
ON CONFLICT DO NOTHING;

-- item status ---
INSERT INTO item_status (item_status_name)
VALUES ('borrowed')
ON CONFLICT DO NOTHING;
INSERT INTO item_status (item_status_name)
VALUES ('reserved')
ON CONFLICT DO NOTHING;
INSERT INTO item_status (item_status_name)
VALUES ('available')
ON CONFLICT DO NOTHING;
