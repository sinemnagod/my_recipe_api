-- Check if database exists
SELECT SCHEMA_NAME
FROM INFORMATION_SCHEMA.SCHEMATA
WHERE SCHEMA_NAME = 'my_recipe';
-- List all tables
SELECT TABLE_NAME
FROM INFORMATION_SCHEMA.TABLES
WHERE TABLE_SCHEMA = 'my_recipe';
-- Check recipe table content
SELECT *
FROM my_recipe.recipe;
-- Check user table content
SELECT id,
    name,
    email
FROM my_recipe.user;
-- Check category table content
SELECT *
FROM my_recipe.category;