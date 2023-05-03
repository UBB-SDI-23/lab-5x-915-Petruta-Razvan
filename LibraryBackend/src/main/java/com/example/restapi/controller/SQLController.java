package com.example.restapi.controller;

import com.example.restapi.dtos.SQLRunResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;

@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
//@CrossOrigin(origins = "https://sdi-library-management.netlify.app", allowCredentials = "true")
@RestController
@RequestMapping("/api")
public class SQLController {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostMapping("/run-delete-books-script")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<?> deleteAllBooks() {
        try {
            String currentDir = System.getProperty("user.dir");
//            String sql = Files.readString(Paths.get(currentDir + "\\src\\main\\java\\com\\example\\restapi\\sql_scripts\\delete_books.sql"));
            String sql = Files.readString(Paths.get(currentDir + "/src/main/java/com/example/restapi/sql_scripts/delete_books.sql"));
            jdbcTemplate.update(sql);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new SQLRunResponseDTO("Successfully deleted all books"));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new SQLRunResponseDTO("Error: something went wrong"));
        }
    }

    @PostMapping("/run-delete-libraries-script")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<?> deleteAllLibraries() {
        try {
            String currentDir = System.getProperty("user.dir");
//            String sql = Files.readString(Paths.get(currentDir + "\\src\\main\\java\\com\\example\\restapi\\sql_scripts\\delete_libraries.sql"));
            String sql = Files.readString(Paths.get(currentDir + "/src/main/java/com/example/restapi/sql_scripts/delete_libraries.sql"));
            jdbcTemplate.update(sql);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new SQLRunResponseDTO("Successfully deleted all libraries"));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new SQLRunResponseDTO("Error: something went wrong (make sure you deleted memberships first)"));
        }
    }

    @PostMapping("/run-delete-memberships-script")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<?> deleteAllMemberships() {
        try {
            String currentDir = System.getProperty("user.dir");
//            String sql = Files.readString(Paths.get(currentDir + "\\src\\main\\java\\com\\example\\restapi\\sql_scripts\\delete_memberships.sql"));
            String sql = Files.readString(Paths.get(currentDir + "/src/main/java/com/example/restapi/sql_scripts/delete_memberships.sql"));
            jdbcTemplate.update(sql);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new SQLRunResponseDTO("Successfully deleted all memberships"));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new SQLRunResponseDTO("Error: something went wrong"));
        }
    }

    @PostMapping("/run-delete-readers-script")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<?> deleteAllReaders() {
        try {
            String currentDir = System.getProperty("user.dir");
//            String sql = Files.readString(Paths.get(currentDir + "\\src\\main\\java\\com\\example\\restapi\\sql_scripts\\delete_readers.sql"));
            String sql = Files.readString(Paths.get(currentDir + "/src/main/java/com/example/restapi/sql_scripts/delete_readers.sql"));
            jdbcTemplate.update(sql);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new SQLRunResponseDTO("Successfully deleted all readers"));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new SQLRunResponseDTO("Error: something went wrong (make sure you deleted memberships first)"));
        }
    }

    @PostMapping("/run-insert-libraries-script")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<?> insertAllLibraries() {
        try {
            String currentDir = System.getProperty("user.dir");
//            String fullPath = currentDir + "\\src\\main\\java\\com\\example\\restapi\\sql_scripts\\insert_libraries.sql";
            String fullPath = currentDir + "/src/main/java/com/example/restapi/sql_scripts/insert_libraries.sql";
            BufferedReader reader = new BufferedReader(new FileReader(fullPath));
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                jdbcTemplate.update(line);
            }
            reader.close();
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new SQLRunResponseDTO("Successfully inserted all libraries"));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new SQLRunResponseDTO("Error: something went wrong"));
        }
    }

    @PostMapping("/run-insert-books-script")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<?> insertAllBooks() {
        try {
            String currentDir = System.getProperty("user.dir");
//            String fullPath = currentDir + "\\src\\main\\java\\com\\example\\restapi\\sql_scripts\\insert_books.sql";
            String fullPath = currentDir + "/src/main/java/com/example/restapi/sql_scripts/insert_books.sql";
            BufferedReader reader = new BufferedReader(new FileReader(fullPath));
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                jdbcTemplate.update(line);
            }
            reader.close();
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new SQLRunResponseDTO("Successfully inserted all books"));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new SQLRunResponseDTO("Error: something went wrong (make sure you inserted the libraries first)"));
        }
    }

    @PostMapping("/run-insert-readers-script")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<?> insertAllReaders() {
        try {
            String currentDir = System.getProperty("user.dir");
//            String fullPath = currentDir + "\\src\\main\\java\\com\\example\\restapi\\sql_scripts\\insert_readers.sql";
            String fullPath = currentDir + "/src/main/java/com/example/restapi/sql_scripts/insert_readers.sql";
            BufferedReader reader = new BufferedReader(new FileReader(fullPath));
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                jdbcTemplate.update(line);
            }
            reader.close();
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new SQLRunResponseDTO("Successfully inserted all readers"));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new SQLRunResponseDTO("Error: something went wrong"));
        }
    }

    @PostMapping("/run-insert-memberships-script")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<?> insertAllMemberships() {
        try {
            String currentDir = System.getProperty("user.dir");
//            String fullPath = currentDir + "\\src\\main\\java\\com\\example\\restapi\\sql_scripts\\insert_memberships.sql";
            String fullPath = currentDir + "/src/main/java/com/example/restapi/sql_scripts/insert_memberships.sql";
            BufferedReader reader = new BufferedReader(new FileReader(fullPath));
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                jdbcTemplate.update(line);
            }
            reader.close();
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new SQLRunResponseDTO("Successfully inserted all memberships"));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new SQLRunResponseDTO("Error: something went wrong (make sure you inserted libraries and readers first)"));
        }
    }
}
