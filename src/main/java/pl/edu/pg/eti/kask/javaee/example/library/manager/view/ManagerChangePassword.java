package pl.edu.pg.eti.kask.javaee.example.library.manager.view;

import lombok.Getter;
import lombok.Setter;
import pl.edu.pg.eti.kask.javaee.example.library.manager.model.Manager;
import pl.edu.pg.eti.kask.javaee.example.library.manager.view.model.ChangePasswordForm;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.io.Serializable;

import static pl.edu.pg.eti.kask.javaee.example.library.manager.HashUtils.sha256;

@ViewScoped
@Named
public class ManagerChangePassword implements Serializable {


        @PersistenceContext
        private EntityManager em;

        @Inject
        private HttpServletRequest securityContext;

        @Setter
        @Getter
        private Manager manager;

        @Getter
        private ChangePasswordForm form = new ChangePasswordForm();

        private Boolean checkOldPassword(){
                return manager.getPassword().equals(sha256(form.getOldPassword()));
        }

        @Transactional
        public String register() {

                if (!checkAdmin() && !checkOldPassword()) {
                        return "/manager_wrong_password?faces-redirect=true";
                }
                manager.setPassword(sha256(form.getPassword()));
                em.merge(manager);
                em.flush();
                return "/manager_registered?faces-redirect=true";
        }


        public Boolean checkAdmin() {
                return securityContext.isUserInRole(Manager.Roles.ADMIN);
        }
}
