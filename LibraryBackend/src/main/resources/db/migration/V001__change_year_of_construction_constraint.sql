ALTER TABLE libraries DROP CONSTRAINT libraries_year_of_construction_check;

ALTER TABLE libraries ADD CONSTRAINT libraries_year_of_construction_check CHECK (year_of_construction >= 1800 AND year_of_construction <= 2023);