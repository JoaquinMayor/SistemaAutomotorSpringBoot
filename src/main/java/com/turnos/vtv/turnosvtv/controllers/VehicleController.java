package com.turnos.vtv.turnosvtv.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.turnos.vtv.turnosvtv.entities.Vehicle;
import com.turnos.vtv.turnosvtv.services.VehicleService;

import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {
    
    @Autowired
    private VehicleService service;

    //Lista todos los vehiculos existentes para el admin
    @GetMapping
    public List<Vehicle> list(){
        return service.findAll();
    }

    //Busca un vehiculo por su patente
    @GetMapping("/{patent}")
    public ResponseEntity<?> findByPatent(@PathVariable String patent){
        Optional<Vehicle> optionalVehicle = service.finByPatent(patent);
        if(optionalVehicle.isPresent()){
            return ResponseEntity.ok(optionalVehicle.orElseThrow());
        }

        return ResponseEntity.notFound().build();
    }

    //Registro de un vehiculo en el sistema
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Vehicle vehicle, BindingResult result){
        if(result.hasFieldErrors()){
            return validation(result);
        }

        Vehicle vehicleNew = service.saveVehicle(vehicle);
        return ResponseEntity.status(HttpStatus.CREATED).body(vehicleNew);
    }

    
    //Actualización de un vehiculo, buscado por su patente
    @PutMapping("/{patent}")
    public ResponseEntity<?> update(@PathVariable String patent, @Valid @RequestBody Vehicle vehicle, BindingResult result){
        if(result.hasFieldErrors()){
            return validation(result);
        }

        vehicle.setPatent(patent);
        Optional<Vehicle> vehicleOptional = service.update(patent, vehicle);

        if(vehicleOptional.isPresent()){
            Vehicle vehicleNew = vehicleOptional.orElseThrow();
            return ResponseEntity.status(HttpStatus.CREATED).body(vehicleNew);

        }

        return ResponseEntity.notFound().build();
    }

    //Validación de que los controladores no tienen errores
    private ResponseEntity<Map<String, String>> validation(BindingResult result){
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(err ->{ //Da una lista de mensajesel getFieldErrors y lo recorremos para ir creando los mensajes
            errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors); //Siempre que se pasa un status 400 se hace un badRequest
    }

}
