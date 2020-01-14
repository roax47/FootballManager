package pl.edu.pg.eti.kask.javaee.example.library.manager.view;

import lombok.Getter;
import pl.edu.pg.eti.kask.javaee.example.library.manager.model.Manager;
import pl.edu.pg.eti.kask.javaee.example.library.manager.view.model.RegistrationForm;

import javax.faces.context.ExternalContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.io.Serializable;

import static pl.edu.pg.eti.kask.javaee.example.library.manager.HashUtils.sha256;

/**
 * In future this will be used to register new users. Now its only for validation.
 *
 * @author psysiu
 */
@ViewScoped
@Named
public class ManagerRegisterView implements Serializable {

    @Inject
    private ExternalContext context;

    @PersistenceContext
    private EntityManager em;
    /**
     * Registration data.
     */
    @Getter
    private RegistrationForm form = new RegistrationForm();

    @Transactional
    public String register() {
        Manager manager =  Manager.builder()
                .login(form.getLogin())
                .password(sha256(form.getPassword()))
                .role( Manager.Roles.MANAGER).build();
        em.persist(manager);
        em.flush();
        context.invalidateSession();
        return "manager_registered?faces-redirect=true";
    }
}
