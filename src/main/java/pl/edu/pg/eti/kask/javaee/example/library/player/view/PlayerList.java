package pl.edu.pg.eti.kask.javaee.example.library.player.view;

import lombok.Getter;
import lombok.Setter;
import pl.edu.pg.eti.kask.javaee.example.library.player.PlayerService;
import pl.edu.pg.eti.kask.javaee.example.library.player.model.Player;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Named
@RequestScoped
public class PlayerList {

    /**
     * Injected book service.
     */
    private PlayerService service;


    @NotBlank(message = "Filter has to have a value")
    @Getter
    @Setter
    private String lastNameFilter;
    /**
     * Lazy loaded list of books.
     */
    private List<Player> players;

    @Inject
    public PlayerList(PlayerService service) {
        this.service = service;
    }

    /**
     * @return all players
     */
    public List<Player> getPlayers() {
        if (players == null) {
            players = service.findAllPlayers();
        }
        return players;
    }

    /**
     * @return all players
     */
    public void getPlayersByLastName() {
        players = service.findPlayersByLastName(lastNameFilter);
    }

    public Boolean checkIfUserIsClubManagerOrAdmin(Player player){
        return service.checkIfUserIsAdminOrClubManager(player);
    }

    /**
     * Deletes players
     *
     * @param player book to be deleted
     * @return navigation url
     */
    public String removePlayer(Player player) {
        service.removePlayer(player);
        return "player_list?faces-redirect=true";
    }


}
