package pl.edu.pg.eti.kask.javaee.example.library.manager.interceptor;

import lombok.extern.java.Log;
import pl.edu.pg.eti.kask.javaee.example.library.manager.model.Manager;
import pl.edu.pg.eti.kask.javaee.example.library.manager.model.Perm;
import pl.edu.pg.eti.kask.javaee.example.library.manager.model.Permissions;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.logging.Level;

@Interceptor
@ManagerBinding
@Priority(100)
@Log
public class ManagerInterceptor {

    @PersistenceContext
    private EntityManager em;

    @Inject
    private HttpServletRequest securityContext;

    @AroundInvoke
    public Object invoke(InvocationContext context) throws Exception {
        log.log(Level.INFO, "User " + (securityContext.getUserPrincipal() != null ?
                securityContext.getUserPrincipal().getName() : "Anonymous") + " calls "
                + context.getMethod().getName() + " with params " + Arrays.toString(context.getParameters()));
        Object result;
        if(userAllowed(context)) {
            result = context.proceed();
        }
        else throw new Exception("Access Denied");
        return result;
    }

    private boolean userAllowed(InvocationContext context){
        if(securityContext.getUserPrincipal() == null){
            return checkPermission(Manager.Roles.ANONYMOUS,context.getMethod().getName(),context.getParameters());
        }
       else if(securityContext.isUserInRole(Manager.Roles.ADMIN)){
           return checkPermission(Manager.Roles.ADMIN,context.getMethod().getName(),context.getParameters());
        }
       else if (securityContext.isUserInRole(Manager.Roles.MANAGER)){
            return checkPermission(Manager.Roles.MANAGER,context.getMethod().getName(),context.getParameters());
        }
       else return false;
    }

    private boolean checkPermission(String role,String operation,Object[] params){
        Permissions permission =  em.createNamedQuery(Permissions.Queries.FIND_PERMISSION, Permissions.class)
                .setParameter("role", role)
                .setParameter("operation", operation)
                .getSingleResult();
        if(permission == null) return false;
        else if(permission.getPermission() == Perm.GRANTED) return true;
        else if (permission.getPermission() == Perm.IF_OWNER) {
            return checkOwnership(params);
        }
        else return false;
    }

    private boolean checkOwnership(Object[] params){
        if (params == null) return false;
        Manager manager = (Manager) params[0];
        Manager userInDB = em.find(Manager.class, manager.getId());
        if(userInDB == null) return true;
        return userInDB.getLogin().equals(securityContext.getUserPrincipal().getName());
    }
}
