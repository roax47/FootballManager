package pl.edu.pg.eti.kask.javaee.example.library.player.view;

import lombok.Setter;
import pl.edu.pg.eti.kask.javaee.example.library.player.PlayerService;
import pl.edu.pg.eti.kask.javaee.example.library.player.model.Player;
import pl.edu.pg.eti.kask.javaee.example.library.player.model.Position;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Named
@ViewScoped
public class PlayerCreateEdit implements Serializable {


    @PostConstruct
    public void init() {
        setPlayer(new Player());
    }
    /**
     * Injected club service.
     */
    private PlayerService service;

    /**
     * All players
     */
    private List<Player> availablePlayers;

    /**
     * Player to be displayed.
     */
    @Setter
    private Player player;

    public Player getPlayer() {
        if(player == null){
            player = new Player();
        }
        return player;
    }
    @Inject
    public PlayerCreateEdit(PlayerService service) {
        this.service = service;
    }

    /**
     * @return all players
     */
    public Collection<Player> getAvailablePlayers() {
        if (availablePlayers == null) {
            if(service.checkIfUserIsAdmin()) availablePlayers = service.findAllPlayers();
            else availablePlayers = service.findPlayersByManager();
        }
        return availablePlayers;
    }

    /**+
     * @return all positions
     */
    public Collection<Position> getAvailablePosition() {
        return List.of(Position.values());
    }

    /**
     * Saves currently viewed player in storage.
     *
     * @return navigation
     */
    public String savePlayer() {
        service.savePlayer(player);
        return "player_list?faces-redirect=true";
    }
}
