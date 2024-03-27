package com.turnos.vtv.turnosvtv.Validations.vehicleValidation;



import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = IsExistDbVehicleValidation.class)
public @interface IsExistDbVehicle {
 
    String message() default "ya existe el vehiculo en la base de datos!";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
