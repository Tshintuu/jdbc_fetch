package com.wildcodeschool.wizards.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import com.wildcodeschool.wizards.entities.School;
import com.wildcodeschool.wizards.repositories.SchoolRepository;

@Controller
@ResponseBody
public class SchoolController {
    private final static String DB_URL = "jdbc:mysql://localhost:3306/wild_db_quest?serverTimezone=GMT";
    private final static String DB_USER = "*****";
    private final static String DB_PASSWORD = "*****";

    @GetMapping("api/school")
    public List<School> getSchools(@RequestParam(defaultValue="%") String country){
        try(
            Connection connection = DriverManager.getConnection(
                DB_URL, DB_USER, DB_PASSWORD
            );

            PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM school WHERE country LIKE ?"
            );

        ) {
            statement.setString(1, country);

            try(
                ResultSet resultSet = statement.executeQuery();
            ) {
                List<School> schools = new ArrayList<School>();

                while(resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    Integer capacity = resultSet.getInt("capacity");
                    String countryResult = resultSet.getString("country");
                    schools.add(new School(id, name, capacity, countryResult));
                }
                return schools;
            }
        }
        catch (SQLException e) {
            throw new ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR, "",e
            );
        }
    }

    @PostMapping("/api/school")
    @ResponseStatus(HttpStatus.CREATED)
    public School store(
        @RequestParam String name,
        @RequestParam Integer capacity,
        @RequestParam String country
    ) {
        int idGeneratedByInsertion = SchoolRepository.insert(
            name,
            capacity,
            country
        );
        return SchoolRepository.selectById(
            idGeneratedByInsertion
        );
    }

    @PutMapping("/api/school/{id}")
    public School update(
        @PathVariable int id,
        @RequestParam(required = false) String name,
        @RequestParam(required = false) Integer capacity,
        @RequestParam(required = false) String country
    ) {
        School school = SchoolRepository.selectById(id);
        SchoolRepository.update(
            id,
            name != null ? name : school.getName(),
            capacity != null ? capacity : school.getCapacity(),
            country != null ? country : school.getCountry()
        );
        return SchoolRepository.selectById(id);
    }

    @DeleteMapping("api/school/{id}")
    public void delete(
        @PathVariable int id
    ) {
        SchoolRepository.delete(id);
    }
}
