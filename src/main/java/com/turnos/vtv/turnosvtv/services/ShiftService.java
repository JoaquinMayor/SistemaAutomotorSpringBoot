package com.turnos.vtv.turnosvtv.services;


import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.turnos.vtv.turnosvtv.entities.Shift;
import com.turnos.vtv.turnosvtv.entities.Vehicle;
import com.turnos.vtv.turnosvtv.repositories.IShiftRepository;
import com.turnos.vtv.turnosvtv.repositories.IVehicleRepository;
//Gestión de turnos
@Service
public class ShiftService {
    
 

    @Autowired
    private IShiftRepository repository;

    @Autowired
    private IVehicleRepository repositoryVehicle;

    //Almacenar un turno
    @SuppressWarnings("null")
    @Transactional
    public Shift save(Shift shift){
        return repository.save(shift);
    }

    //Encontrar todos los turnos
    @Transactional(readOnly = true)
    public List<Shift> findAll(){
        return repository.findAll();
    }

    //Buscar turno por fecha
    @Transactional(readOnly = true)
    public List<Shift> findByDate(Date date){
        return repository.findByDate(date);
    }

    //Buscar turno por patente de vehiculo
    @Transactional(readOnly = true)
    public Optional<Shift> findByPatent(String patent){
        return repository.findByPatent(patent);   
    }

    //Si existe el sku del turno
    @Transactional(readOnly = true)
    public boolean existBySku(String sku){
        return repository.existsBySku(sku);
    }

    //Eliminación de un turno
    @Transactional
    public boolean removeShift(String patent){
        Optional<Vehicle> optionalVehicle = repositoryVehicle.findByPatent(patent);
        Optional<Shift> optionalShift = repository.findByPatent(patent);

        if(optionalVehicle.isPresent() && optionalShift.isPresent()){
            Vehicle vehicle = optionalVehicle.orElseThrow();
            Shift shift = optionalShift.orElseThrow();
            vehicle.getShifts().remove(shift);
            shift.setVehicle(null);
            repositoryVehicle.save(vehicle);
            return true;

        }else{
            return false;
        }
    }


}
