package pl.edu.pg.eti.kask.javaee.example.library.manager.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Assigns password validator to field.
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordValidator.class)
@Documented
public @interface Password {

    String message() default "password must contain small letter, capital letter and digit";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
