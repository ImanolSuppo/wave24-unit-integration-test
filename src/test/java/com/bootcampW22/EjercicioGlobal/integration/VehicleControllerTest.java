package com.bootcampW22.EjercicioGlobal.integration;

import com.bootcampW22.EjercicioGlobal.dto.VehicleDto;
import com.bootcampW22.EjercicioGlobal.entity.Vehicle;
import com.bootcampW22.EjercicioGlobal.exception.NotFoundException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class VehicleControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    private static final String url = "/vehicles";

    private static List<Vehicle> listOfVehicles = new ArrayList<>();

    @BeforeAll
    static void initialSetUp() throws IOException {
        loadDataBase();
    }

    @Test
    void getVehicles() throws Exception {
        RequestBuilder requestBuilder = get(url);

        ResultMatcher statusExpected = status().isOk();

        List<VehicleDto> vehicleDtos = map(listOfVehicles);

        ResultMatcher bodyExpected = content().json(mapper.writeValueAsString(vehicleDtos));

        ResultMatcher contentExpected = content().contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(statusExpected)
                .andExpect(bodyExpected)
                .andExpect(contentExpected);
    }


    @Test
    void getVehiclesByColorAndYearHappyPath() throws Exception {
        RequestBuilder requestBuilder = get(url + "/color/Aquamarine/year/1998");

        ResultMatcher statusExpected = status().isOk();

        List<VehicleDto> vehicleDtos = map(listOfVehicles.stream().filter(
                vehicle -> vehicle.getColor().equals("Aquamarine") && vehicle.getYear() == 1998
        ).toList());

        ResultMatcher bodyExpected = content().json(mapper.writeValueAsString(vehicleDtos));

        ResultMatcher contentExpected = content().contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(statusExpected)
                .andExpect(bodyExpected)
                .andExpect(contentExpected);

    }

    //Este es el Test que me falla
    //Cuando hago la request a /vehicles/color/dszfdasfs/year/1998 me devuelve un 404 (como se espera)
    // pero no me devuelve el body o eso es lo que yo estoy entendiendo

    //Exception:
    //java.lang.AssertionError:
    //Expected: cause
    //     but none found
    // ;
    //Expected: localizedMessage
    //     but none found
    // ;
    //Expected: stackTrace
    //     but none found
    // ;
    //Expected: suppressed
    //     but none found

    @Test
    void getVehiclesByColorAndYearSadPath() throws Exception {
        RequestBuilder requestBuilder = get(url + "/color/dszfdasfs/year/1998");

        ResultMatcher statusExpected = status().isNotFound();

        NotFoundException notFoundException = new NotFoundException("No se encontraron vehículos con esos criterios.");

        ResultMatcher bodyExpected = content().json(mapper.writeValueAsString(notFoundException));

        ResultMatcher contentExpected = content().contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(statusExpected)
                .andExpect(contentExpected)
                .andExpect(bodyExpected);

    }

    @Test
    void testGetVehiclesByColorAndYear() {
    }

    @Test
    void getAverageSpeedByBrand() {
    }

    @Test
    void getAverageCapacityByBrand() {
    }

    @Test
    void getVehiclesByRangeOfWeight() {
    }

    private List<VehicleDto> map(List<Vehicle> vehicles) {
        return vehicles.stream().map(
                vehicle -> new VehicleDto(
                        vehicle.getId(),
                        vehicle.getBrand(),
                        vehicle.getModel(),
                        vehicle.getRegistration(),
                        vehicle.getColor(),
                        vehicle.getYear(),
                        vehicle.getMax_speed(),
                        vehicle.getPassengers(),
                        vehicle.getFuel_type(),
                        vehicle.getTransmission(),
                        vehicle.getLength(),
                        vehicle.getWidth(),
                        vehicle.getWeight()
                )
        ).toList();
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