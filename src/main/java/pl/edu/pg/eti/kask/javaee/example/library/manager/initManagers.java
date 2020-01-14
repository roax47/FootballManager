package pl.edu.pg.eti.kask.javaee.example.library.manager;

import pl.edu.pg.eti.kask.javaee.example.library.manager.model.Manager;
import pl.edu.pg.eti.kask.javaee.example.library.manager.model.Perm;
import pl.edu.pg.eti.kask.javaee.example.library.manager.model.Permissions;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import static pl.edu.pg.eti.kask.javaee.example.library.manager.HashUtils.sha256;

/**
 * CDI bean automatically launched when container starts.
 *
 * @author psysiu
 */
@ApplicationScoped
public class initManagers {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void init(@Observes @Initialized(ApplicationScoped.class) Object init) {
        initPermissions();
        Manager admin =  Manager.builder().login("admin").password(sha256("admin"))
                .role( Manager.Roles.ADMIN).role( Manager.Roles.MANAGER).build();
        Manager manager =  Manager.builder().login("manager").password(sha256("manager"))
                .role( Manager.Roles.MANAGER).build();
        Manager manager2 =  Manager.builder().login("manager2").password(sha256("manager2"))
                .role( Manager.Roles.MANAGER).build();
        em.persist(manager);
        em.persist(manager2);
        em.persist(admin);
        em.flush();
    }

    @Transactional
    private void initPermissions(){
        Permissions perm1 =  Permissions.builder().role( Manager.Roles.ADMIN)
                .operation("findAllManagers").permission(Perm.GRANTED).build();
        Permissions perm2 =  Permissions.builder().role( Manager.Roles.ADMIN)
                .operation("saveManager").permission(Perm.GRANTED).build();
        Permissions perm3 =  Permissions.builder().role( Manager.Roles.ADMIN)
                .operation("removeManager").permission(Perm.GRANTED).build();
        Permissions perm4 =  Permissions.builder().role( Manager.Roles.ADMIN)
                .operation("findManager").permission(Perm.GRANTED).build();

        Permissions perm5 =  Permissions.builder().role( Manager.Roles.MANAGER)
                .operation("findAllManagers").permission(Perm.GRANTED).build();
        Permissions perm6 =  Permissions.builder().role( Manager.Roles.MANAGER)
                .operation("saveManager").permission(Perm.IF_OWNER).build();
        Permissions perm7 =  Permissions.builder().role( Manager.Roles.MANAGER)
                .operation("removeManager").permission(Perm.IF_OWNER).build();
        Permissions perm8 =  Permissions.builder().role( Manager.Roles.MANAGER)
                .operation("findManager").permission(Perm.GRANTED).build();

        Permissions perm9 =  Permissions.builder().role( Manager.Roles.ANONYMOUS)
                .operation("findAllManagers").permission(Perm.DENIED).build();
        Permissions perm10 =  Permissions.builder().role( Manager.Roles.ANONYMOUS)
                .operation("saveManager").permission(Perm.DENIED).build();
        Permissions perm11 =  Permissions.builder().role( Manager.Roles.ANONYMOUS)
                .operation("removeManager").permission(Perm.DENIED).build();
        Permissions perm12 =  Permissions.builder().role( Manager.Roles.ANONYMOUS)
                .operation("findManager").permission(Perm.DENIED).build();
        em.persist(perm1);
        em.persist(perm2);
        em.persist(perm3);
        em.persist(perm4);

        em.persist(perm5);
        em.persist(perm6);
        em.persist(perm7);
        em.persist(perm8);

        em.persist(perm9);
        em.persist(perm10);
        em.persist(perm11);
        em.persist(perm12);
    }

}
