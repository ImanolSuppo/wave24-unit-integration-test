package com.bootcampW22.EjercicioGlobal.service;

import com.bootcampW22.EjercicioGlobal.dto.VehicleAvgCapacityByBrandDto;
import com.bootcampW22.EjercicioGlobal.dto.VehicleAvgSpeedByBrandDto;
import com.bootcampW22.EjercicioGlobal.dto.VehicleDto;

import java.util.List;

public interface IVehicleService {
    List<VehicleDto> searchAllVehicles();
    List<VehicleDto> searchVehiclesByYearAndColor(String color,Integer year);
    List<VehicleDto> searchVehiclesByBrandAndRangeOfYear(String brand, Integer start_year, Integer end_year);
    VehicleAvgSpeedByBrandDto calculateAvgSpeedByBrand(String brand);
    VehicleAvgCapacityByBrandDto calculateAvgCapacityByBrand(String brand);
    List<VehicleDto> searchVehiclesByRangeOfWeight(Double weight_min, Double weight_max);
}
