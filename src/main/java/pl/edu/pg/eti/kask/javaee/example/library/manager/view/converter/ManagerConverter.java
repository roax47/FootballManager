package pl.edu.pg.eti.kask.javaee.example.library.manager.view.converter;

import pl.edu.pg.eti.kask.javaee.example.library.manager.ManagerService;
import pl.edu.pg.eti.kask.javaee.example.library.manager.model.Manager;

import javax.enterprise.context.Dependent;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

@FacesConverter(forClass = Manager.class, managed = true)
@Dependent
public class ManagerConverter implements Converter<Manager> {

    /**
     * Injected club service.
     */
    private ManagerService service;

    @Inject
    public ManagerConverter(ManagerService service) {
        this.service = service;
    }

    @Override
    public Manager getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        return service.findManager(Integer.parseInt(value));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Manager value) {
        if (value == null|| value.getId()==null) {
            return "";
        }
        return Integer.toString(value.getId());
    }
}
