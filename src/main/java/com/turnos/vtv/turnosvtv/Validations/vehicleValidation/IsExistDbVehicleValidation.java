package com.turnos.vtv.turnosvtv.Validations.vehicleValidation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.turnos.vtv.turnosvtv.services.VehicleService;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
//Validación que determina si existe un vehiculo en la base de datos
@Component
public class IsExistDbVehicleValidation implements ConstraintValidator<IsExistDbVehicle,String>{

    @Autowired
    private VehicleService service;

    @Override
    public boolean isValid(String patent, ConstraintValidatorContext context) {
       
        if(service == null){
            return true;
        }

        return !service.existByPatent(patent);
    }
    
}
