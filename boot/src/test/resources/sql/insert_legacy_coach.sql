INSERT INTO coaches (id, email, name, first_name, last_name) 
VALUES (1, 'oldCoachVersion@test.com', 'Old Version', 'TBD', 'TBD');

ALTER TABLE coaches ALTER COLUMN id RESTART WITH 2;
