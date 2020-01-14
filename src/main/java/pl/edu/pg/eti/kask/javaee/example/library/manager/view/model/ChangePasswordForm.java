package pl.edu.pg.eti.kask.javaee.example.library.manager.view.model;

import lombok.*;
import pl.edu.pg.eti.kask.javaee.example.library.manager.validation.Password;
import pl.edu.pg.eti.kask.javaee.example.library.manager.validation.PasswordGroup;
import pl.edu.pg.eti.kask.javaee.example.library.manager.validation.RepeatedPasswords;

import javax.validation.constraints.Size;
import java.io.Serializable;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@RepeatedPasswords(groups = PasswordGroup.class)
public class ChangePasswordForm implements Serializable {
    /**
     * Old password.
     */
    @Size(min = 3, max = 20)
    private String oldPassword;

    /**
     * User password.
     */
    @Size(min = 3, max = 20)
    @Password
    private String password;

    /**
     * Repeated password.
     */
    @Size(min = 3, max=20)
    @Password
    private String repeatPassword;
}
