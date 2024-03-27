package com.turnos.vtv.turnosvtv.services;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.turnos.vtv.turnosvtv.entities.Role;
import com.turnos.vtv.turnosvtv.entities.User;
import com.turnos.vtv.turnosvtv.entities.Vehicle;
import com.turnos.vtv.turnosvtv.repositories.IRoleRepository;
import com.turnos.vtv.turnosvtv.repositories.IUserRepository;
import com.turnos.vtv.turnosvtv.repositories.IVehicleRepository;

@Service
public class UserService {
    
     
    @Autowired
    private IUserRepository repository;

    @Autowired
    private IRoleRepository repositoryRole;

    @Autowired
    private IVehicleRepository repositoryVehicle;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public User save(User user){
        Optional<Role> optionalRole = repositoryRole.findByName("ROLE_USER");
        Set<Role> roles = new HashSet<>();
        optionalRole.ifPresent(role ->roles.add(role));

        user.setRoles(roles);
        String passwordEncoded = passwordEncoder.encode(user.getPassword()) ; //Codificación de la contraseña
        user.setPassword(passwordEncoded);
        return repository.save(user);
    }

    @Transactional
    public ResponseEntity<?> addVehicle(String dni, Vehicle vehicle){
        Optional<User> optionalUser = repository.findByDni(dni);

        if(optionalUser.isPresent()){
            User user = optionalUser.orElseThrow();
            user.setVehicle(vehicle);
            vehicle.setCarOwner(user);
            repository.save(user);
            repositoryVehicle.save(vehicle);
            return ResponseEntity.status(HttpStatus.CREATED).body(user);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Transactional(readOnly = true)
    public Set<User> allUsers(){
        return repository.allUsers();
    }

    @Transactional(readOnly = true)
    public Optional<User> findByEmail(String email){
        return repository.findByEmail(email);
    }

    @Transactional(readOnly = true)
    public Optional<User> findByDni(String dni){
        return repository.findByDni(dni);
    }

    @Transactional(readOnly = true)
    public Optional<User> findByLastname(String lastname){
        return repository.findByLastname(lastname);
    }

    @Transactional(readOnly = true)
    public boolean existByDni(String dni){
        return repository.existsByDni(dni);
    }

    @Transactional
    public Optional<User> update(String dni, User user){
        Optional<User> userDB = repository.findByDni(dni);
        if(userDB.isPresent()){
            User userNew = userDB.orElseThrow();
            userNew.setAge(user.getAge());
            userNew.setEmail(user.getEmail());
            userNew.setEnabled(user.isEnabled());
            userNew.setLastname(user.getLastname());
            userNew.setName(user.getName());
            userNew.setPassword(passwordEncoder.encode(user.getPassword()));
            return Optional.of(repository.save(userNew));
        }
        return userDB;
    }

    @Transactional
    public Optional<User> changePassword(String dni, String password){
        Optional<User> optionalUser = repository.findByDni(dni);
        if(optionalUser.isPresent()){
            User user = optionalUser.orElseThrow();
            user.setPassword(passwordEncoder.encode(password));
            return Optional.of(repository.save(user));
        }
        return optionalUser;
    }

    @Transactional
    public Optional<User> changeName(String dni, String name){
        Optional<User> optionalUser = repository.findByDni(dni);
        if(optionalUser.isPresent()){
            User user = optionalUser.orElseThrow();
            user.setName(name);;
            return Optional.of(repository.save(user));
        }
        return optionalUser;
    }

    @Transactional
    public Optional<User> changeLastname(String dni, String lastName){
        Optional<User> optionalUser = repository.findByDni(dni);
        if(optionalUser.isPresent()){
            User user = optionalUser.orElseThrow();
            user.setLastname(lastName);
            return Optional.of(repository.save(user));
        }
        return optionalUser;
    }

    @Transactional
    public Optional<User> changeAge(String dni, Integer age){
        Optional<User> optionalUser = repository.findByDni(dni);
        if(optionalUser.isPresent()){
            User user = optionalUser.orElseThrow();
            user.setAge(age);
            return Optional.of(repository.save(user));
        }
        return optionalUser;
    }

    @Transactional
    public Optional<User> changeEmail(String dni, String email){
        Optional<User> optionalUser = repository.findByDni(dni);
        if(optionalUser.isPresent()){
            User user = optionalUser.orElseThrow();
            user.setEmail(email);
            return Optional.of(repository.save(user));
        }
        return optionalUser;
    }

    

     @Transactional
    public ResponseEntity<?> changeCarOwner(String patent, String dniFirstOwner, String dniNewOwner){
        Optional<Vehicle> optionalVehicle = repositoryVehicle.findByPatent(patent);
        Optional<User> optionalFirstUser = repository.findByDni(dniFirstOwner);
        Optional<User> optionalNewUser = repository.findByDni(dniNewOwner);

        if(optionalVehicle.isPresent() && optionalFirstUser.isPresent() && optionalNewUser.isPresent()){
            Vehicle vehicle = optionalVehicle.orElseThrow();
            User firstUser = optionalFirstUser.orElseThrow();
            User newUser = optionalNewUser.orElseThrow();

            firstUser.getVehicles().remove(vehicle);
            addVehicle(dniNewOwner, vehicle);
            repository.save(firstUser);
            
            return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    
}
