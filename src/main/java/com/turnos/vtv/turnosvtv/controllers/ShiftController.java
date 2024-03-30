package com.turnos.vtv.turnosvtv.controllers;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.turnos.vtv.turnosvtv.entities.Shift;
import com.turnos.vtv.turnosvtv.services.ShiftService;

import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/shift")
public class ShiftController {
    
    @Autowired
    private ShiftService service;

    //Retorno de todos los turnos para control del admin
    @GetMapping
    public List<Shift> findaAll(){
        return service.findAll();
    }

    //Obtener un turno para el admin mediante la fecha y hacer control
    @GetMapping("/myObject")
    public List<Shift> listByDate(@RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") Date date) throws ParseException{ //http://localhost:8080/api/shift/myObject?date=2022-02-01T00:00
            return service.findByDate(date);
    }

    //Obtener lista de turnos en base a la patente del vehiculo
   @GetMapping("/{patent}")
    public ResponseEntity<?> findByPatent(@PathVariable String patent){

        Optional<Shift> optionalShift = service.findByPatent(patent);
        if(optionalShift.isPresent()){
            return ResponseEntity.ok(optionalShift.orElseThrow());
        }

        return ResponseEntity.notFound().build();
    }

    //Creación de un turno, cuando lo hace el admin o el user
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Shift shift, BindingResult result){
        if(result.hasFieldErrors()){
            return validation(result);
        }

        Shift newShift = service.save(shift);
        return ResponseEntity.status(HttpStatus.CREATED).body(newShift);
    }

    //Eliminación para cuando se cancela o vence un turno
    @DeleteMapping("/delete/{patent}")
    public ResponseEntity<?> delete(@PathVariable String patent){
        
        boolean eliminated = service.removeShift(patent);
        if(eliminated){
            return ResponseEntity.ok("Mensaje eliminado con Exito");
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    //Validación de las funciones de que se encuentran bien
    private ResponseEntity<Map<String, String>> validation(BindingResult result){
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(err ->{ //Da una lista de mensajesel getFieldErrors y lo recorremos para ir creando los mensajes
            errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors); //Siempre que se pasa un status 400 se hace un badRequest
    }

}
