package com.bootcampW22.EjercicioGlobal.unit.repository;

import com.bootcampW22.EjercicioGlobal.entity.Vehicle;
import com.bootcampW22.EjercicioGlobal.repository.IVehicleRepository;
import com.bootcampW22.EjercicioGlobal.repository.VehicleRepositoryImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@SpringBootTest
class VehicleRepositoryImplTest {

    private static IVehicleRepository repository;
    private static List<Vehicle> listOfVehicles;

    @BeforeAll
    static void initialSetUp() throws IOException {
        repository = new VehicleRepositoryImpl();
        listOfVehicles = new ArrayList<>();
        loadDataBase();
    }

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void findAll() {
        // Arrange
        List<Vehicle> expected = listOfVehicles;

        // Act
        List<Vehicle> result = repository.findAll();

        // Assert
        Assertions.assertEquals(expected, result);
    }

    @Test
    void findAllSize() {
        // Arrange
        int sizeExpected = 500;

        // Act
        int sizeResult = repository.findAll().size();

        // Assert
        Assertions.assertEquals(sizeExpected, sizeResult);
    }

    @Test
    void findVehiclesByYearAndColor() {
        // Arrange
        String color = "Red";
        int year = 1999;
        int sizeExpected = 3;

        // Act
        int sizeResult = repository.findVehiclesByYearAndColor(color, year).size();

        // Assert
        Assertions.assertEquals(sizeExpected, sizeResult);
    }

    @Test
    void findVehiclesByBrandAndRangeOfYear() {
        // Arrange
        String brand = "Ford";
        int start_year = 1999;
        int end_year = 2000;
        int sizeExpected = 1;

        // Act
        int sizeResult = repository.findVehiclesByBrandAndRangeOfYear(brand, start_year, end_year).size();

        // Assert
        Assertions.assertEquals(sizeExpected, sizeResult);
    }

    @Test
    void findVehiclesByBrand() {
        // Arrange
        String brand = "Ford";
        int sizeExpected = 46;

        // Act
        int sizeResult = repository.findVehiclesByBrand(brand).size();

        // Assert
        Assertions.assertEquals(sizeExpected, sizeResult);
    }

    @Test
    void findVehiclesByRangeOfWeight() {
        // Arrange
        double weight_min = 1;
        double weight_max = 1000;
        int sizeExpected = 500;

        // Act
        int sizeResult = repository.findVehiclesByRangeOfWeight(weight_min, weight_max).size();

        // Assert
        Assertions.assertEquals(sizeExpected, sizeResult);
    }

    private static void loadDataBase() throws IOException {
        File file;
        ObjectMapper objectMapper = new ObjectMapper();
        List<Vehicle> vehicles ;

        file= ResourceUtils.getFile("classpath:vehicles_100.json");
        vehicles= objectMapper.readValue(file,new TypeReference<List<Vehicle>>(){});

        listOfVehicles = vehicles;
    }
}