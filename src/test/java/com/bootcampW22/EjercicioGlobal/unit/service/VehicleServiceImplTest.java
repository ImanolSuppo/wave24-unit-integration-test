package com.bootcampW22.EjercicioGlobal.unit.service;

import com.bootcampW22.EjercicioGlobal.dto.VehicleAvgCapacityByBrandDto;
import com.bootcampW22.EjercicioGlobal.dto.VehicleAvgSpeedByBrandDto;
import com.bootcampW22.EjercicioGlobal.dto.VehicleDto;
import com.bootcampW22.EjercicioGlobal.entity.Vehicle;
import com.bootcampW22.EjercicioGlobal.exception.NotFoundException;
import com.bootcampW22.EjercicioGlobal.repository.IVehicleRepository;
import com.bootcampW22.EjercicioGlobal.repository.VehicleRepositoryImpl;
import com.bootcampW22.EjercicioGlobal.service.VehicleServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VehicleServiceImplTest {

    @Mock
    private VehicleRepositoryImpl vehicleRepository;

    @InjectMocks
    private VehicleServiceImpl vehicleService;

    private static final List<Vehicle> vehicles = new ArrayList<>();
    private static final List<VehicleDto> vehicleDtos = new ArrayList<>();

    @BeforeAll
    static void initialSetUp() {
        vehicles.add(new Vehicle(1L, "Pontiac", "Fiero", "6603", "Mauv", 1986, "85", 2, "gasoline", "semi-automatic", 105.43, 280.28, 288.8));
        vehicles.add(new Vehicle(2L, "Buick", "LeSabre", "81962", "Green", 2005, "240", 6, "gasoline", "semi-automatic", 207.93, 125.94, 199.22));
        vehicles.add(new Vehicle(3L, "Mitsubishi", "Excel", "0904", "Green", 1987, "89", 5, "gas", "automatic", 39.18, 290.82, 121.17));
        vehicles.add(new Vehicle(4L, "Toyota", "4Runner", "496", "Puce", 1994, "127", 1, "gas", "automatic", 251.59, 121.06, 65.19));
        vehicles.add(new Vehicle(5L, "Lexus", "LS", "03857", "Orange", 2003, "159", 3, "diesel", "automatic", 9.49, 118.21, 168.54));
        vehicles.add(new Vehicle(6L, "Toyota", "Camry", "02112", "Orange", 2000, "150", 4, "diesel", "automatic", 121.06, 251.59, 280.28));

        vehicleDtos.add(new VehicleDto(1L, "Pontiac", "Fiero", "6603", "Mauv", 1986, "85", 2, "gasoline", "semi-automatic", 105.43, 280.28, 288.8));
        vehicleDtos.add(new VehicleDto(2L, "Buick", "LeSabre", "81962", "Green", 2005, "240", 6, "gasoline", "semi-automatic", 207.93, 125.94, 199.22));
        vehicleDtos.add(new VehicleDto(3L, "Mitsubishi", "Excel", "0904", "Green", 1987, "89", 5, "gas", "automatic", 39.18, 290.82, 121.17));
        vehicleDtos.add(new VehicleDto(4L, "Toyota", "4Runner", "496", "Puce", 1994, "127", 1, "gas", "automatic", 251.59, 121.06, 65.19));
        vehicleDtos.add(new VehicleDto(5L, "Lexus", "LS", "03857", "Orange", 2003, "159", 3, "diesel", "automatic", 9.49, 118.21, 168.54));
        vehicleDtos.add(new VehicleDto(6L, "Toyota", "Camry", "02112", "Orange", 2000, "150", 4, "diesel", "automatic", 121.06, 251.59, 280.28));

    }

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void searchAllVehicles() {
        //Arrange
        List<VehicleDto> expected = vehicleDtos;
        when(vehicleRepository.findAll()).thenReturn(vehicles);

        //Act
        List<VehicleDto> result = vehicleService.searchAllVehicles();

        //Assert
        assertEquals(expected, result);
    }

    @Test
    void searchAllVehiclesIsEmpity() {
        //Arrange
        when(vehicleRepository.findAll()).thenReturn(new ArrayList<>());

        //Assert
        assertThrows(NotFoundException.class, () -> vehicleService.searchAllVehicles());
    }

    @Test
    void searchVehiclesByYearAndColor() {
        //Arrange
        List<Vehicle> greenVehicles = new ArrayList<>();
        greenVehicles.add(vehicles.get(2));
        when(vehicleRepository.findVehiclesByYearAndColor("Green", 1987)).thenReturn(greenVehicles);

        List<VehicleDto> expected = new ArrayList<>();
        expected.add(vehicleDtos.get(2));

        //Act
        List<VehicleDto> result = vehicleService.searchVehiclesByYearAndColor("Green", 1987);

        //Assert
        assertEquals(expected, result);
    }

    @Test
    void searchVehiclesByYearAndColorNotFound() {
        //Arrange
        when(vehicleRepository.findVehiclesByYearAndColor("Green", 1988)).thenReturn(new ArrayList<>());

        //Assert
        assertThrows(NotFoundException.class, () -> vehicleService.searchVehiclesByYearAndColor("Green", 1988));
    }

    @Test
    void searchVehiclesByBrandAndRangeOfYear() {
        //Arrange
        List<Vehicle> vehiclesByBrandAndRangeOfYear = new ArrayList<>();
        vehiclesByBrandAndRangeOfYear.add(vehicles.get(0));
        when(vehicleRepository.findVehiclesByBrandAndRangeOfYear("Pontiac", 1986, 2005)).thenReturn(vehiclesByBrandAndRangeOfYear);

        List<VehicleDto> expected = new ArrayList<>();
        expected.add(vehicleDtos.get(0));

        //Act
        List<VehicleDto> result = vehicleService.searchVehiclesByBrandAndRangeOfYear("Pontiac", 1986, 2005);

        //Assert
        assertEquals(expected, result);
    }

    @Test
    void searchVehiclesByBrandAndRangeOfYearNotFound() {
        //Arrange
        List<Vehicle> vehiclesByBrandAndRangeOfYear = new ArrayList<>();
        when(vehicleRepository.findVehiclesByBrandAndRangeOfYear("Pontiac", 1986, 2005)).thenReturn(vehiclesByBrandAndRangeOfYear);
        //Assert
        assertThrows(NotFoundException.class, () -> vehicleService.searchVehiclesByBrandAndRangeOfYear("Pontiac", 1986, 2005));
    }

    @Test
    void calculateAvgSpeedByBrand() {
        //Arrange
        VehicleAvgSpeedByBrandDto expected = new VehicleAvgSpeedByBrandDto(138.5);
        List<Vehicle> toyotaVehicle = new ArrayList<>();
        toyotaVehicle.add(vehicles.get(3));
        toyotaVehicle.add(vehicles.get(5));
        when(vehicleRepository.findVehiclesByBrand("Toyota")).thenReturn(toyotaVehicle);

        //Act
        VehicleAvgSpeedByBrandDto result = vehicleService.calculateAvgSpeedByBrand("Toyota");

        //Assert
        assertEquals(expected, result);
    }

    @Test
    void calculateAvgSpeedByBrandNotFound() {
        //Arrange
        List<Vehicle> nissanVehicle = new ArrayList<>();
        when(vehicleRepository.findVehiclesByBrand("Nissan")).thenReturn(nissanVehicle);

        //Assert
        assertThrows(NotFoundException.class, () -> vehicleService.calculateAvgSpeedByBrand("Nissan"));
    }

    @Test
    void calculateAvgCapacityByBrand() {
        //Arrange
        VehicleAvgCapacityByBrandDto expected = new VehicleAvgCapacityByBrandDto(2.5);
        List<Vehicle> toyotaVehicle = new ArrayList<>();
        toyotaVehicle.add(vehicles.get(3));
        toyotaVehicle.add(vehicles.get(5));
        when(vehicleRepository.findVehiclesByBrand("Toyota")).thenReturn(toyotaVehicle);

        //Act
        VehicleAvgCapacityByBrandDto result = vehicleService.calculateAvgCapacityByBrand("Toyota");

        //Assert
        assertEquals(expected, result);
    }

    @Test
    void calculateAvgCapacityByBrandNotFound() {
        //Arrange
        List<Vehicle> nissanVehicle = new ArrayList<>();
        when(vehicleRepository.findVehiclesByBrand("Nissan")).thenReturn(nissanVehicle);
        //Assert
        assertThrows(NotFoundException.class, () -> vehicleService.calculateAvgCapacityByBrand("Nissan"));
    }

    @Test
    void searchVehiclesByRangeOfWeight() {
        //Arrange
        List<Vehicle> vehiclesByRangeOfWeight = new ArrayList<>();
        vehiclesByRangeOfWeight.add(vehicles.get(0));
        vehiclesByRangeOfWeight.add(vehicles.get(1));
        vehiclesByRangeOfWeight.add(vehicles.get(2));
        vehiclesByRangeOfWeight.add(vehicles.get(3));
        vehiclesByRangeOfWeight.add(vehicles.get(4));
        vehiclesByRangeOfWeight.add(vehicles.get(5));
        when(vehicleRepository.findVehiclesByRangeOfWeight(0.0, 340.0)).thenReturn(vehiclesByRangeOfWeight);

        List<VehicleDto> expected = new ArrayList<>();
        expected.add(vehicleDtos.get(0));
        expected.add(vehicleDtos.get(1));
        expected.add(vehicleDtos.get(2));
        expected.add(vehicleDtos.get(3));
        expected.add(vehicleDtos.get(4));
        expected.add(vehicleDtos.get(5));

        //Act
        List<VehicleDto> result = vehicleService.searchVehiclesByRangeOfWeight(0.0, 340.0);

        //Assert
        assertEquals(expected, result);
    }

    @Test
    void searchVehiclesByRangeOfWeightNotFound() {
        //Arrange
        List<Vehicle> vehiclesByRangeOfWeight = new ArrayList<>();
        when(vehicleRepository.findVehiclesByRangeOfWeight(-1231.0, 0.0)).thenReturn(vehiclesByRangeOfWeight);
        //Assert
        assertThrows(NotFoundException.class, () -> vehicleService.searchVehiclesByRangeOfWeight(-1231.0, 0.0));
    }
}