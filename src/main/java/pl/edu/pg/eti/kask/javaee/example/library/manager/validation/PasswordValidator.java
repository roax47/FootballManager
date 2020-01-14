package pl.edu.pg.eti.kask.javaee.example.library.manager.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Validates if password contains at least one small letter, one capital letter and one digit.
 *
 * @author psysiu
 */
public class PasswordValidator implements ConstraintValidator<Password, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        boolean capital = false;
        boolean small = false;
        boolean digit = false;

        for (char character : value.toCharArray()) {
            if (Character.isUpperCase(character)) {
                capital = true;
            }
            if (Character.isLowerCase(character)) {
                small = true;
            }
            if (Character.isDigit(character)) {
                digit = true;
            }
        }
        return capital && small && digit;
    }

}
