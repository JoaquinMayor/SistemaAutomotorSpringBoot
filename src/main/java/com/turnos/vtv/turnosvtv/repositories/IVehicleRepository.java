package com.turnos.vtv.turnosvtv.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.turnos.vtv.turnosvtv.entities.Vehicle;
//Acceso a la tabla e información de los vehiculos
public interface IVehicleRepository extends CrudRepository<Vehicle,String>{

    
    
    @SuppressWarnings("null")
    @Query("select v from Vehicle v left join fetch v.shifts")
    List<Vehicle> findAll();

    @Query("select v from Vehicle v left join fetch v.shifts where v.patent=?1")
    Optional<Vehicle> findByPatent(String patent);
    
    boolean existsByPatent(String patent);
}
