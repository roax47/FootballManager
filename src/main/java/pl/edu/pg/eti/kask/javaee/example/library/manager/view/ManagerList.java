package pl.edu.pg.eti.kask.javaee.example.library.manager.view;

import lombok.Getter;
import lombok.Setter;
import pl.edu.pg.eti.kask.javaee.example.library.manager.ManagerService;
import pl.edu.pg.eti.kask.javaee.example.library.manager.model.Manager;
import pl.edu.pg.eti.kask.javaee.example.library.manager.model.Manager_;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotBlank;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Named
@RequestScoped
public class ManagerList {

    private ManagerService service;

    private List<Manager> managers;


    @Getter
    @Setter
    private String filter;

    @NotBlank(message = "Filter has to have a value")
    @Getter
    @Setter
    private String property;

    @Inject
    public ManagerList(ManagerService service) {
        this.service = service;
    }

    public List<Manager> getManagers() {
        if (managers == null) {
            managers = service.findAllManagers();
        }
        return managers;
    }

    public boolean checkRole(){
        return service.checkRole();
    }

    public List<String> getManagerProperties(){
        List<String> managerProperties = new ArrayList<>();
        Manager manager = new Manager();
        Field fieldlist[] = manager.getClass().getDeclaredFields();
        for (Field aFieldlist : fieldlist) {
                if(!aFieldlist.getName().equals("links") && !aFieldlist.getName().equals("password")){
                    managerProperties.add(aFieldlist.getName());
                }
        }
        return managerProperties;
    }
    private void FilterSort(String filter,String property,Boolean sort,  Boolean asc){
        boolean set = property.equals(Manager_.roles.getName());
        managers = service.getManagersByFilterSort(filter, property, sort, set, asc);
    }

    public void getManagersByFilter(){
        if(filter.isEmpty()) managers = service.findAllManagers();
        else FilterSort(filter,property,false,false);
    }
    public void getManagersBySortAsc(){
        FilterSort(null,property,true,true);
    }

    public void getManagersBySortDesc(){
        FilterSort(null,property,true,false);
    }

    public String removeManager(Manager manager) {
        service.removeManager(manager);
        return "manager_list?faces-redirect=true";
    }

    public void resetFilter(){
        managers = null;
        filter = null;
    }

}
