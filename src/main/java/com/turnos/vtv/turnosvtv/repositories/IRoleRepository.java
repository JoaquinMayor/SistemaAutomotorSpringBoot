package com.turnos.vtv.turnosvtv.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.turnos.vtv.turnosvtv.entities.Role;

//Acceso de la información de la tabla roles
public interface IRoleRepository extends CrudRepository<Role, Long>{
    
    Optional<Role> findByName(String name);
}
