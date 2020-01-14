package pl.edu.pg.eti.kask.javaee.example.library.club.view.converter;


import pl.edu.pg.eti.kask.javaee.example.library.club.ClubService;
import pl.edu.pg.eti.kask.javaee.example.library.club.model.Club;

import javax.enterprise.context.Dependent;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;


@FacesConverter(forClass = Club.class, managed = true)
@Dependent
public class ClubConverter implements Converter<Club> {


    private ClubService service;

    @Inject
    public ClubConverter(ClubService service) {
        this.service = service;
    }

    @Override
    public Club getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        return service.findClub(Integer.parseInt(value));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Club value) {
        if (value == null || value.getId()==null) {
            return "";
        }
        return Integer.toString(value.getId());
    }
}
