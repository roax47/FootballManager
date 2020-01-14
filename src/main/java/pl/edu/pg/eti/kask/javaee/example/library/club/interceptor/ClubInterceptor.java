package pl.edu.pg.eti.kask.javaee.example.library.club.interceptor;

import lombok.extern.java.Log;
import pl.edu.pg.eti.kask.javaee.example.library.club.model.Club;
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
@ClubBinding
@Priority(100)
@Log
public class ClubInterceptor {

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
        Club club = (Club) params[0];
        if(club == null) return false;
        Club clubInDB = checkIfClubExisted(club);
        if(clubInDB.getManager()!=null){
            return  clubInDB.getManager().getLogin()
                    .equals(securityContext.getUserPrincipal().getName());
        }
        else return false;
    }

    private Club checkIfClubExisted(Club club){
        if(club.getId()!=null) return em.find(Club.class, club.getId());
        else return club;
    }
}
