DROP INDEX IF EXISTS name_index;

DROP INDEX IF EXISTS fk_library_id_index;

DROP INDEX IF EXISTS name_libraries_index;

DROP INDEX IF EXISTS fk_library_id_books_index;

CREATE INDEX name_libraries_index ON libraries (name);

CREATE INDEX fk_library_id_books_index ON books (library_id);
