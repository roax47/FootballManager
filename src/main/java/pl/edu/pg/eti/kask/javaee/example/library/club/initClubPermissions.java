package pl.edu.pg.eti.kask.javaee.example.library.club;

import pl.edu.pg.eti.kask.javaee.example.library.manager.model.Manager;
import pl.edu.pg.eti.kask.javaee.example.library.manager.model.Perm;
import pl.edu.pg.eti.kask.javaee.example.library.manager.model.Permissions;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@ApplicationScoped
public class initClubPermissions {

    @PersistenceContext
    private EntityManager em;


    @Transactional
    public void init(@Observes @Initialized(ApplicationScoped.class) Object init) {
        Permissions perm1 =  Permissions.builder().role( Manager.Roles.ADMIN)
                .operation("findAllClubs").permission(Perm.GRANTED).build();
        Permissions perm2 =  Permissions.builder().role( Manager.Roles.ADMIN)
                .operation("saveClub").permission(Perm.GRANTED).build();
        Permissions perm3 =  Permissions.builder().role( Manager.Roles.ADMIN)
                .operation("removeClub").permission(Perm.GRANTED).build();
        Permissions perm4 =  Permissions.builder().role( Manager.Roles.ADMIN)
                .operation("findClub").permission(Perm.GRANTED).build();
        Permissions perm5=  Permissions.builder().role( Manager.Roles.ADMIN)
                .operation("findClubPlayers").permission(Perm.GRANTED).build();
        Permissions perm6=  Permissions.builder().role( Manager.Roles.ADMIN)
                .operation("findClubByManager").permission(Perm.GRANTED).build();


        Permissions perm7 =  Permissions.builder().role( Manager.Roles.MANAGER)
                .operation("findAllClubs").permission(Perm.GRANTED).build();
        Permissions perm8 =  Permissions.builder().role( Manager.Roles.MANAGER)
                .operation("saveClub").permission(Perm.IF_OWNER).build();
        Permissions perm9 =  Permissions.builder().role( Manager.Roles.MANAGER)
                .operation("removeClub").permission(Perm.IF_OWNER).build();
        Permissions perm10 =  Permissions.builder().role( Manager.Roles.MANAGER)
                .operation("findClub").permission(Perm.GRANTED).build();
        Permissions perm11=  Permissions.builder().role( Manager.Roles.MANAGER)
                .operation("findClubPlayers").permission(Perm.GRANTED).build();
        Permissions perm12=  Permissions.builder().role( Manager.Roles.MANAGER)
                .operation("findClubByManager").permission(Perm.GRANTED).build();

        Permissions perm13 =  Permissions.builder().role( Manager.Roles.ANONYMOUS)
                .operation("findAllClub").permission(Perm.DENIED).build();
        Permissions perm14 =  Permissions.builder().role( Manager.Roles.ANONYMOUS)
                .operation("saveClub").permission(Perm.DENIED).build();
        Permissions perm15 =  Permissions.builder().role( Manager.Roles.ANONYMOUS)
                .operation("removeClub").permission(Perm.DENIED).build();
        Permissions perm16 =  Permissions.builder().role( Manager.Roles.ANONYMOUS)
                .operation("findClub").permission(Perm.DENIED).build();
        Permissions perm17=  Permissions.builder().role( Manager.Roles.ANONYMOUS)
                .operation("findClubPlayers").permission(Perm.DENIED).build();
        Permissions perm18=  Permissions.builder().role( Manager.Roles.ANONYMOUS)
                .operation("findClubByManager").permission(Perm.DENIED).build();

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

        em.persist(perm13);
        em.persist(perm14);
        em.persist(perm15);
        em.persist(perm16);
        em.persist(perm17);
        em.persist(perm18);

    }
}
