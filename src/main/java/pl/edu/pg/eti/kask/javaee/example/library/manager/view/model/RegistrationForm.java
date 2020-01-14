package pl.edu.pg.eti.kask.javaee.example.library.manager.view.model;

import lombok.*;
import pl.edu.pg.eti.kask.javaee.example.library.manager.validation.Password;
import pl.edu.pg.eti.kask.javaee.example.library.manager.validation.PasswordGroup;
import pl.edu.pg.eti.kask.javaee.example.library.manager.validation.RepeatedPasswords;

import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * View DTO for registration.
 *
 * @author psysiu
 */
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@RepeatedPasswords(groups = PasswordGroup.class)
public class RegistrationForm implements Serializable {

    /**
     * User login.
     */
    @Size(min = 3, max = 12)
    private String login;

    /**
     * User password.
     */
    @Size(min = 8, max = 20)
    @Password
    private String password;

    /**
     * Repeated password.
     */
    @Size(min = 8, max=20)
    @Password
    private String repeatPassword;

}
