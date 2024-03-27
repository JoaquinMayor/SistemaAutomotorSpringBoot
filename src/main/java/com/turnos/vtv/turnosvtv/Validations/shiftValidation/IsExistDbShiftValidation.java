package com.turnos.vtv.turnosvtv.Validations.shiftValidation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.turnos.vtv.turnosvtv.services.ShiftService;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Component
public class IsExistDbShiftValidation implements ConstraintValidator<IsExistDbShift,String>{

    @Autowired
    private ShiftService service;

    @Override
    public boolean isValid(String sku, ConstraintValidatorContext context) {
       
        if(service == null){
            return true;
        }


        return !service.existBySku(sku);
    }
    
}
