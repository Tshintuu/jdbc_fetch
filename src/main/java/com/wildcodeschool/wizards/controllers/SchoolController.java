package com.wildcodeschool.wizards.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

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
                    int capacity = resultSet.getInt("capacity");
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

    class School {
        private int id;
        private String name;
        private int capacity;
        private String country;

        public School(int id, String name, int capacity, String country) {
            this.id = id;
            this.name = name;
            this.capacity = capacity;
            this.country = country;
        }

        public int getId() {
            return this.id;
        }

        public String getName() {
            return this.name;
        }

        public int getCapacity() {
            return this.capacity;
        }

        public String getCountry() {
            return this.country;
        }
    }
}
