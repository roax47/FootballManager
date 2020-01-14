package pl.edu.pg.eti.kask.javaee.example.library.player;

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
public class initPlayerPermissions {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void init(@Observes @Initialized(ApplicationScoped.class) Object init) {
        Permissions perm1 =  Permissions.builder().role( Manager.Roles.ADMIN)
                .operation("findAllPlayers").permission(Perm.GRANTED).build();
        Permissions perm2 =  Permissions.builder().role( Manager.Roles.ADMIN)
                .operation("savePlayer").permission(Perm.GRANTED).build();
        Permissions perm3 =  Permissions.builder().role( Manager.Roles.ADMIN)
                .operation("removePlayer").permission(Perm.GRANTED).build();
        Permissions perm4 =  Permissions.builder().role( Manager.Roles.ADMIN)
                .operation("findPlayer").permission(Perm.GRANTED).build();
        Permissions perm5=  Permissions.builder().role( Manager.Roles.ADMIN)
                .operation("findPlayersByLastName").permission(Perm.GRANTED).build();
        Permissions perm6=  Permissions.builder().role( Manager.Roles.ADMIN)
                .operation("findPlayersByManager").permission(Perm.GRANTED).build();



        Permissions perm7 =  Permissions.builder().role( Manager.Roles.MANAGER)
                .operation("findAllPlayers").permission(Perm.GRANTED).build();
        Permissions perm8 =  Permissions.builder().role( Manager.Roles.MANAGER)
                .operation("savePlayer").permission(Perm.IF_OWNER).build();
        Permissions perm9 =  Permissions.builder().role( Manager.Roles.MANAGER)
                .operation("removePlayer").permission(Perm.IF_OWNER).build();
        Permissions perm10 =  Permissions.builder().role( Manager.Roles.MANAGER)
                .operation("findPlayer").permission(Perm.GRANTED).build();
        Permissions perm11=  Permissions.builder().role( Manager.Roles.MANAGER)
                .operation("findPlayersByLastName").permission(Perm.GRANTED).build();
        Permissions perm12=  Permissions.builder().role( Manager.Roles.MANAGER)
                .operation("findPlayersByManager").permission(Perm.GRANTED).build();

        Permissions perm13 =  Permissions.builder().role( Manager.Roles.ANONYMOUS)
                .operation("findAllPlayer").permission(Perm.DENIED).build();
        Permissions perm14 =  Permissions.builder().role( Manager.Roles.ANONYMOUS)
                .operation("savePlayer").permission(Perm.DENIED).build();
        Permissions perm15 =  Permissions.builder().role( Manager.Roles.ANONYMOUS)
                .operation("removePlayer").permission(Perm.DENIED).build();
        Permissions perm16 =  Permissions.builder().role( Manager.Roles.ANONYMOUS)
                .operation("findPlayer").permission(Perm.DENIED).build();
        Permissions perm17=  Permissions.builder().role( Manager.Roles.ANONYMOUS)
                .operation("findPlayersByLastName").permission(Perm.DENIED).build();
        Permissions perm18=  Permissions.builder().role( Manager.Roles.ANONYMOUS)
                .operation("findPlayersByManager").permission(Perm.DENIED).build();

        Permissions perm19=  Permissions.builder().role( Manager.Roles.ADMIN)
                .operation("findLastModifiedPlayers").permission(Perm.GRANTED).build();
        Permissions perm20=  Permissions.builder().role( Manager.Roles.MANAGER)
                .operation("findLastModifiedPlayers").permission(Perm.GRANTED).build();
        Permissions perm21=  Permissions.builder().role( Manager.Roles.ADMIN)
                .operation("findLastModifiedPlayers").permission(Perm.DENIED).build();


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

        em.persist(perm19);
        em.persist(perm20);
        em.persist(perm21);

    }
}
