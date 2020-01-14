package pl.edu.pg.eti.kask.javaee.example.library.manager.view;

import lombok.Setter;
import pl.edu.pg.eti.kask.javaee.example.library.manager.ManagerService;
import pl.edu.pg.eti.kask.javaee.example.library.manager.model.Manager;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Named
@ViewScoped
public class ManagerCreateEdit implements Serializable {

    /**
     * Injected manager service.
     */
    private ManagerService service;

    /**
     * All managers
     */
    private List<Manager> availableManagers;


    private Set<String> availableRoles;

    /**
     * Manager to be displayed.
     */
    @Setter
    private Manager manager;

    public Manager getManager() {
        if(manager == null){
            manager = new Manager();
        }
        return manager;
    }

    @Inject
    public ManagerCreateEdit(ManagerService service) {
        this.service = service;
    }

    @PostConstruct
    public void init() {
        setManager(new Manager());
    }
    /**
     * @return all managers
     */
    public Collection<String> getAvailableRoles() {
        if(availableRoles==null) {
            availableRoles = new HashSet<String>();
            Field[] allFields = Manager.Roles.class.getDeclaredFields();
            for (Field field : allFields) {
                field.setAccessible(true);
                availableRoles.add(field.getName());
            }
        }
        return availableRoles;
    }


    public Collection<Manager> getAvailableManagers() {
        if (availableManagers == null) {
            availableManagers = service.findAllManagers();
        }
        return availableManagers;
    }
    /**
     * Saves currently viewed manager
     *
     * @return navigation
     */
    public String saveManager() {
        service.saveManager(manager);
        return "manager_list?faces-redirect=true";
    }
}
