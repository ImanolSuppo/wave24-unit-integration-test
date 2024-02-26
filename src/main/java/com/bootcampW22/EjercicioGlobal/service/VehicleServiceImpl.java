package com.bootcampW22.EjercicioGlobal.service;

import com.bootcampW22.EjercicioGlobal.dto.VehicleAvgCapacityByBrandDto;
import com.bootcampW22.EjercicioGlobal.dto.VehicleAvgSpeedByBrandDto;
import com.bootcampW22.EjercicioGlobal.dto.VehicleDto;
import com.bootcampW22.EjercicioGlobal.entity.Vehicle;
import com.bootcampW22.EjercicioGlobal.exception.NotFoundException;
import com.bootcampW22.EjercicioGlobal.repository.IVehicleRepository;
import com.bootcampW22.EjercicioGlobal.repository.VehicleRepositoryImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VehicleServiceImpl implements IVehicleService{

    IVehicleRepository vehicleRepository;

    public VehicleServiceImpl(VehicleRepositoryImpl vehicleRepository){
        this.vehicleRepository = vehicleRepository;
    }
    @Override
    public List<VehicleDto> searchAllVehicles() {
        List<Vehicle> vehicleList = vehicleRepository.findAll();
        if(vehicleList.isEmpty()){
            throw new NotFoundException("No se encontró ningun auto en el sistema.");
        }
        return vehicleList.stream()
                .map(this::convertVehicleToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<VehicleDto> searchVehiclesByYearAndColor(String color, Integer year) {
        List<Vehicle> vehicleFilteredList = vehicleRepository.findVehiclesByYearAndColor(color,year);
        if(vehicleFilteredList.isEmpty()){
            throw new NotFoundException("No se encontraron vehículos con esos criterios.");
        }
        return vehicleFilteredList.stream()
                .map(this::convertVehicleToDto)
                .toList();
    }

    @Override
    public List<VehicleDto> searchVehiclesByBrandAndRangeOfYear(String brand, Integer start_year, Integer end_year) {
        ObjectMapper mapper = new ObjectMapper();
        List<Vehicle> vehicleFilteredList = vehicleRepository.findVehiclesByBrandAndRangeOfYear(brand,start_year,end_year);
        if(vehicleFilteredList.isEmpty()){
            throw new NotFoundException("No se encontraron vehículos con esos criterios.");
        }
        return vehicleFilteredList.stream()
                .map(this::convertVehicleToDto)
                .toList();
    }

    @Override
    public VehicleAvgSpeedByBrandDto calculateAvgSpeedByBrand(String brand) {
        List<Vehicle> vehicleFilteredList = vehicleRepository.findVehiclesByBrand(brand);
        if(vehicleFilteredList.isEmpty()){
            throw new NotFoundException("No se encontraron vehículos de esa marca.");
        }
        Double avg_speed = vehicleFilteredList.stream()
                .map(Vehicle::getMax_speed)
                .mapToDouble(Double::parseDouble)
                .average()
                .orElse(0.0);

        return new VehicleAvgSpeedByBrandDto(Math.round(avg_speed * 100.0)/100.0);
    }

    @Override
    public VehicleAvgCapacityByBrandDto calculateAvgCapacityByBrand(String brand) {
        List<Vehicle> vehicleFilteredList = vehicleRepository.findVehiclesByBrand(brand);
        if(vehicleFilteredList.isEmpty()){
            throw new NotFoundException("No se encontraron vehículos de esa marca.");
        }
        double avg_capacity = vehicleFilteredList.stream()
                .mapToDouble(Vehicle::getPassengers)
                .average()
                .orElse(0.0);
        return new VehicleAvgCapacityByBrandDto(Math.round(avg_capacity * 100.0)/100.0);
    }

    @Override
    public List<VehicleDto> searchVehiclesByRangeOfWeight(Double weight_min, Double weight_max) {
        List<Vehicle> vehicleFilteredList = vehicleRepository.findVehiclesByRangeOfWeight(weight_min,weight_max);
        if(vehicleFilteredList.isEmpty()){
            throw new NotFoundException("No se encontraron vehículos en ese rango de peso.");
        }
        return vehicleFilteredList.stream()
                .map(this::convertVehicleToDto)
                .toList();
    }


    private VehicleDto convertVehicleToDto(Vehicle v){
        return new VehicleDto(
                v.getId(),
                v.getBrand(),
                v.getModel(),
                v.getRegistration(),
                v.getColor(),
                v.getYear(),
                v.getMax_speed(),
                v.getPassengers(),
                v.getFuel_type(),
                v.getTransmission(),
                v.getLength(),
                v.getWidth(),
                v.getWeight());
    }

}
