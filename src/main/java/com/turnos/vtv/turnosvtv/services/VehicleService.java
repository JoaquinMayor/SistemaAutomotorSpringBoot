package com.turnos.vtv.turnosvtv.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.turnos.vtv.turnosvtv.entities.Vehicle;
import com.turnos.vtv.turnosvtv.repositories.IVehicleRepository;

//Gestión de Vehiculos
@Service
public class VehicleService {
    
   
    @Autowired
    private IVehicleRepository repository;


    //Guardar un vehiculo registrado
    @SuppressWarnings("null")
    @Transactional
    public Vehicle saveVehicle(Vehicle vehicle){
        return repository.save(vehicle);
    }

    //Buscar todos los vehiculos
    @Transactional(readOnly = true)
    public List<Vehicle> findAll(){
        return repository.findAll();
    }

    //Buscar vehiculo por su patente
    @Transactional(readOnly = true)
    public Optional<Vehicle> finByPatent(String patent){
        return repository.findByPatent(patent);
    }

    //Determinar si existe un vehiculo por su patente
    @Transactional(readOnly = true)
    public boolean existByPatent(String patent){
        return repository.existsByPatent(patent);
    }

    //Actualizar los datos de un vehiculo
    @Transactional
    public Optional<Vehicle> update(String patent, Vehicle vehicle){
        Optional<Vehicle> vehicleDB = repository.findByPatent(patent);
        if(vehicleDB.isPresent()){
            Vehicle vehicleNew = vehicleDB.orElseThrow();
            vehicleNew.setEnabled(vehicle.isEnabled());
            vehicleNew.setKilometres(vehicle.getKilometres());
            vehicleNew.setModel(vehicle.getModel());
            vehicleNew.setType(vehicle.getType());
            return Optional.of(repository.save(vehicleNew));
        }
        return vehicleDB;
    }

   
}
