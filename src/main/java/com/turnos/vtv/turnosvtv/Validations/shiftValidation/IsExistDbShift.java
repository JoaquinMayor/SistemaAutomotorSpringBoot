package com.turnos.vtv.turnosvtv.Validations.shiftValidation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = IsExistDbShiftValidation.class)
public @interface IsExistDbShift {
    
    String message() default "ya existe el turno en la base de datos!, escoja otra fecha";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
