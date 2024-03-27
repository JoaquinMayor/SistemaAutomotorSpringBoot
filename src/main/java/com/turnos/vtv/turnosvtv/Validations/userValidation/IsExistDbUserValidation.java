package com.turnos.vtv.turnosvtv.Validations.userValidation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.turnos.vtv.turnosvtv.services.UserService;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Component
public class IsExistDbUserValidation implements ConstraintValidator<IsExistDbUser,String>{

    @Autowired
    private UserService service;
    @Override
    public boolean isValid(String dni, ConstraintValidatorContext context) {
        
        if(service == null){
            return true;
        }

        return !service.existByDni(dni);
    }
    
}
