-- TEST: creator × product × creator_role
SELECT
    p.title,
    c.creator_first_name || ' ' || c.creator_last_name AS creator,
    r.creator_role
FROM creator_product_relation cpr
         JOIN creators c ON c.creator_id = cpr.creator_id
         JOIN products p ON p.product_id = cpr.product_id
         JOIN creator_roles r ON r.creator_role_id = cpr.creator_role_id;


--  TEST:  product × country
SELECT
    p.title,
    c.country_name
FROM country_relation cr
         JOIN products p ON p.product_id = cr.product_id
         JOIN countries c ON c.country_id = cr.country_id
ORDER BY p.product_id;



--  TEST:  product × language
SELECT
    p.title,
    l.language_name
FROM language_relation lr
         JOIN products p ON p.product_id = lr.product_id
         JOIN languages l ON l.language_id = lr.language_id
ORDER BY p.product_id;



--  TEST:   product × item_status × item_location × library
SELECT
    p.title,
    lib.library_name,
    CONCAT(lib.address_street, ' ', lib.address_house_nr, ', ', lib.address_city, ' ', lib.address_postcode) AS address,
    s.item_status_name,
    il.location_in_library
FROM product_items pi
         JOIN products p ON p.product_id = pi.product_id
         JOIN item_status s ON s.item_status_id = pi.status_id
         JOIN item_location il ON il.item_id = pi.item_id
         JOIN libraries lib ON lib.library_id = il.library_id
WHERE pi.item_id = 1001;




-- TEST: update status after borrowing
UPDATE product_items
SET    status_id = 3
WHERE  item_id   = 1001;

SELECT
    c.client_id,
    p.title,
    b.borrow_start_date,
    b.borrow_due_date,
    s.item_status_name AS current_status
FROM borrows b
         JOIN clients c ON c.client_id = b.client_id
         JOIN product_items pi ON pi.item_id = b.item_id
         JOIN products p ON p.product_id = pi.product_id
         JOIN item_status s ON s.item_status_id = pi.status_id
WHERE b.borrow_id = 1;


-- TEST: get of all products with the same title
SELECT
    p.product_id,
    p.title,
    pt.name AS format,
    p.description
FROM products p
         JOIN product_types pt ON p.product_type_id = pt.product_type_id
WHERE p.title IN (
    SELECT title
    FROM products
    GROUP BY title
    HAVING COUNT(*) > 1
)
ORDER BY p.title, pt.is_digital;


