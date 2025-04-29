-- TEST: creator × product × creator_role
SELECT p.title,
       c.creator_first_name || ' ' || c.creator_last_name AS creator,
       r.creator_role
FROM   creator_product_relation cpr
           JOIN   creators c  ON c.creator_id  = cpr.creator_id
           JOIN   products p ON p.product_id  = cpr.product_id
           JOIN   creator_roles r     ON r.creator_role_id     = cpr.creator_role_id;

-- -- TEST:  product × country
SELECT p.title, c.country_name
FROM   country_relation cr
           JOIN   products p ON p.product_id  = cr.product_id
           JOIN   countries  c ON c.country_id  = cr.country_id
ORDER  BY p.product_id;

-- -- TEST:  product × language
SELECT p.title, l.language
FROM   language_relation lr
           JOIN   products  p ON p.product_id  = lr.product_id
           JOIN   languages l ON l.language_id = lr.language_id
ORDER  BY p.product_id;
