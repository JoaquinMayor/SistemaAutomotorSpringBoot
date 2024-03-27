package com.turnos.vtv.turnosvtv.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.turnos.vtv.turnosvtv.entities.Vehicle;
import com.turnos.vtv.turnosvtv.repositories.IVehicleRepository;


@Service
public class VehicleService {
    
   
    @Autowired
    private IVehicleRepository repository;


   
    @Transactional
    public Vehicle saveVehicle(Vehicle vehicle){
        return repository.save(vehicle);
    }

    @Transactional(readOnly = true)
    public List<Vehicle> findAll(){
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Vehicle> finByPatent(String patent){
        return repository.findByPatent(patent);
    }

    @Transactional(readOnly = true)
    public boolean existByPatent(String patent){
        return repository.existsByPatent(patent);
    }

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
