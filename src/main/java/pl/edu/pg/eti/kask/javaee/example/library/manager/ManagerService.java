package pl.edu.pg.eti.kask.javaee.example.library.manager;


import pl.edu.pg.eti.kask.javaee.example.library.manager.interceptor.ManagerBinding;
import pl.edu.pg.eti.kask.javaee.example.library.manager.model.Manager;
import pl.edu.pg.eti.kask.javaee.example.library.manager.model.Manager_;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;

@ApplicationScoped
public class ManagerService {

    @PersistenceContext
    private EntityManager em;

    @Inject
    private HttpServletRequest securityContext;

    public ManagerService() { }

    @ManagerBinding
    public List<Manager> findAllManagers() {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Manager> query = builder.createQuery(Manager.class);
        Root<Manager> root = query.from(Manager.class);
        query.select(root);
        if(securityContext.isUserInRole(Manager.Roles.ADMIN) || securityContext.getUserPrincipal() == null) {
            return em.createQuery(query)
                    .setHint("javax.persistence.loadgraph", em.getEntityGraph(Manager.Graphs.WITH_CLUB))
                    .getResultList();
        } else  {
            ParameterExpression<String> p = builder.parameter(String.class);
            query.where(builder.equal(root.get(Manager_.login),p));
            return em.createQuery(query)
                    .setParameter(p,securityContext.getUserPrincipal().getName())
                    .setHint("javax.persistence.loadgraph", em.getEntityGraph(Manager.Graphs.WITH_CLUB))
                    .getResultList();
        }
    }

    public List<Manager> getManagersByFilterSort(String filterValue,String property,boolean sort, boolean set,
                                                 boolean asc){
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Manager> query = builder.createQuery(Manager.class);
        Root<Manager> root = query.from(Manager.class);
        if(sort) {
            if(set){
                if (asc) query.orderBy(builder.asc(builder.size(root.<Collection>get(property))));
                else query.orderBy(builder.desc(builder.size(root.<Collection>get(property))));
            }
            else {
                if (asc) query.orderBy(builder.asc(root.get(property)));
                else query.orderBy(builder.desc(root.get(property)));
            }
        }
        if(filterValue!=null) {
            if (!set) query.where(builder.equal(root.get(property), filterValue));
            else query.where(builder.isMember(filterValue, root.get(property)));
        }
        query.select(root);
        return em.createQuery(query)
                .setHint("javax.persistence.loadgraph", em.getEntityGraph(Manager.Graphs.WITH_CLUB))
                .getResultList();
    }


    @ManagerBinding
    public Manager findManager(int id) {
        return em.find(Manager.class, id);
    }


    @Transactional
    private void updateClub(Manager manager){
        if(manager.getClub()!=null) {
            manager.getClub().setManager(manager);
            em.merge(manager.getClub());
        }
    }

    @Transactional
    @ManagerBinding
    public void saveManager(Manager manager) {
        if (manager.getId() == null) {
            em.persist(manager);
        } else {
            updateClub(manager);
            em.merge(manager);
        }
    }

    public Boolean checkRole(){
        if(securityContext.getUserPrincipal() == null) return false;
        return securityContext.isUserInRole(Manager.Roles.ADMIN);
    }

    @Transactional
    @ManagerBinding
    public void removeManager(Manager manager) {
        em.remove(em.merge(manager));
    }

}
