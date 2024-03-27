package com.turnos.vtv.turnosvtv.repositories;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.Set;

import com.turnos.vtv.turnosvtv.entities.User;

public interface IUserRepository extends CrudRepository<User, String>{

    @Query("select u from User u left join fetch u.vehicles ")
    Set<User> allUsers();

    @Query("SELECT u FROM User u left join fetch u.vehicles WHERE u.email =?1")
    Optional<User> findByEmail(String email);

    @Query("select u from User u left join fetch u.vehicles where u.dni = ?1")
    Optional<User> findByDni(String dni);

    @Query("select u from User u left join fetch u.vehicles where u.lastname = ?1")
    Optional<User> findByLastname(String lastname);
    
    boolean existsByDni(String dni);
}   
