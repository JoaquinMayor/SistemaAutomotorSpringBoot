package com.turnos.vtv.turnosvtv.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.turnos.vtv.turnosvtv.entities.Shift;


import java.util.List;
import java.util.Optional;
import java.util.Date;


public interface IShiftRepository extends CrudRepository<Shift,String>{
    
@SuppressWarnings("null")
@Query("select s from Shift s")
    List<Shift> findAll();
    
    @Query("select s from Shift s where s.date = ?1")
    List<Shift> findByDate(Date date);

    @Query("select s from Shift s where s.patent= ?1")
    Optional<Shift> findByPatent(String patent);

    boolean existsBySku(String sku);
}
