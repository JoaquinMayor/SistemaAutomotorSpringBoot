package com.turnos.vtv.turnosvtv.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.turnos.vtv.turnosvtv.Validations.UpdateValidationGroups;
import com.turnos.vtv.turnosvtv.entities.User;
import com.turnos.vtv.turnosvtv.entities.Vehicle;
import com.turnos.vtv.turnosvtv.services.UserService;

import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/users")
//@Validated
public class UserController {
    
    @Autowired
    private UserService service;

    @GetMapping
    public Set<User> list(){
        return service.allUsers();
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<?> findByEmail(@PathVariable String email){

        Optional<User> userOptional = service.findByEmail(email);
        if(userOptional.isPresent()){
            return ResponseEntity.ok(userOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/lastname/{lastname}")
    public ResponseEntity<?> findByLastname(@PathVariable String lastname){

        Optional<User> userOptional = service.findByLastname(lastname);
        if(userOptional.isPresent()){
            return ResponseEntity.ok(userOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/dni/{dni}")
    public ResponseEntity<?> findByDni(@PathVariable String dni){

        Optional<User> userOptional = service.findByDni(dni);
        if(userOptional.isPresent()){
            return ResponseEntity.ok(userOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody User user, BindingResult result){
        
        if(result.hasFieldErrors()){
            return validation(result);
        }

        User userNew = service.save(user);
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.CREATED.value());
        response.put("data", userNew);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/dni/{dni}")
    public ResponseEntity<?> update(@PathVariable String dni, @Validated(UpdateValidationGroups.class) @RequestBody User user, BindingResult result){
        if(result.hasFieldErrors()){
            return validation(result);
        }

        
        Optional<User> userOptional = service.update(dni, user);

        if(userOptional.isPresent()){
            User userNew = userOptional.orElseThrow();
            return ResponseEntity.status(HttpStatus.CREATED).body(userNew);

        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/changePassword/{dni}/{password}")
    private ResponseEntity<?>changePassword(@PathVariable String dni, @PathVariable String password, BindingResult result){
        if(result.hasFieldErrors()){
            return validation(result);
        }
        Optional<User> userOptional =  service.changePassword(dni, password);
        
        if(userOptional.isPresent()){
            User userNew = userOptional.orElseThrow();
            return ResponseEntity.status(HttpStatus.CREATED).body(userNew);

        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/changeName/{dni}/{name}")
    private ResponseEntity<?>changeName(@PathVariable String dni, @PathVariable String name){
      
        Optional<User> userOptional =  service.changeName(dni, name);
        
        if(userOptional.isPresent()){
            User userNew = userOptional.orElseThrow();
            return ResponseEntity.status(HttpStatus.CREATED).body(userNew);

        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/changeLastname/{dni}/{lastname}")
    private ResponseEntity<?>changeLastname(@PathVariable String dni, @PathVariable String lastname){
       
        Optional<User> userOptional =  service.changeLastname(dni, lastname);
        
        if(userOptional.isPresent()){
            User userNew = userOptional.orElseThrow();
            return ResponseEntity.status(HttpStatus.CREATED).body(userNew);

        }

        return ResponseEntity.notFound().build();
    }

    

    @PatchMapping("/changeAge/{dni}/{age}")
    private ResponseEntity<?>changePassword(@PathVariable String dni, @PathVariable Integer age){
       
        Optional<User> userOptional =  service.changeAge(dni, age);
        
        if(userOptional.isPresent()){
            User userNew = userOptional.orElseThrow();
            return ResponseEntity.status(HttpStatus.CREATED).body(userNew);

        }

        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/changeEmail/{dni}/{email}")
    private ResponseEntity<?>changeEmail(@PathVariable String dni, @PathVariable String email){
        
        Optional<User> userOptional =  service.changeEmail(dni, email);
        
        if(userOptional.isPresent()){
            User userNew = userOptional.orElseThrow();
            return ResponseEntity.status(HttpStatus.CREATED).body(userNew);

        }

        return ResponseEntity.notFound().build();
    }

    @PutMapping("/add/vehicle/{dni}")
    public ResponseEntity<?> addVehicle(@PathVariable String dni, @Valid @RequestBody Vehicle vehicle, BindingResult result){
        if(result.hasFieldErrors()){
            return validation(result);
        }

        return service.addVehicle(dni, vehicle);
    }

    @PutMapping("/change/{patent}/{dniFirst}/{dniSecond}")
    public ResponseEntity<?> changeOwner(@PathVariable String patent, @PathVariable String dniFirst, @PathVariable String dniSecond){
        return service.changeCarOwner(patent, dniFirst, dniSecond);
    }
    
    
    
    private ResponseEntity<Map<String, String>> validation(BindingResult result){
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(err ->{ //Da una lista de mensajesel getFieldErrors y lo recorremos para ir creando los mensajes
            errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors); //Siempre que se pasa un status 400 se hace un badRequest
    }

    

}
