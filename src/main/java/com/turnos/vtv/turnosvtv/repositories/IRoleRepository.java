package com.turnos.vtv.turnosvtv.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.turnos.vtv.turnosvtv.entities.Role;

public interface IRoleRepository extends CrudRepository<Role, Long>{
    
    Optional<Role> findByName(String name);
}
