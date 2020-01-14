package pl.edu.pg.eti.kask.javaee.example.library.player.view.converter;

import pl.edu.pg.eti.kask.javaee.example.library.player.PlayerService;
import pl.edu.pg.eti.kask.javaee.example.library.player.model.Player;

import javax.enterprise.context.Dependent;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

@FacesConverter(forClass = Player.class, managed = true)
@Dependent
public class PlayerConverter implements Converter<Player> {

    /**
     * Injected club service.
     */
    private PlayerService service;

    @Inject
    public PlayerConverter(PlayerService service) {
        this.service = service;
    }

    @Override
    public Player getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        return service.findPlayer(Integer.parseInt(value));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Player value) {
        if (value == null || value.getId()==null) {
            return "";
        }
        return Integer.toString(value.getId());
    }
}
