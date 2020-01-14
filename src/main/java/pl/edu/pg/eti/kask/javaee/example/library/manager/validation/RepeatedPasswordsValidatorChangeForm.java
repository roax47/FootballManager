package pl.edu.pg.eti.kask.javaee.example.library.manager.validation;

import pl.edu.pg.eti.kask.javaee.example.library.manager.view.model.ChangePasswordForm;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

/**
 * Validates if provided passwords are the same.
 *
 * @author psysiu
 */
public class RepeatedPasswordsValidatorChangeForm
        implements ConstraintValidator<RepeatedPasswords, ChangePasswordForm> {

    @Override
    public boolean isValid(ChangePasswordForm value, ConstraintValidatorContext context) {
        return Objects.equals(value.getPassword(), value.getRepeatPassword());
    }
}
