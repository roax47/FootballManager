package pl.edu.pg.eti.kask.javaee.example.library.player.view;
import pl.edu.pg.eti.kask.javaee.example.library.player.PlayerService;
import pl.edu.pg.eti.kask.javaee.example.library.player.model.Player;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
@RequestScoped
public class PlayerModifiedList {

    /**
     * Injected book service.
     */
    private PlayerService service;


    private List<Player> players;

    @Inject
    public PlayerModifiedList(PlayerService service) {
        this.service = service;
    }


    public List<Player> getPlayers() {
        players = service.findLastModifiedPlayers();
        return players;
    }

    public void refresh(){
        players = service.findLastModifiedPlayers();
    }


}
